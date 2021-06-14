package com.joewuthrich.planetgenerator.planet.objects;

import org.bukkit.Material;

import java.util.List;

public class Planet {
    Material o;
    Material u;
    Material[] c;

    List<PlanetBlock> blocks;

    int rad;
    int centerY;

    public Planet(List<PlanetBlock> _blocks, int radius, int _centerY) {
        blocks = _blocks;
        rad = radius;
        centerY = _centerY;
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
            if (b.getType() == 0)
                b.setBlockType(o);
            else if (b.getType() == 1)
                b.setBlockType(u);
            else if (b.getType() == 2) {
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
        }
    }
}
