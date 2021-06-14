package com.joewuthrich.dungeongenerator.planet.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class PlanetBlock {
    private final int OVERLAY = 0, UNDERLAY = 1, SOLID = 2, AIR = 3;

    Block b;
    Material prev;
    int t;

    public PlanetBlock(Block block, Material previousMaterial, int type) {
        b = block;
        prev = previousMaterial;
        t = type;
    }

    /**
     * Get the type of the block: OVERLAY = 0, UNDERLAY = 1, SOLID = 2, AIR = 3
     */
    public int getType() { return t; }

    /**
     * Set the type of the block: OVERLAY = 0, UNDERLAY = 1, SOLID = 2, AIR = 3
     */
    public void setType(int type) { t = type; }

    public Block getBlock() { return b; };

    public void setBlockType(Material m) { b.setType(m); }
}
