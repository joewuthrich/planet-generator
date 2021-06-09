package com.joewuthrich.dungeongenerator.placeblocks;

import com.joewuthrich.dungeongenerator.roomgenerator.Room;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.checkerframework.checker.units.qual.min;

public class PlaceBlocks {
    public static boolean placeBlocks(Room[] roomList, int[] seCorner, int[] nwCorner) {

        World w = Bukkit.getServer().getWorld("world");
        int[] coordinates;
        int length;
        int width;

        fillBlocks(w, seCorner, nwCorner);

        for (int i = 0; i < roomList.length; i++) {
            coordinates = roomList[i].getCoordinates();
            length = roomList[i].getHeight();
            width = roomList[i].getWidth();

            for (int x = coordinates[0]; x < coordinates[0] + length; x++) {
                for (int y = coordinates[1]; y < coordinates[1] + width; y++) {
                    if (w.getBlockAt(x, 100, y).getType() == Material.BLACK_CONCRETE)
                        w.getBlockAt(x, 100, y).setType(Material.WHITE_CONCRETE);
                    else
                        w.getBlockAt(x, 100, y).setType(Material.RED_CONCRETE);
                }
            }
        }

        return true;
    }

    public static boolean fillBlocks(World w, int[] seCorner, int[] nwCorner) {

        int extraSize = 40;

        for (int x = nwCorner[0] - extraSize; x <= seCorner[0] + extraSize; x++) {
            for (int z = nwCorner[1] - extraSize; z <= seCorner[1] + extraSize; z++) {
                w.getBlockAt(x, 100, z).setType(Material.BLACK_CONCRETE);
            }
        }

        return true;
    }
}
