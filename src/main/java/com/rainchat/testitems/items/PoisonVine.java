package com.rainchat.testitems.items;

import com.rainchat.testitems.utils.EffectsHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PoisonVine extends SpecialItem {

    public PoisonVine(JavaPlugin plugin) {
        super("poison_vine", "Отравленная лоза", Material.VINE, plugin);
    }

    @Override
    public boolean performAction(Player player) {
        EffectsHandler.applyNegativeEffects(player);
        return true;
    }
}
