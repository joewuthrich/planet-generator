package com.joewuthrich.dungeongenerator.planet;

import org.bukkit.World;
import org.bukkit.block.Block;

public class PlanetBlock {
    int x;
    int y;
    int z;
    World w;

    PlanetBlock blockAbove;
    PlanetBlock blockXPlus;
    PlanetBlock blockXMinus;
    PlanetBlock blockYPlus;
    PlanetBlock blockYMinus;
    PlanetBlock blockBelow;

    public PlanetBlock(World _w, int _x, int _y, int _z) {
        x = _x;
        y = _y;
        z = _z;
        w = _w;

        blockAbove = new PlanetBlock(w.getBlockAt(x, y + 1, z));

    }

    public PlanetBlock(Block block) {
        x = block.getX();
        y = block.getY();
        z = block.getZ();
        w = block.getWorld();


    }
}
