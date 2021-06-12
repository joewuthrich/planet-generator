package com.joewuthrich.dungeongenerator.placeblocks;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

public class PlaceBlocks {
    public static boolean placeBlocks(Room[] roomList, Coordinate seCorner, Coordinate nwCorner) {

        World w = Bukkit.getServer().getWorld("world");
        assert w != null;

        Coordinate coordinates;
        int length;
        int width;

        fillBlocks(w, seCorner, nwCorner);

        for (Room room : roomList) {
            coordinates = room.getCoordinates();
            length = room.lengthX;
            width = room.lengthZ;

            for (int x = coordinates.x; x < coordinates.x + length; x++) {
                for (int y = coordinates.z; y < coordinates.z + width; y++) {
                    if (w.getBlockAt(x, 100, y).getType() == Material.BLACK_CONCRETE)
                        w.getBlockAt(x, 100, y).setType(Material.WHITE_CONCRETE);
                    else
                        w.getBlockAt(x, 100, y).setType(Material.RED_CONCRETE);
                }
            }
        }

        return true;
    }

    public static void fillBlocks(World w, Coordinate seCorner, Coordinate nwCorner) {
        int extraSize = 50;

        for (int x = nwCorner.x - extraSize; x <= seCorner.x + extraSize; x++) {
            for (int z = nwCorner.z - extraSize; z <= seCorner.z + extraSize; z++) {
                w.getBlockAt(x, 100, z).setType(Material.BLACK_CONCRETE);
            }
        }
    }
}