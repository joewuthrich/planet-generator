package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;

public class RoomPicker {

    /**
     * Pick the best rooms, with preference to biggest rooms by area, from a list.
     * @param roomList the list of rooms
     * @return the adjusted list of rooms
     */
    public static Room[] chooseRooms(Room[] roomList) {
        int totalSize = 0;

        if (roomList.length == 3) {
            for (Room room : roomList) {
                room.setUsed(true);
            }
            return roomList;
        }

        for (Room room : roomList) {
            totalSize += room.getSize();
        }

        int meanSize = totalSize / roomList.length;

        int numUsedRooms = 0;

        while (numUsedRooms < 3) {
            for (Room room : roomList) {
                if (room.getSize() > meanSize) {
                    room.setUsed(true);
                    numUsedRooms++;
                }
            }

            meanSize /= 2;
        }

        return roomList;
    }
}