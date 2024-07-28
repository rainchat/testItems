package com.rainchat.testitems.utils.wg;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorldGuardUtils {

    private static WorldGuardHandler worldGuardHandler;

    public static void initialize(Plugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            worldGuardHandler = new WorldGuardHandler(plugin);
        }
    }

    public static void onEnable(Plugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            worldGuardHandler.onEnable(plugin);
        }
    }

    public static boolean canBuild(Player player, Location loc) {
        if (worldGuardHandler == null || !worldGuardHandler.isWorldGuardLoaded()) {
            return true;
        }

        RegionContainer regionContainer = worldGuardHandler.getRegionManager();
        if (regionContainer == null) {
            return true;
        }

        com.sk89q.worldedit.util.Location weLocation = BukkitAdapter.adapt(loc);
        LocalPlayer localPlayer = worldGuardHandler.getWorldGuardPlugin().wrapPlayer(player);
        RegionQuery query = regionContainer.createQuery();

        return query.testState(weLocation, localPlayer, worldGuardHandler.getNoBuildFlag());
    }

}
