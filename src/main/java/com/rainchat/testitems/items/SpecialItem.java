package com.rainchat.testitems.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SpecialItem {

    private final String itemName;
    private final String name;
    private final Material material;
    private final NamespacedKey key;

    public SpecialItem(String itemName, String name, Material material, JavaPlugin plugin) {
        this.name = name;
        this.itemName = itemName;
        this.material = material;
        this.key = new NamespacedKey(plugin, "unique_item_name");
    }

    public String getName() {
        return name;
    }

    public String getItemName() {
        return itemName;
    }

    public ItemStack createItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.STRING, name);
        item.setItemMeta(meta);
        return item;
    }

    public abstract boolean performAction(Player player);

    public NamespacedKey getKey() {
        return key;
    }
}
