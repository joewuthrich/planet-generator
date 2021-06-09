package com.joewuthrich.dungeongenerator.commands;

import com.joewuthrich.dungeongenerator.roomgenerator.Room;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.joewuthrich.dungeongenerator.placeblocks.PlaceBlocks.placeBlocks;
import static com.joewuthrich.dungeongenerator.roomgenerator.GenerateRooms.generateRooms;

public class DungeonGeneratorCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        Player p = (Player) sender;

        Room[] roomList = generateRooms(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));

        int[] seCorner = {Integer.parseInt(args[2]) + Integer.parseInt(args[1]), Integer.parseInt(args[3]) + Integer.parseInt(args[1])};
        int[] nwCorner = {Integer.parseInt(args[2]) - Integer.parseInt(args[1]), Integer.parseInt(args[3]) - Integer.parseInt(args[1])};

        placeBlocks(roomList, seCorner, nwCorner);

        return true;
    }
}
