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

        double f = 1.0 - Math.random()/Math.nextDown(1.0);
        for (Room room : roomList) {
            List<Block> blocks = getBlob(room.getCenterBlock(), room.lengthX,
                    (int) Math.round((height - (double) height / 3) * (1.0 - f) +
                            (height + (double) height / 3) * f), room.lengthZ);

            for (Block b : blocks)
                b.setType(Material.STONE);
        }
    }

    private static List<Block> getBlob(Block block, int lengthX, int lengthY, int lengthZ) {
        final double FREQUENCY = 0.3d, AMPLITUDE = 0.5d;
        final World WORLD = Bukkit.getServer().getWorld("world");
        assert WORLD != null;

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
                    if (dist + dist * noise <= radSqr)
                        blocks.add(WORLD.getBlockAt(x + bx, y + by, z + bz));
                }
            }
        }

        return blocks;
    }
}
