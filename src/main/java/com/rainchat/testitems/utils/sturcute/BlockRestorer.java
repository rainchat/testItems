package com.rainchat.testitems.utils.sturcute;

import com.rainchat.testitems.testItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class BlockRestorer {

    public static void scheduleBlockRestoration(UUID playerId) {
        new BukkitRunnable() {
            @Override
            public void run() {
                restoreOriginalBlocks(playerId);
            }
        }.runTaskLater(testItems.getPlugin(testItems.class), 100L);
    }

    private static void restoreOriginalBlocks(UUID playerId) {
        Map<Location, Material> originalBlocks = StructureBuilder.playerOriginalBlocks.get(playerId);
        if (originalBlocks != null) {
            for (Map.Entry<Location, Material> entry : originalBlocks.entrySet()) {
                Block block = entry.getKey().getBlock();
                block.setType(entry.getValue());
            }
            originalBlocks.clear();
        }
    }
}
