package com.joewuthrich.dungeongenerator.commands;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Edge;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;
import java.util.AbstractMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.joewuthrich.dungeongenerator.placeblocks.PlaceBlocks.placeBlocks;
import static com.joewuthrich.dungeongenerator.placeblocks.PlaceLine.placeLines;
import static com.joewuthrich.dungeongenerator.roomgenerator.CollisionDetection.*;
import static com.joewuthrich.dungeongenerator.roomgenerator.Connections.getConnections;
import static com.joewuthrich.dungeongenerator.roomgenerator.GenerateRooms.generateRooms;

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

        Room[] roomList;

        Coordinate seCorner, nwCorner;

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

        List<Edge> edges = getConnections(roomList);

        placeBlocks(roomList, seCorner, nwCorner);
        placeLines(edges);

        return true;
    }
}