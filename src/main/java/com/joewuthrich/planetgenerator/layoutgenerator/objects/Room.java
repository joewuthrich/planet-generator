package com.joewuthrich.planetgenerator.layoutgenerator.objects;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The room object.
 *
 * Contains coordinates for it's north-west corner (the smallest coordinates), as well as height and width.
 */
public class Room {

    public final int roomID;
    Coordinate nwCorner;
    public int lengthX;
    public int lengthZ;
    int size;
    Coordinate center;

    public Room(int _roomID, int _nwX, int _nwZ, int minRoomSize, int maxRoomSize) {
        roomID = _roomID;

        nwCorner = new Coordinate(_nwX, _nwZ);

        lengthX = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;
        lengthZ = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;

        size = lengthX * lengthZ;

        center = new Coordinate((int) Math.round(_nwX + ((double) lengthX / 2d)), (int) Math.round(_nwZ + ((double) lengthZ / 2d)));
    }

    public Room(int _roomID, int _nwX, int _nwY, int _nwZ, int minRoomSize, int maxRoomSize) {
        roomID = _roomID;

        nwCorner = new Coordinate(_nwX, _nwZ);

        lengthX = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;
        lengthZ = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;

        size = lengthX * lengthZ;

        center = new Coordinate((int) Math.round(_nwX + ((double) lengthX / 2d)), _nwY, (int) Math.round(_nwZ + ((double) lengthZ / 2d)));
    }

    public void setCoordinates(int _nwX, int _nwZ) {
        nwCorner.x = _nwX;
        nwCorner.z = _nwZ;

        center = new Coordinate((int) Math.round(_nwX + ((double) lengthX / 2d)), (int) Math.round(_nwZ + ((double) lengthZ / 2d)));
    }

    public Coordinate getCoordinates() {
        return nwCorner;
    }

    public Coordinate getCenterCoordinate() {
        return center;
    }

    public Block getCenterBlock() {
        return Bukkit.getServer().getWorld("world").getBlockAt(center.x, center.y, center.z);
    }
}

