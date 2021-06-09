package com.joewuthrich.dungeongenerator.roomgenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The room object.
 *
 * Contains coordinates for it's north-west corner (the smallest coordinates), as well as height and width.
 */
public class Room {

    int northWestX;
    int northWestZ;
    int lengthX;
    int lengthZ;

    public Room(int newNorthWestX, int newNorthWestZ, int minRoomSize, int maxRoomSize) {
        northWestX = newNorthWestX;
        northWestZ = newNorthWestZ;

        lengthX = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;
        lengthZ = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;
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
}

