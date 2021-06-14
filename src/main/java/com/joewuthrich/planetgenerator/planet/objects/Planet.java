package com.joewuthrich.planetgenerator.planet.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Planet {
    private final int OVERLAY = 0, UNDERLAY = 1, SOLID = 2, AIR = 3;

    Material o;
    Material u;
    Material[] c;

    Biome biome;

    List<PlanetBlock> blocks;

    int rad;
    int centerY;

    /**
     * Generate a planet
     * @param block the center block
     * @param radius the radius of the planet
     */
    public Planet(Block block, int radius) {
        final double FREQUENCY = 0.5, AMPLITUDE = 20d;
        final World WORLD = block.getWorld();

        rad = radius;

        blocks = new ArrayList<>();

        double xSeed = ThreadLocalRandom.current().nextDouble();
        double ySeed = ThreadLocalRandom.current().nextDouble();
        double zSeed = ThreadLocalRandom.current().nextDouble();

        int bx = block.getX(), by = block.getY(), bz = block.getZ();

        centerY = by;

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

                    Block b = WORLD.getBlockAt(x + bx, y + by, z + bz);

                    if (!(dist + dist * noise <= radSqr) && (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) < radSqr))
                        blocks.add(new PlanetBlock(b, Material.BLACK_CONCRETE, SOLID));
                    else
                        blocks.add(new PlanetBlock(b, Material.AIR, AIR));
                }
            }
        }
    }


    /**
     * Set the types of all the blocks in the planet
     * @param overlay the overlay material
     * @param underlay the underlay material
     * @param composition a list of blocks for the composition, with the lowest block first for gradient
     * @param gradient if it should be gradient or just mixed
     */
    public void baseMaterials(Material overlay, Material underlay, Material[] composition, boolean gradient) {
        o = overlay;
        u = underlay;
        c = composition;

        for (int i = blocks.size() - 1; i >= 0; i--) {
            if (i + (rad * 2 + 1) < blocks.size()) {
                int t = blocks.get(i + (rad * 2 + 1)).getType();
                if (t == 3 && blocks.get(i).getType() == 2)
                    blocks.get(i).setType(0);
                else if (t == 0)
                    blocks.get(i).setType(1);
            }
            else if (blocks.get(i).getType() == 2)
                blocks.get(i).setType(0);
        }

        double bound = 1d / (double) c.length;
        double length = rad * 2 + 1, bounds = length / (double) (3 * c.length - 1);
        int minY = centerY - rad;

        for (PlanetBlock b : blocks) {
            if (b.getType() == OVERLAY)
                b.setBlockType(o);
            else if (b.getType() == UNDERLAY)
                b.setBlockType(u);
            else if (b.getType() == SOLID) {
                if (!gradient) {
                    double rand = Math.random();

                    for (int i = 1; i <= c.length; i++) {
                        if (rand > bound * (i - 1) && rand < bound * i) {
                            b.setBlockType(c[i - 1]);
                            break;
                        }
                    }
                }
                else {
                    int y = b.getBlock().getY();

                    for (int i = 0; i < 3 * c.length - 1; i++) {
                        double min, max;
                        if (i % 3 == 0) {
                            min = minY + (bounds * i);
                            max = minY + (bounds * (i + 1));
                        }
                        else if (i % 3 == 1) {
                            min = minY + (bounds * i);
                            max = minY + (bounds * (i + 2));
                        }
                        else {
                            min = minY + (bounds * (i - 1));
                            max = minY + (bounds * (i + 1));
                        }

                        if (y > min && y <= max) {
                            if ((i) % 3 == 0)
                                b.setBlockType(c[(int) ((i) / 3d)]);
                            else {
                                double value = (y - min) / (max - min);

                                if (i % 3 == 1) {
                                    if (Math.random() > value)
                                        b.setBlockType(c[(int) ((i - 1) / 3d)]);
                                    else
                                        b.setBlockType(c[(int) ((i - 1) / 3d)]);
                                }
                                else {
                                    if (Math.random() > value)
                                        b.setBlockType(c[(int) ((i - 2) / 3d)]);
                                    else
                                        b.setBlockType(c[(int) ((i + 1) / 3d)]);
                                }
                            }
                        }
                    }
                }
            }
            else if (b.getType() == AIR) {
                b.setBlockType(Material.AIR);
            }
        }
    }
}
