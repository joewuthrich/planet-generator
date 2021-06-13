package com.joewuthrich.dungeongenerator.commands;

import com.joewuthrich.dungeongenerator.layoutgenerator.objects.Edge;
import com.joewuthrich.dungeongenerator.layoutgenerator.objects.Room;
import java.util.AbstractMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.joewuthrich.dungeongenerator.placeblocks.PlaceLine.placeLines;
import static com.joewuthrich.dungeongenerator.layoutgenerator.CollisionDetection.*;
import static com.joewuthrich.dungeongenerator.layoutgenerator.Connections.getConnections;
import static com.joewuthrich.dungeongenerator.layoutgenerator.GenerateRooms.generateRooms;
import static com.joewuthrich.dungeongenerator.roomgenerator.PlaceBlob.generateBlobs;
import static com.joewuthrich.dungeongenerator.roomgenerator.PlaceOval.generateOval;

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
        int height = Integer.parseInt(args[5]);

        Room[] roomList;

        roomList = generateRooms(numRooms, radius, centerX,
                centerY, minRoomLength, maxRoomLength);

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

        generateBlobs(roomList, height);
        placeLines(edges);

        return true;
    }
}