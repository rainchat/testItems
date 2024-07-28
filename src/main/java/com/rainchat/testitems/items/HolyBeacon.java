package com.rainchat.testitems.items;

import com.rainchat.testitems.utils.EffectsHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HolyBeacon extends SpecialItem {

    public HolyBeacon(JavaPlugin plugin) {
        super("holy_beacon", "Священный маяк", Material.BEACON, plugin);
    }

    @Override
    public boolean performAction(Player player) {
        EffectsHandler.applyPositiveEffects(player);
        return true;
    }
}
