package com.joewuthrich.planetgenerator.planet.commands;

import com.joewuthrich.planetgenerator.PlanetGenerator;
import com.joewuthrich.planetgenerator.planet.objects.Planet;
import com.joewuthrich.planetgenerator.planet.utils.ChooseRandom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PlanetCMD implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        if (args.length == 0) {
            sender.sendMessage("/pg [radius] [preset] (x) (y) (z)");
        }

        int radius = Integer.parseInt(args[0]);
        String preset = args[1];

        /*Material overlay = Material.valueOf(args[1].toUpperCase());
        Material underlay = Material.valueOf(args[2].toUpperCase());
        String[] mats = args[3].split(",");
        Material[] c = new Material[mats.length];
        for (int i = 0; i < mats.length; i++) {
            c[i] = Material.valueOf(mats[i].toUpperCase());
        }
        Material cave = Material.valueOf(args[4]);
        Biome biome = Biome.valueOf(args[5].toUpperCase());
        String texture = args[6].toUpperCase();
        */

        PlanetGenerator.plugin.reloadConfig();

        FileConfiguration config = PlanetGenerator.textures;

        List<String> subClasses = config.getStringList(preset + ".subclasses");

        while (subClasses.size() > 0) {
            preset = ChooseRandom.chooseRandom(subClasses, 1).get(0);
            subClasses = config.getStringList(preset + ".subclasses");
        }

        int num = 2 + (int) Math.round(((double) radius / 8) * Math.random());

        Material overlay = Material.valueOf(ChooseRandom.chooseRandom(config.getStringList(preset + ".overlay"), 1).get(0));
        Material underlay = Material.valueOf(ChooseRandom.chooseRandom(config.getStringList(preset + ".underlay"), 1).get(0));
        String[] mats = ChooseRandom.chooseRandom(config.getStringList(preset + ".material"), num).toArray(new String[0]);
        Material[] c = new Material[mats.length];
        for (int i = 0; i < mats.length; i++)
            c[i] = Material.valueOf(mats[i].toUpperCase());
        Material cave = Material.valueOf(ChooseRandom.chooseRandom(config.getStringList(preset + ".cave"), 1).get(0));
        Material topDecoration = Material.valueOf(ChooseRandom.chooseRandom(config.getStringList(preset + ".topdecoration"), 1).get(0));
        Biome biome = Biome.valueOf(ChooseRandom.chooseRandom(config.getStringList(preset + ".biome"), 1).get(0));
        String texture = ChooseRandom.chooseRandom(config.getStringList(preset + ".texture"), 1).get(0);

        Block bl;

        if (args.length == 2)
            bl = ((Player) sender).getTargetBlock(50);
        else
            bl = Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));

        assert bl != null;
        Planet planet = new Planet(bl, radius);

        sender.sendMessage(ChatColor.GRAY + "The planet " + ChatColor.WHITE + ChatColor.BOLD + planet.getName() + ChatColor.GRAY + " has appeared.");

        planet.baseMaterials(overlay, underlay, c, cave, topDecoration, biome, texture);

        return true;
    }
}