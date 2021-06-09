package com.joewuthrich.dungeongenerator.roomgenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The room object.
 *
 * Contains coordinates for it's north-west corner (the smallest coordinates), as well as height and width.
 */
public class Room {

    public final int roomID;
    int northWestX;
    int northWestZ;
    int lengthX;
    int lengthZ;
    int size;
    boolean currentlyUsed = false;

    public Room(int newRoomID, int newNorthWestX, int newNorthWestZ, int minRoomSize, int maxRoomSize) {
        roomID = newRoomID;

        northWestX = newNorthWestX;
        northWestZ = newNorthWestZ;

        lengthX = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;
        lengthZ = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;

        size = lengthX * lengthZ;
    }

    public void setCoordinates(int newNorthWestX, int newNorthWestZ) {
        northWestX = newNorthWestX;
        northWestZ = newNorthWestZ;
    }

    public int[] getCoordinates() {
        return new int[] {northWestX, northWestZ};
    }

    public int getLengthX() {
        return lengthX;
    }

    public int getLengthY() {
        return lengthZ;
    }

    public void setUsed(boolean value) { currentlyUsed = value; }

    public boolean isUsed() { return currentlyUsed; }

    public int getSize() { return size; }
}

