package com.joewuthrich.dungeongenerator;

import com.joewuthrich.dungeongenerator.commands.DungeonGeneratorCMD;
import com.joewuthrich.dungeongenerator.commands.PlanetCMD;
import org.bukkit.plugin.java.JavaPlugin;

public final class DungeonGenerator extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Online");
        this.getCommand("dungeongenerator").setExecutor(new DungeonGeneratorCMD());
        this.getCommand("planetgenerator").setExecutor(new PlanetCMD());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Offline");
    }
}
