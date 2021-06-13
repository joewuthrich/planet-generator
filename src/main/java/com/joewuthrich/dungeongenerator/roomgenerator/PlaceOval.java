package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.layoutgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.layoutgenerator.objects.Room;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.PerlinNoiseGenerator;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import java.util.ArrayList;
import java.util.List;

public class PlaceOval {

    private static void setOvalBlocks(Coordinate coordinate, Material material, int lengthX, int lengthZ, int height) {
        World world = Bukkit.getServer().getWorld("world");
        assert world != null;

        int halfX = (int) Math.round(((double) lengthX / 2d));
        int halfY = (int) Math.round(((double) height / 2d));
        int halfZ = (int) Math.round(((double) lengthZ / 2d));

        List<Block> blocks = new ArrayList<>();

        for (int x = -halfX; x <= halfX; x++) {
            for (int y = -halfY; y <= halfY; y++) {
                for (int z = -halfZ; z <= halfZ; z++) {
                    double distance = ((double) (x * x)/(double) (halfX * halfX))+((double) (y * y)/(double) (halfY * halfY))+((double) (z * z)/(double) (halfZ * halfZ));
                    double noise = SimplexNoiseGenerator.getNoise(x, y, z);

                    if (distance <= 4 * noise) {
                        blocks.add(world.getBlockAt(x + coordinate.x, y + coordinate.y, z + coordinate.z));
                    }
                }
            }
        }

        for (Block b : blocks) {
            b.setType(Material.STONE);
        }
    }

    public static void generateOval(Room[] roomList, int height) {
        for (Room room : roomList)
            setOvalBlocks(room.getCenterCoordinate(), Material.STONE, room.lengthX, height, room.lengthZ);
    }
}
