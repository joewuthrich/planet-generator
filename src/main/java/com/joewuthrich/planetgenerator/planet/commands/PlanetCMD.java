package com.joewuthrich.planetgenerator.planet.commands;

import com.joewuthrich.planetgenerator.planet.objects.Planet;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class PlanetCMD implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        if (args.length == 0) {
            sender.sendMessage("/pg [radius] [overlay] [underlay] [block,list,comma,seperated] [gradient (true/false)]");
        }

        int radius = Integer.parseInt(args[0].toUpperCase());
        Material overlay = Material.valueOf(args[1].toUpperCase());
        Material underlay = Material.valueOf(args[2].toUpperCase());
        String[] mats = args[3].split(",");
        Material[] c = new Material[mats.length];
        for (int i = 0; i < mats.length; i++) {
            c[i] = Material.valueOf(mats[i].toUpperCase());
        }

        Material cave = Material.valueOf(args[4]);

        Biome biome = Biome.valueOf(args[5].toUpperCase());

        String gradient = args[6].toUpperCase();

        Block bl = ((Player) sender).getTargetBlock(100);
        assert bl != null;

        Planet planet = new Planet(bl, radius);

        planet.baseMaterials(overlay, underlay, c, cave, biome, gradient);

        return true;
    }
}
