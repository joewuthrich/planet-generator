package com.joewuthrich.planetgenerator.commands;

import com.joewuthrich.planetgenerator.planet.objects.Planet;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlanetCMD implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        if (args.length == 0) {
            sender.sendMessage("/pg [radius] [overlay] [underlay] [block,list,comma,seperated] [gradient (true/false)]");
        }

        int radius = Integer.parseInt(args[0]);
        Material overlay = Material.valueOf(args[1]);
        Material underlay = Material.valueOf(args[2]);
        String[] mats = args[3].split(",");
        Material[] c = new Material[mats.length];
        for (int i = 0; i < mats.length; i++) {
            c[i] = Material.valueOf(mats[i].toUpperCase());
        }

        boolean gradient = Boolean.parseBoolean(args[4]);

        Block bl = ((Player) sender).getTargetBlock(100);
        assert bl != null;

        Planet planet = new Planet(bl, radius);

        planet.baseMaterials(overlay, underlay, c, gradient);

        return true;
    }
}
