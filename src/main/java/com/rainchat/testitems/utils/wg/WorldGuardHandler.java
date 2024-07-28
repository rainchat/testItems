package com.rainchat.testitems.utils.wg;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class WorldGuardHandler {

    private WorldGuardPlugin worldGuardPlugin;
    private WorldGuard worldGuard;
    private RegionContainer regionManager;
    private StateFlag NO_BUILD_FLAG;

    public WorldGuardHandler(Plugin plugin) {
        initialize(plugin);
    }

    private void initialize(Plugin plugin) {
        worldGuardPlugin = (WorldGuardPlugin) plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        if (worldGuardPlugin != null) {
            worldGuard = WorldGuard.getInstance();
            FlagRegistry flagRegistry = worldGuard.getFlagRegistry();
            try {
                NO_BUILD_FLAG = new StateFlag("test-items-no-build", true);
                flagRegistry.register(NO_BUILD_FLAG);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getLogger().warning("WorldGuard plugin not found. Disabling WorldGuard related features.");
        }
    }

    public void onEnable(Plugin plugin) {
        if (worldGuardPlugin != null) {
            regionManager = worldGuard.getPlatform().getRegionContainer();
        }
    }

    public WorldGuardPlugin getWorldGuardPlugin() {
        return worldGuardPlugin;
    }

    public WorldGuard getWorldGuard() {
        return worldGuard;
    }

    public RegionContainer getRegionManager() {
        return regionManager;
    }

    public StateFlag getNoBuildFlag() {
        return NO_BUILD_FLAG;
    }

    public boolean isWorldGuardLoaded() {
        return worldGuardPlugin != null;
    }
}
