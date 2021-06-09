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

        fillBlocks(w, seCorner, nwCorner);

        for (int i = 0; i < roomList.length; i++) {
            coordinates = roomList[i].getCoordinates();

            w.getBlockAt(coordinates[0], 100, coordinates[1]).setType(Material.WHITE_CONCRETE);
        }

        return true;
    }

    public static boolean fillBlocks(World w, int[] seCorner, int[] nwCorner) {
        for (int x = nwCorner[0]; x <= seCorner[0]; x++) {
            for (int z = nwCorner[1]; z <= seCorner[1]; z++) {
                w.getBlockAt(x, 100, z).setType(Material.BLACK_CONCRETE);
            }
        }

        return true;
    }
}
