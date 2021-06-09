package com.joewuthrich.dungeongenerator.roomgenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The room object.
 *
 * Contains coordinates for it's bottom left corner, as well as height and width.
 */
public class Room {

    int bottomLeftX;
    int bottomLeftY;
    int height;
    int width;

    public Room(int newBottomLeftX, int newBottomLeftY, int minRoomSize, int maxRoomSize) {
        bottomLeftX = newBottomLeftX;
        bottomLeftY = newBottomLeftY;

        height = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;
        width = ThreadLocalRandom.current().nextInt(maxRoomSize - minRoomSize) + minRoomSize;
    }

    public void setCoordinates(int newBottomLeftX, int newBottomLeftY) {
        bottomLeftX = newBottomLeftX;
        bottomLeftY = newBottomLeftY;
    }

    public void setHeight(int newHeight) {
        height = newHeight;
    }

    public void setWidth(int newWidth) {
        width = newWidth;
    }

    public int[] getCoordinates() {
        return new int[] {bottomLeftX, bottomLeftY};
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

