package com.joewuthrich.dungeongenerator.planet;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

public class PlanetBlockPlacer {
    public static void placePlanetBlocks(List<Block> blocks, boolean gradient, int radius, int center) {

        Material[] mats = {Material.BLACK_CONCRETE, Material.COAL_BLOCK};

        if (!gradient) {
            for (Block b : blocks) {
                if (Math.random() > 0.5)
                    b.setType(mats[0]);
                else
                    b.setType(mats[1]);
            }
        }
        else {
            for (Block b : blocks) {
                int y = b.getY();
                if (y < center - (radius / 2))
                    b.setType(mats[0]);
                else if (y < center + (radius / 2)) {
                    double min = center - ((double) radius / 2), max = center + ((double) radius / 2);
                    double value = (y - min)/(max - min);

                    if (Math.random() > value)
                        b.setType(mats[0]);
                    else
                        b.setType(mats[1]);
                }
                else
                    b.setType(mats[1]);
            }
        }
    }
}
