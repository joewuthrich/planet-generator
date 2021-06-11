package com.joewuthrich.dungeongenerator.commands;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Edge;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.NotEnoughPointsException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.joewuthrich.dungeongenerator.placeblocks.PlaceBlocks.placeBlocks;
import static com.joewuthrich.dungeongenerator.placeblocks.PlaceLine.placeLines;
import static com.joewuthrich.dungeongenerator.roomgenerator.CollisionDetection.*;
import static com.joewuthrich.dungeongenerator.roomgenerator.GenerateRooms.generateRooms;
import static com.joewuthrich.dungeongenerator.roomgenerator.MST.addEdges;
import static com.joewuthrich.dungeongenerator.roomgenerator.MST.generateMST;
import static com.joewuthrich.dungeongenerator.roomgenerator.RoomPicker.chooseRooms;
import static com.joewuthrich.dungeongenerator.roomgenerator.Triangulation.triangulateEdges;

public class DungeonGeneratorCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        int numRooms = Integer.parseInt(args[0]);

        if (numRooms < 4) {
            sender.sendMessage("need more than 3 rooms");
            return false;
        }

        int radius = numRooms * 3;
        int centerX = Integer.parseInt(args[1]);
        int centerY = Integer.parseInt(args[2]);
        int minRoomLength = Integer.parseInt(args[3]);
        int maxRoomLength = Integer.parseInt(args[4]);

        int numEdges;
        Room[] roomList = new Room[0];

        List<Edge> edges = new ArrayList<>();
        Coordinate seCorner = null, nwCorner = null;
        List<Edge> MSTEdges = new ArrayList<>();

        int q = 0;

        while (MSTEdges.size() != roomList.length - 1 && q < 50) {
            numEdges = 0;
            while (numEdges < numRooms) {
                roomList = generateRooms(numRooms, radius, centerX,
                        centerY, minRoomLength, maxRoomLength);

                seCorner = new Coordinate(centerX + (radius * 2), centerY + (radius * 2));
                nwCorner = new Coordinate(centerX - (radius * 2), centerY - (radius * 2));

                AbstractMap.SimpleEntry<int[][], Integer> c;
                int[][] collisions;
                int numCollisions;

                do {
                    c = checkCollisions(roomList);
                    collisions = c.getKey();
                    numCollisions = c.getValue();

                    if (numCollisions != 0) {
                        resolveCollisions(roomList, collisions, centerX, centerY);
                    }

                } while (numCollisions != 0);

                //chooseRooms(roomList);

                try {
                    edges = triangulateEdges(roomList);
                } catch (NotEnoughPointsException e) {
                    e.printStackTrace();
                }

                numEdges = edges.size();
            }

            MSTEdges = generateMST(edges, roomList);
            q++;
        }

        if (q == 50) {
            sender.sendMessage("room creation failed");
            return false;
        }

        addEdges(edges, MSTEdges);

        placeBlocks(roomList, seCorner, nwCorner);
        placeLines(MSTEdges);

        return true;
    }
}