package com.joewuthrich.dungeongenerator.roomgenerator.objects;

import org.checkerframework.checker.units.qual.C;

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
    boolean currentlyUsed = false;

    public Room(int _roomID, int _nwX, int _nwZ, int minRoomSize, int maxRoomSize) {
        roomID = _roomID;

        nwCorner = new Coordinate(_nwX, _nwZ);

        lengthX = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;
        lengthZ = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;

        size = lengthX * lengthZ;
    }

    public void setCoordinates(int newNorthWestX, int newNorthWestZ) {
        nwCorner.x = newNorthWestX;
        nwCorner.z = newNorthWestZ;
    }

    public Coordinate getCoordinates() {
        return nwCorner;
    }

    public void setUsed(boolean value) { currentlyUsed = value; }

    public boolean isUsed() { return currentlyUsed; }

    public int getSize() { return size; }
}

