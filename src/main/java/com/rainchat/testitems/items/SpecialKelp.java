package com.rainchat.testitems.items;

import com.rainchat.testitems.utils.sturcute.StructureBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpecialKelp extends SpecialItem {

    public SpecialKelp(JavaPlugin plugin) {
        super("plast", "Пласт", Material.DRIED_KELP, plugin);
    }

    @Override
    public boolean performAction(Player player) {
        return StructureBuilder.createObsidianWall(player);
    }
}
