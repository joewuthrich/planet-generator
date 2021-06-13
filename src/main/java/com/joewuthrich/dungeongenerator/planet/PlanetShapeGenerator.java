package com.joewuthrich.dungeongenerator.planet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlanetShapeGenerator {
    public static List<Block> generatePlanetShape(Block block, int rad) {
        final double FREQUENCY = 0.5, AMPLITUDE = 20d;
        final World WORLD = Bukkit.getServer().getWorld("world");
        assert WORLD != null;

        List<Block> blocks = new ArrayList<>();

        double xSeed = ThreadLocalRandom.current().nextDouble();
        double ySeed = ThreadLocalRandom.current().nextDouble();
        double zSeed = ThreadLocalRandom.current().nextDouble();

        int bx = block.getX(), by = block.getY(), bz = block.getZ();

        double distortion = FREQUENCY / 10;

        int radSqr = rad * rad;

        for (int x = -rad; x <= rad; x++) {
            double dx = xSeed + x * distortion;
            double xDist = x * x;

            for (int y = -rad; y <= rad; y++) {
                double dy = ySeed + y * distortion;
                double yDist = y * y + xDist;

                for (int z = -rad; z <= rad; z++) {
                    double dz = zSeed + z * distortion;
                    double dist = z * z + yDist;

                    double noise = AMPLITUDE * SimplexNoiseGenerator.getNoise(dx, dy, dz);

                    if (!(dist + dist * noise <= radSqr) && (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) < radSqr))
                        blocks.add(WORLD.getBlockAt(x + bx, y + by, z + bz));
                }
            }
        }

        return blocks;
    }
}
