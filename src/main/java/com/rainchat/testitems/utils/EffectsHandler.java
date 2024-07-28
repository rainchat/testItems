package com.rainchat.testitems.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectsHandler {

    public static void applyPositiveEffects(Player player) {
        Location playerLocation = player.getLocation();

        for (Player nearbyPlayer : Bukkit.getOnlinePlayers()) {
            if (nearbyPlayer.getLocation().distance(playerLocation) <= 10) {
                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 1));
                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1));
                nearbyPlayer.sendMessage("Вы получили благословение!");
            }
        }
    }

    public static void applyNegativeEffects(Player player) {
        Location playerLocation = player.getLocation();

        for (Player nearbyPlayer : Bukkit.getOnlinePlayers()) {
            if (nearbyPlayer.getLocation().distance(playerLocation) <= 10) {
                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
                nearbyPlayer.sendMessage("Вы отравлены!");
            }
        }
    }
}
