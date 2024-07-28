package com.rainchat.testitems.utils.sturcute;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StructureBuilder {

    public static final Map<UUID, Map<Location, Material>> playerOriginalBlocks = new HashMap<>();

    public static boolean createObsidianStructure(Player player) {
        UUID playerId = player.getUniqueId();

        if (playerOriginalBlocks.containsKey(playerId) && !playerOriginalBlocks.get(playerId).isEmpty()) {
            player.sendMessage("Вы не можете использовать этот предмет, пока действует предыдущий.");
            return false;
        }

        StructureBuilderHelper builder = new StructureBuilderHelper(player, playerOriginalBlocks);
        boolean success = builder.createObsidianStructure();
        if (success) {
            BlockRestorer.scheduleBlockRestoration(playerId);
        }
        return success;
    }

    public static boolean createObsidianWall(Player player) {
        UUID playerId = player.getUniqueId();

        if (playerOriginalBlocks.containsKey(playerId) && !playerOriginalBlocks.get(playerId).isEmpty()) {
            player.sendMessage("Вы не можете использовать этот предмет, пока действует предыдущий.");
            return false;
        }

        StructureBuilderHelper builder = new StructureBuilderHelper(player, playerOriginalBlocks);
        boolean success = builder.createObsidianWall();
        if (success) {
            BlockRestorer.scheduleBlockRestoration(playerId);
        }
        return success;
    }

    public static void restoreAllOriginalBlocks() {
        for (UUID playerId : playerOriginalBlocks.keySet()) {
            Map<Location, Material> originalBlocks = playerOriginalBlocks.get(playerId);
            if (originalBlocks != null) {
                for (Map.Entry<Location, Material> entry : originalBlocks.entrySet()) {
                    Location loc = entry.getKey();
                    Material originalMaterial = entry.getValue();

                    Block block = loc.getBlock();
                    block.setType(originalMaterial);
                }
            }
        }
        playerOriginalBlocks.clear();
    }
}
