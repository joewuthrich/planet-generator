package com.joewuthrich.dungeongenerator.roomgenerator;

import java.util.AbstractMap;

public class CollisionDetection {
    public static Room[] resolveCollisions(Room[] roomList, int[][] collisions, int circleX, int circleY) {
        Room room1;
        Room room2;

        int[] coords1;
        int[] coords2;

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

            distance1 = Math.sqrt(((coords1[0] - circleX) * (coords1[0] - circleX)) + ((coords1[1] - circleY) * (coords1[1] - circleY)));
            distance2 = Math.sqrt(((coords2[0] - circleX) * (coords2[0] - circleX)) + ((coords2[1] - circleY) * (coords2[1] - circleY)));

            if (distance1 < distance2) {
                roomList[collision[1]] = seperateRooms(room2, circleX, circleY, distance2);
            } else {
                roomList[collision[0]] = seperateRooms(room1, circleX, circleY, distance1);
            }
        }
        return roomList;
    }

    /**
     * Move a colliding room away from its collision
     * @param moveRoom the room to move
     * @param circleX the center X of the grid
     * @param circleY the center Z of the grid
     * @param distance the distance away from the center the room is
     * @return the room at a new set of coordinates
     */
    public static Room seperateRooms(Room moveRoom, int circleX, int circleY, double distance) {
        int[] coordinates = moveRoom.getCoordinates();
        int x = coordinates[0];
        int y = coordinates[1];

        int newX = (int) Math.round(x + (x - circleX) / distance * 8);
        int newY = (int) Math.round(y + (y - circleY) / distance * 8);

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

        int[] c1 = room1.getCoordinates();
        int[] c2 = room2.getCoordinates();

        int l1 = room1.getLengthX();
        int w1 = room1.getLengthY();
        int l2 = room2.getLengthX();
        int w2 = room2.getLengthY();

        //  If there is a collision
        return c1[0] < c2[0] + l2 && c1[0] + l1 > c2[0] && c1[1] < c2[1] + w2 && c1[1] + w1 > c2[1];
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
