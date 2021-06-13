package com.joewuthrich.dungeongenerator.commands;

import com.joewuthrich.dungeongenerator.planet.PlanetBlockLabeler;
import com.joewuthrich.dungeongenerator.planet.PlanetBlockPlacer;
import com.joewuthrich.dungeongenerator.planet.PlanetShapeGenerator;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlanetCMD implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        int radius = Integer.parseInt(args[0]);

        Block bl = ((Player) sender).getTargetBlock(100);
        assert bl != null;

        List<Block> blocks = PlanetShapeGenerator.generatePlanetShape(bl, radius);

        PlanetBlockPlacer.placePlanetBlocks(blocks, true, radius, bl.getY());

        PlanetBlockLabeler.labelBlock(blocks);

        return true;
    }
}
