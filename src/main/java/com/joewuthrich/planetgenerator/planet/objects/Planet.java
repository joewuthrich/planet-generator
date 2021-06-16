package com.joewuthrich.planetgenerator.planet.objects;

import com.joewuthrich.planetgenerator.planet.utils.GenerateName;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Planet {
    private final int OVERLAY = 0, UNDERLAY = 1, SOLID = 2, AIR = 3, INSIDE_AIR = 4;

    String name;

    Block centerBlock;

    Material o;
    Material u;
    Material[] c;
    Material cave;

    Biome biome;

    PlanetBlock[] blocks;

    int rad;

    /**
     * Generate a planet
     * @param block the center block
     * @param radius the radius of the planet
     */
    public Planet(Block block, int radius) {
        name = GenerateName.generateName();

        final double FREQUENCY = 0.5, AMPLITUDE = 20d;
        final World WORLD = block.getWorld();

        rad = radius;
        centerBlock = block;
        List<PlanetBlock> blocksList = new ArrayList<>();

        double xSeed = ThreadLocalRandom.current().nextDouble();
        double ySeed = ThreadLocalRandom.current().nextDouble();
        double zSeed = ThreadLocalRandom.current().nextDouble();

        int bx = centerBlock.getX(), by = centerBlock.getY(), bz = centerBlock.getZ();

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

                    Block b = WORLD.getBlockAt(x + bx, y + by, z + bz);

                    if ((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) < radSqr)) {
                        double noise = AMPLITUDE * SimplexNoiseGenerator.getNoise(dx, dy, dz);

                        if (!(dist + dist * noise <= radSqr))
                            blocksList.add(new PlanetBlock(b, Material.AIR, SOLID));
                        else
                            blocksList.add(new PlanetBlock(b, Material.AIR, INSIDE_AIR));
                    }
                    else
                        blocksList.add(new PlanetBlock(b, Material.AIR, AIR));
                }
            }
        }

        blocks = blocksList.toArray(new PlanetBlock[0]);
    }


    /**
     * Set the types of all the blocks in the planet
     * @param overlay the overlay material
     * @param underlay the underlay material
     * @param composition a list of blocks for the composition, with the lowest block first for gradient
     * @param texture the texture of the planet, either MIXED, GRADIENT or BLOB
     */
    public void baseMaterials(Material overlay, Material underlay, Material[] composition, Material _cave, Biome _biome, String texture) {
        o = overlay;
        u = underlay;
        c = composition;
        cave = _cave;
        biome = _biome;

        setTypes();

        double bound = 1d / (double) c.length;
        double length = rad * 2 + 1, bounds = length / (double) (3 * c.length - 1);
        int minY = centerBlock.getY() - rad;

        if (texture.equalsIgnoreCase("BLOB")) {
            final double FREQUENCY = 1, AMPLITUDE = 10d;
            final World WORLD = centerBlock.getWorld();

            int bx = centerBlock.getX(), by = centerBlock.getY(), bz = centerBlock.getZ();

            double distortion = FREQUENCY / 10;

            int radSqr = rad * rad;

            for (PlanetBlock b : blocks) {
                if (b.getType() == SOLID)
                    b.setBlockType(c[0]);
            }

            for (int i = 1; i < c.length; i++) {
                double xSeed = ThreadLocalRandom.current().nextDouble();
                double ySeed = ThreadLocalRandom.current().nextDouble();
                double zSeed = ThreadLocalRandom.current().nextDouble();

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

                            if (!(dist + dist * noise <= radSqr) && (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) < radSqr)) {
                                int index = getIndex(b);
                                if (blocks[index].getType() == SOLID)
                                    blocks[index].setBlockType(c[i]);
                            }
                        }
                    }
                }
            }
        }

        for (PlanetBlock b : blocks) {
            b.getBlock().setBiome(biome);

            if (b.getType() == OVERLAY)
                b.setBlockType(o);
            else if (b.getType() == UNDERLAY)
                b.setBlockType(u);
            else if (b.getType() == SOLID) {
                if (texture.equalsIgnoreCase("MIXED")) {
                    double rand = Math.random();

                    for (int i = 1; i <= c.length; i++) {
                        if (rand > bound * (i - 1) && rand < bound * i) {
                            b.setBlockType(c[i - 1]);
                            break;
                        }
                    }
                }
                else if (texture.equalsIgnoreCase("GRADIENT")) {
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
                                        b.setBlockType(c[(int) ((i + 1.7) / 3d)]);
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
            else if (b.getType() == INSIDE_AIR)
                b.setBlockType(cave);
            else if (b.getType() == AIR)
                b.setBlockType(Material.AIR);

        }
    }

    /**
     * Set the types of the overlay and underlay blocks
     */
    private void setTypes() {
        for (int i = blocks.length - 1; i >= 0; i--) {
            if (i + (rad * 2 + 1) < blocks.length) {
                int t = blocks[i + (rad * 2 + 1)].getType();
                if (t == AIR && blocks[i].getType() == SOLID)
                    blocks[i].setType(OVERLAY);
                else if (t == OVERLAY)
                    blocks[i].setType(UNDERLAY);
                else if (t == INSIDE_AIR && blocks[i].getType() == SOLID && cave == Material.AIR)
                    blocks[i].setType(OVERLAY);
            }
            else if (blocks[i].getType() == SOLID)
                blocks[i].setType(OVERLAY);
        }
    }

    /**
     * Get the array index of a specified block in the region
     */
    public int getIndex(Block block) {
        int diffX = Math.abs(block.getX() - (centerBlock.getX() - rad));
        int diffY = Math.abs(block.getY() - (centerBlock.getY() - rad));
        int diffZ = Math.abs(block.getZ() - (centerBlock.getZ() - rad));

        int dist = 1 + rad * 2;

        return diffZ + (diffY * dist) + (diffX * dist * dist);
    }

    /**
     * Get the name of the planet
     */
    public String getName() { return name; }
}
