package com.rainchat.testitems.listeners;

import com.rainchat.testitems.items.SpecialItem;
import com.rainchat.testitems.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemListener implements Listener {

    private final ItemManager itemManager;

    public ItemListener(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void handleItemUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (isSpecialItem(itemInHand)) {
            event.setCancelled(true);
            for (SpecialItem specialItem : itemManager.getSpecialItems()) {
                if (specialItem.performAction(player)) {
                    itemInHand.setAmount(itemInHand.getAmount() - 1);
                }
            }
        }
    }

    @EventHandler
    public void handleItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (isSpecialItem(item)) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
                return;
            }
        }
    }

    @EventHandler
    public void handleItemSmelt(FurnaceSmeltEvent event) {
        ItemStack item = event.getSource();
        if (isSpecialItem(item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (isSpecialItem(item)) {
            event.setCancelled(true);
            player.sendMessage("Этот предмет нельзя употреблять.");
        }
    }

    private boolean isSpecialItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();
        List<SpecialItem> specialItems = itemManager.getSpecialItems();
        for (SpecialItem specialItem : specialItems) {
            if (container.has(specialItem.getKey(), PersistentDataType.STRING) &&
                    container.get(specialItem.getKey(), PersistentDataType.STRING).equals(specialItem.getName())) {
                return true;
            }
        }

        return false;
    }

}
