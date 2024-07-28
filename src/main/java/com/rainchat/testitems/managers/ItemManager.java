package com.rainchat.testitems.managers;

import com.rainchat.testitems.items.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private List<SpecialItem> specialItems = new ArrayList<>();

    public ItemManager(JavaPlugin plugin) {
        setSpecialItems(new MagicNetherite(plugin));
        setSpecialItems(new SpecialKelp(plugin));
        setSpecialItems(new HolyBeacon(plugin));
        setSpecialItems(new PoisonVine(plugin));
    }

    public void setSpecialItems(SpecialItem specialItem) {
        this.specialItems.add(specialItem);
    }

    public List<SpecialItem> getSpecialItems() {
        return specialItems;
    }

    public void giveSpecialItems(Player player) {
        for (SpecialItem item : specialItems) {
            ItemStack stack = item.createItem();
            player.getInventory().addItem(stack);
            player.sendMessage("Вы получили " + item.getName() + "!");
        }
    }

    public void giveSpecialItemByName(Player player, String itemName) {
        for (SpecialItem item : specialItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                ItemStack stack = item.createItem();
                player.getInventory().addItem(stack);
                player.sendMessage("Вы получили " + item.getName() + "!");
                return;
            }
        }
        player.sendMessage("Предмет с именем " + itemName + " не найден.");
    }

    public List<String> getItemNames() {
        List<String> itemNames = new ArrayList<>();
        for (SpecialItem item : specialItems) {
            itemNames.add(item.getItemName());
        }
        return itemNames;
    }

}
