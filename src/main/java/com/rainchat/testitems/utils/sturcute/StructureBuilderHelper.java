package com.rainchat.testitems.utils.sturcute;

import com.rainchat.testitems.utils.wg.WorldGuardUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class StructureBuilderHelper {

    private static final Random random = new Random();
    private final Player player;
    private final Location baseLocation;
    private final int width = 5;
    private final int height = 5;
    private final int radius = width / 2;
    private final Map<UUID, Map<Location, Material>> playerOriginalBlocks;

    public StructureBuilderHelper(Player player, Map<UUID, Map<Location, Material>> playerOriginalBlocks) {
        this.player = player;
        this.baseLocation = player.getLocation().subtract(0, 1, 0);
        this.playerOriginalBlocks = playerOriginalBlocks;
    }

    private void buildStructure(Map<Location, Material> blocksToChange) {
        Map<Location, Material> originalBlocks = new HashMap<>();
        for (Location blockLoc : blocksToChange.keySet()) {
            Block block = blockLoc.getBlock();
            originalBlocks.put(blockLoc, block.getType());

            Material material = random.nextBoolean() ? Material.OBSIDIAN : Material.CRYING_OBSIDIAN;

            if (shouldPlaceObsidianBlock(blockLoc)) {
                if (blockLoc.getY() == baseLocation.getY() + height - 1) {
                    if (isCrossPattern(blockLoc)) {
                        block.setType(Material.OBSIDIAN);
                    } else {
                        block.setType(Material.AIR);
                    }
                } else {
                    block.setType(material);
                }
            } else {
                block.setType(Material.AIR);
            }

            if (blockLoc.getY() == baseLocation.getY() + 1 && shouldPlaceSlab(blockLoc)) {
                if (isAlternatingPattern(blockLoc, 1)) {
                    block.setType(Material.POLISHED_BLACKSTONE_BRICK_SLAB);
                    Slab slab = (Slab) block.getBlockData();
                    slab.setType(Slab.Type.TOP);
                    block.setBlockData(slab);
                } else {
                    block.setType(Material.OBSIDIAN);
                }
            }
        }

        playerOriginalBlocks.put(player.getUniqueId(), originalBlocks);
    }

    private void buildWall(int roundedYaw) {
        Location loc = player.getLocation().clone().add(player.getLocation().getDirection().normalize().multiply(2));

        Map<Location, Material> originalBlocks = new HashMap<>();
        for (int y = 0; y < height; y++) {
            for (int i = -radius; i <= radius; i++) {
                Location blockLoc = loc.clone();
                blockLoc.add(rotateVector(new Vector(i, y, 0), roundedYaw));
                Block block = blockLoc.getBlock();
                originalBlocks.put(blockLoc, block.getType());

                Material material = random.nextBoolean() ? Material.OBSIDIAN : Material.CRYING_OBSIDIAN;

                if ((i == -radius || i == radius) && (y == 0 || y == height - 1)) {
                    block.setType(Material.CRYING_OBSIDIAN);
                } else {
                    block.setType(material);
                }
            }
        }

        playerOriginalBlocks.put(player.getUniqueId(), originalBlocks);
    }

    public boolean createObsidianStructure() {
        Map<Location, Material> originalBlocks = determineBlocksToChange();
        if (originalBlocks == null) {
            return false;
        }

        buildStructure(originalBlocks);
        return true;
    }

    public boolean createObsidianWall() {
        float yaw = player.getLocation().getYaw();
        int roundedYaw = Math.round(yaw / 45) * 45;
        roundedYaw = (roundedYaw % 360 + 360) % 360;

        Map<Location, Material> originalBlocks = determineBlocksToChange();
        if (originalBlocks == null) {
            return false;
        }

        buildWall(roundedYaw);
        return true;
    }

    private Map<Location, Material> determineBlocksToChange() {
        Map<Location, Material> blocksToChange = new HashMap<>();
        for (int y = 0; y < height; y++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    Location blockLoc = baseLocation.clone().add(x, y, z);
                    Block block = blockLoc.getBlock();

                    if (shouldPlaceObsidianBlock(blockLoc) || (blockLoc.getY() == baseLocation.getY() + 1 && shouldPlaceSlab(blockLoc))) {
                        blocksToChange.put(blockLoc, block.getType());
                    }
                }
            }
        }

        if (!checkWorldGuardRegions(blocksToChange)) {
            player.sendMessage("Не удалось поставить блоки. Один или несколько блоков пересекаются с защищенным регионом.");
            return null;
        }

        return blocksToChange;
    }

    private boolean checkWorldGuardRegions(Map<Location, Material> blocksToChange) {
        for (Location loc : blocksToChange.keySet()) {
            if (!WorldGuardUtils.canBuild(player, loc)) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldPlaceObsidianBlock(Location loc) {
        int y = loc.getBlockY() - baseLocation.getBlockY();
        int x = loc.getBlockX() - baseLocation.getBlockX();
        int z = loc.getBlockZ() - baseLocation.getBlockZ();
        return y == 0 || y == height - 1 || Math.abs(x) == radius || Math.abs(z) == radius;
    }

    private boolean shouldPlaceSlab(Location loc) {
        int x = loc.getBlockX() - baseLocation.getBlockX();
        int z = loc.getBlockZ() - baseLocation.getBlockZ();
        return Math.abs(x) == radius || Math.abs(z) == radius;
    }

    private boolean isCrossPattern(Location loc) {
        int x = loc.getBlockX() - baseLocation.getBlockX();
        int z = loc.getBlockZ() - baseLocation.getBlockZ();
        return x == 0 || z == 0;
    }

    private boolean isAlternatingPattern(Location loc, int phaseShift) {
        int x = loc.getBlockX() - baseLocation.getBlockX();
        int z = loc.getBlockZ() - baseLocation.getBlockZ();
        return ((x + z + phaseShift) % 2 == 0);
    }

    private Vector rotateVector(Vector vector, int rotationAngle) {
        double radians = Math.toRadians(rotationAngle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double x = vector.getX() * cos - vector.getZ() * sin;
        double z = vector.getX() * sin + vector.getZ() * cos;
        return new Vector(x, vector.getY(), z);
    }

}
