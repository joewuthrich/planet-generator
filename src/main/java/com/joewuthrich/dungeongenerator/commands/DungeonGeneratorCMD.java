package com.joewuthrich.dungeongenerator.commands;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Edge;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.NotEnoughPointsException;
import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.Triangle2D;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.joewuthrich.dungeongenerator.placeblocks.PlaceBlocks.placeBlocks;
import static com.joewuthrich.dungeongenerator.roomgenerator.CollisionDetection.*;
import static com.joewuthrich.dungeongenerator.roomgenerator.GenerateRooms.generateRooms;
import static com.joewuthrich.dungeongenerator.roomgenerator.MST.generateMST;
import static com.joewuthrich.dungeongenerator.roomgenerator.RoomPicker.chooseRooms;
import static com.joewuthrich.dungeongenerator.roomgenerator.Triangulation.triangulateEdges;

public class DungeonGeneratorCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        int numRooms = Integer.parseInt(args[0]);
        int radius = numRooms * 3;
        int centerX = Integer.parseInt(args[1]);
        int centerY = Integer.parseInt(args[2]);
        int minRoomLength = Integer.parseInt(args[3]);
        int maxRoomLength = Integer.parseInt(args[4]);

        Room[] roomList = generateRooms(numRooms, radius, centerX,
                centerY, minRoomLength, maxRoomLength);

        int[] seCorner = {centerX + (radius * 2), centerY + (radius * 2)};
        int[] nwCorner = {centerX - (radius * 2), centerY - (radius * 2)};

        AbstractMap.SimpleEntry<int[][], Integer> c;
        int[][] collisions;
        int numCollisions;

        do {
            c = checkCollisions(roomList);
            collisions = c.getKey();
            numCollisions = c.getValue();

            if (numCollisions != 0)
                roomList = resolveCollisions(roomList, collisions, centerX, centerY);

        } while (numCollisions != 0);

        roomList = chooseRooms(roomList);

        List<Edge> edges = new ArrayList<>();

        try {
            edges = triangulateEdges(roomList);
        } catch (NotEnoughPointsException e) {
            e.printStackTrace();
        }

        edges = generateMST(edges, roomList);

        /*
        for (Edge edge : edges) {
            System.out.println(edge.toString());
        }
        */
        placeBlocks(roomList, seCorner, nwCorner);

        return true;
    }
}