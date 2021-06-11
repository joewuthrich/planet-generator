package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;

public class GenerateRooms {
    /**
     *
     * @param roomCount The number of rooms to generate
     * @param radius The radius of the circle to generate the rooms in
     * @param circleX The x coordinate of the center of the circle
     * @param circleY The y coordinate of the center of the circle
     * @param minRoomLength The minimum length of a wall in a room
     * @param maxRoomLength The maximum length of a wall in a room
     * @return A list of rooms in the circle with the specified criteria
     */
    public static Room[] generateRooms(int roomCount, int radius, int circleX, int circleY, int minRoomLength, int maxRoomLength) {
        Room[] roomList = new Room[roomCount];
        Coordinate coordinates;

        for (int i = 0; i < roomList.length; i++) {
            coordinates = getCoordinate(radius, circleX, circleY);
            roomList[i] = new Room(i, coordinates.x, coordinates.z, minRoomLength, maxRoomLength);
        }

        return roomList;
    }

    private static Coordinate getCoordinate(int radius, int circleX, int circleY) {
        double len = Math.sqrt(Math.random()) * radius;
        double deg = Math.random() * 2 * Math.PI;
        int x = (int) Math.round(circleX + len * Math.cos(deg));
        int y = (int) Math.round(circleY + len * Math.sin(deg));
        return new Coordinate(x, y);
    }
}
