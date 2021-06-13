package com.joewuthrich.dungeongenerator.placeblocks;

import com.joewuthrich.dungeongenerator.layoutgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.layoutgenerator.objects.Room;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

public class PlaceBlocks {
    public static void placeBlocks(Room[] roomList) {
        placeBlocks(roomList, new Coordinate(0, 0), new Coordinate(0, 0));
    }

    public static void placeBlocks(Room[] roomList, Coordinate seCorner, Coordinate nwCorner) {

        World w = Bukkit.getServer().getWorld("world");
        assert w != null;

        Coordinate coordinates;
        int length;
        int width;

        //fillBlocks(w, seCorner, nwCorner);

        for (Room room : roomList) {
            coordinates = room.getCoordinates();
            length = room.lengthX;
            width = room.lengthZ;

            for (int x = coordinates.x; x < coordinates.x + length; x++) {
                for (int y = coordinates.z; y < coordinates.z + width; y++) {
                    w.getBlockAt(x, 100, y).setType(Material.WHITE_CONCRETE);
                }
            }
        }
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