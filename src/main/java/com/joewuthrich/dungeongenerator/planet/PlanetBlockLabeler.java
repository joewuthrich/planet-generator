package com.joewuthrich.dungeongenerator.planet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;
import java.util.Objects;

public class PlanetBlockLabeler {
    public static void labelBlock(List<Block> blocks) {
        Material overlay = Material.BLACK_CONCRETE_POWDER,
                underlay = Material.BLACK_WOOL;

        for (Block b : blocks) {
            if (Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getBlockAt(b.getX(), b.getY() + 1, b.getZ()).getType() == Material.AIR) {
                b.setType(overlay);
            }
        }

        for (Block b : blocks) {
            if (Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getBlockAt(b.getX(), b.getY() + 1, b.getZ()).getType() == overlay) {
                b.setType(underlay);
            }
        }
    }
}
