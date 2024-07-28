package com.rainchat.testitems.items;

import com.rainchat.testitems.utils.sturcute.StructureBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicNetherite extends SpecialItem {

    public MagicNetherite(JavaPlugin plugin) {
        super("magic_nether", "Клетка", Material.NETHERITE_SCRAP, plugin);
    }

    @Override
    public boolean performAction(Player player) {
        return StructureBuilder.createObsidianStructure(player);
    }
}
