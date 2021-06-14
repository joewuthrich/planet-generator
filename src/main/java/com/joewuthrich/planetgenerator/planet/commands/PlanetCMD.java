package com.joewuthrich.planetgenerator.planet.commands;

import com.joewuthrich.planetgenerator.PlanetGenerator;
import com.joewuthrich.planetgenerator.planet.objects.Planet;
import com.joewuthrich.planetgenerator.planet.utils.ChooseRandom;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class PlanetCMD implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        if (args.length == 0) {
            sender.sendMessage("/pg [radius] [overlay] [underlay] [block,list,comma,seperated] [cave] [biome] [gradient (true/false)]");
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

        FileConfiguration config = PlanetGenerator.plugin.getConfig();

        Material overlay = Material.valueOf(ChooseRandom.chooseRandom(config.getStringList("textures." + preset + ".overlay"), 1).get(0));
        Material underlay = Material.valueOf(ChooseRandom.chooseRandom(config.getStringList("textures." + preset + ".underlay"), 1).get(0));
        String[] mats = ChooseRandom.chooseRandom(config.getStringList("textures." + preset + ".material"), 2).toArray(new String[0]);
        System.out.println(Arrays.toString(mats));
        Material[] c = new Material[mats.length];
        for (int i = 0; i < mats.length; i++) {
            c[i] = Material.valueOf(mats[i].toUpperCase());
        }
        Material cave = Material.valueOf(ChooseRandom.chooseRandom(config.getStringList("textures." + preset + ".cave"), 1).get(0));
        Biome biome = Biome.valueOf(ChooseRandom.chooseRandom(config.getStringList("textures." + preset + ".biome"), 1).get(0));
        String texture = ChooseRandom.chooseRandom(config.getStringList("textures." + preset + ".texture"), 1).get(0);

        //https://random-word-api.herokuapp.com/word?number=1

        Block bl;

        if (args.length == 2)
            bl = ((Player) sender).getTargetBlock(50);
        else
            bl = Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));

        sender.sendMessage(overlay + " " + underlay + " " + Arrays.toString(c) + " " + cave + " " + biome + " " + texture);

        assert bl != null;
        Planet planet = new Planet(bl, radius);

        sender.sendMessage("here now");

        planet.baseMaterials(overlay, underlay, c, cave, biome, texture);

        return true;
    }
}
