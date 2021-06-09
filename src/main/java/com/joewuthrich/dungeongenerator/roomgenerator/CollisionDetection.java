package com.joewuthrich.dungeongenerator.roomgenerator;

import java.util.AbstractMap;

public class CollisionDetection {
    public static Room[] resolveCollisions(Room[] roomList, int[][] collisions, int circleX, int circleY) {
        int numRooms = roomList.length;
        Room room1;
        Room room2;

        int[] coords1;
        int[] coords2;

        int[] size1;
        int[] size2;

        double distance1;
        double distance2;

        for (int i = 0; i < collisions.length; i++) {
            if (collisions[i] == null)
                break;

            room1 = roomList[collisions[i][0]];
            room2 = roomList[collisions[i][1]];

            coords1 = room1.getCoordinates();
            coords2 = room2.getCoordinates();

            size1 = new int[] {room1.getLengthX(), room1.getLengthY()};

            distance1 = Math.sqrt(((coords1[0] - circleX) * (coords1[0] - circleX)) + ((coords1[1] - circleY) * (coords1[1] - circleY)));
            distance2 = Math.sqrt(((coords2[0] - circleX) * (coords2[0] - circleX)) + ((coords2[1] - circleY) * (coords2[1] - circleY)));

            if (distance1 < distance2) {
                seperateRooms(room2, room1, circleX, circleY);
            }
            else {
                seperateRooms(room1, room2, circleX, circleY);
            }
        }
        return roomList;
    }


    public static Room[] seperateRooms(Room moveRoom, Room stayRoom, int circleX, int circleY) {
        Room[] rooms = {moveRoom, stayRoom};
        String quadrant = roomQuadrant(moveRoom, circleX, circleY);



        return rooms;
    };

    /**
     * Returns the quadrant of a graph that the point is in relative to the centerpoint
     * @param room the room
     * @param centerX the center's X coordinate
     * @param centerY the center's Y coordinate
     * @return the name of the quadrant as a string: NE, NW, SE, SW
     */
    private static String roomQuadrant(Room room, int centerX, int centerY) {
        int[] coordinates = room.getCoordinates();
        int x = coordinates[0];
        int y = coordinates[1];

        if (x >= centerX && y >= centerY) {
            // SE quadrant
            return "SE";
        }
        else if (x > centerX && y < centerY) {
            // NW quadrant
            return "NW";
        }
        else if(x < centerX && y > centerY) {
            //  SE quadrant
            return "SE";
        }

        //  NE quadrant
        return "NE";
    }

    /**
     * Takes a list of rooms and returns the indexes in the list that have colliding rooms.
     * @param roomList the list of rooms
     * @returns a pair an array of room indices in that list that collide and the number of collisions there were
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

        return new AbstractMap.SimpleEntry<>(collisions, numCollisions--);
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
        if (c1[0] < c2[0] + l2 && c1[0] + l1 > c2[0] && c1[1] < c2[1] + w2 && c1[1] + w1 > c2[1]) {
            return true;
        }

        return false;
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
