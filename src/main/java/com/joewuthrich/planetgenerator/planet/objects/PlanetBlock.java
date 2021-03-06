package com.joewuthrich.planetgenerator.planet.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;

public class PlanetBlock {
    Block b;
    Material prev;
    int t;

    public PlanetBlock(Block block, Material previousMaterial, int type) {
        b = block;
        prev = previousMaterial;
        t = type;
    }

    /**
     * Get the type of the block: OVERLAY = 0, UNDERLAY = 1, SOLID = 2, AIR = 3, INSIDE_AIR = 4
     */
    public int getType() { return t; }

    /**
     * Set the type of the block: OVERLAY = 0, UNDERLAY = 1, SOLID = 2, AIR = 3, INSIDE_AIR = 4
     */
    public void setType(int type) { t = type; }

    public Block getBlock() { return b; }

    public void setBlockType(Material m) {
        prev = getBlock().getType();
        b.setType(m);
    }

    public void setBlockTypeNoWaterlog(Material m) {
        setBlockType(m);

        if (b.getBlockData() instanceof Waterlogged) {
            Waterlogged w = (Waterlogged) b.getBlockData();
            w.setWaterlogged(false);
            b.setBlockData(w);
        }
    }
}