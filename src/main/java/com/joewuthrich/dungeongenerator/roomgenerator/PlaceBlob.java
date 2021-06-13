package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.layoutgenerator.objects.Room;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlaceBlob {

    public static void generateBlobs(Room[] roomList, int height) {
        for (Room room : roomList) {
            List<Block> blocks = getBlob(room.getCenterBlock(), Material.STONE, room.lengthX, height, room.lengthZ);

            for (Block b : blocks) {
                b.setType(Material.STONE);
            }
        }
    }

    private static List<Block> getBlob(Block block, Material material, int lengthX, int lengthY, int lengthZ) {
        final double FREQUENCY = 0.3d, AMPLITUDE = 0.5d;
        final World WORLD = Bukkit.getServer().getWorld("world");

        List<Block> blocks = new ArrayList<>();

        double xSeed = ThreadLocalRandom.current().nextDouble();
        double ySeed = ThreadLocalRandom.current().nextDouble();
        double zSeed = ThreadLocalRandom.current().nextDouble();

        double max = Math.max(lengthX, Math.max(lengthY, lengthZ));

        double size = max / 2d;

        double rx = ((double) lengthX) / max, ry = ((double) lengthY) / max,
                rz = ((double) lengthZ) / max;

        int bx = block.getX(), by = block.getY(), bz = block.getZ();

        double distortion = FREQUENCY / size;

        double xDiff = 1d / rx, yDiff = 1d / ry, zDiff = 1d / rz;

        int rad = (int) size, radSqr = rad * rad, iterations = rad * 2;

        for (int x = -iterations; x <= iterations; x++) {
            double dx = xSeed + x * distortion;
            double xDist = x * x * xDiff;

            for (int y = -iterations; y <= iterations; y++) {
                double dy = ySeed + y * distortion;
                double yDist = y * y * yDiff + xDist;

                for (int z = -iterations; z <= iterations; z++) {
                    double dz = zSeed + z * distortion;
                    double dist = z * z * zDiff + yDist;

                    double noise = AMPLITUDE * SimplexNoiseGenerator.getNoise(dx, dy, dz);
                    if (dist + dist * noise < radSqr) {
                        blocks.add(WORLD.getBlockAt(x + bx, y + by, z + bz));
                    }

                }

            }

        }

        /*
        final double FREQUENCY = 30d;
        final double AMPLITUDE = 50d;
        final World WORLD = Bukkit.getServer().getWorld("world");

        List<Block> blocks = new ArrayList<>();

        double xSeed = ThreadLocalRandom.current().nextDouble();
        double ySeed = ThreadLocalRandom.current().nextDouble();
        double zSeed = ThreadLocalRandom.current().nextDouble();

        double distortion = FREQUENCY / (((double) lengthX + (double) lengthZ) / 2d);

        int bx = block.getX();
        int by = block.getY();
        int bz = block.getZ();

        int halfX = (int) Math.round(((double) lengthX / 2d));
        int halfY = (int) Math.round(((double) lengthY / 2d));
        int halfZ = (int) Math.round(((double) lengthZ / 2d));

        double modX = 1d / (bx) ;
        double modY = 1d / (by);
        double modZ = 1d / (bz);

        for (int x = -lengthX; x <= lengthX; x++) {
            double nx = xSeed + x * distortion;
            double d1 = x * x * modX;
            for (int y = -lengthX; y <= lengthX; y++) {
                double d2 = d1 + y * y * modY;
                double ny = ySeed + y * distortion;
                for (int z = -lengthX; z <= lengthX; z++) {
                    double nz = zSeed + z * distortion;
                    double distance = d2 + z * z * modZ;
                    double noise = AMPLITUDE * SimplexNoiseGenerator.getNoise(nx, ny, nz);
                    if (distance + distance * noise < halfX * halfX) {
                        blocks.add(WORLD.getBlockAt(x + bx, y + by, z + bz));
                    }
                }
            }
        }
*/
        /*
        for (int x = -halfX; x <= halfX; x++) {
            double distX = xSeed + x * distortion;
            double dx = x * x * modX;
            for (int y = -halfY; y <= halfY; y++) {
                double distY = ySeed + y * distortion;
                double dy = y * y * modY;
                for (int z = -halfZ; z <= halfZ; z++) {
                    double distZ = zSeed + z * distortion;
                    double dz = z * z * modZ;
                    double noise = AMPLITUDE * SimplexNoiseGenerator.getNoise(x, y, z);
                    double distance = ((Math.pow(x + distX, 2)/(double) (halfX * halfX))+(Math.pow(y + distY, 2)/(double) (halfY * halfY))+(Math.pow(z + dz, 2)/(double) (halfZ * halfZ)));

                    if (distance <= 2 * noise) {
                        blocks.add(WORLD.getBlockAt(bx + x, by + y, bz + z));
                    }
                }
            }
        }*/

        return blocks;
    }
}
