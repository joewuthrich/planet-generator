package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;

import java.util.AbstractMap;

public class CollisionDetection {
    public static Room[] resolveCollisions(Room[] roomList, int[][] collisions, int circleX, int circleZ) {
        Room room1;
        Room room2;

        Coordinate coords1;
        Coordinate coords2;

        double distance1;
        double distance2;

        for (int[] collision : collisions) {
            if (collision == null)
                break;

            room1 = roomList[collision[0]];
            room2 = roomList[collision[1]];

            if (room1 == room2) {
                break;
            }

            coords1 = room1.getCoordinates();
            coords2 = room2.getCoordinates();

            distance1 = Math.sqrt(Math.pow((coords1.x - circleX), 2) + Math.pow((coords1.z - circleZ), 2));
            distance2 = Math.sqrt(Math.pow((coords2.x - circleX), 2) + Math.pow((coords2.z - circleZ), 2));

            if (distance1 < distance2) {
                roomList[collision[1]] = separateRooms(room2, circleX, circleZ, distance2);
            } else {
                roomList[collision[0]] = separateRooms(room1, circleX, circleZ, distance1);
            }
        }
        return roomList;
    }

    /**
     * Move a colliding room away from its collision
     * @param moveRoom the room to move
     * @param circleX the center X of the grid
     * @param circleZ the center Z of the grid
     * @param distance the distance away from the center the room is
     * @return the room at a new set of coordinates
     */
    public static Room separateRooms(Room moveRoom, int circleX, int circleZ, double distance) {
        Coordinate coordinates = moveRoom.getCoordinates();

        int newX = (int) Math.round(coordinates.x + (coordinates.x - circleX) / distance * 8);
        int newY = (int) Math.round(coordinates.z + (coordinates.z - circleZ) / distance * 8);

        moveRoom.setCoordinates(newX, newY);

        return moveRoom;
    }

    /**
     * Takes a list of rooms and returns the indexes in the list that have colliding rooms.
     * @param roomList the list of rooms
     * @return a pair an array of room indices in that list that collide and the number of collisions there were
     */
    public static AbstractMap.SimpleEntry<int[][], Integer> checkCollisions(Room[] roomList) {
        int numRooms = roomList.length;
        int[][] collisions = new int[maxCollisions(numRooms)][2];
        int numCollisions = 0;

        for (int i = 0; i < numRooms; i++) {
            for (int j = i + 1; j < numRooms; j++) {
                if (detectCollision(roomList[i], roomList[j])) {
                    collisions[numCollisions][0] = i;
                    collisions[numCollisions][1] = j;
                    numCollisions++;
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(collisions, numCollisions);
    }

    /**
     * Detect collisions between two rooms.
     * @param room1 The first room to test
     * @param room2 The second room to test
     * @return true if rooms collide, false if not
     */
    public static boolean detectCollision(Room room1, Room room2) {
        Coordinate c1 = room1.getCoordinates();
        Coordinate c2 = room2.getCoordinates();

        int l1 = room1.lengthX;
        int w1 = room1.lengthZ;
        int l2 = room2.lengthX;
        int w2 = room2.lengthZ;

        //  If there is a collision
        return (c1.x < c2.x + l2 && c1.x + l1 > c2.x && c1.z < c2.z + w2 && c1.z + w1 > c2.z);
    }

    /**
     * Get the maximum possible number of collisions given a number of rectangles
     * @param num an integer number
     * @return the maximum possible number of collisions as an integer
     */
    private static int maxCollisions(int num) {
        int total = 1;
        for (int i = 1; i < num; i++) {
            total += i;
        }

        return total;
    }
}