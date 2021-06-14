package com.joewuthrich.planetgenerator;

import com.joewuthrich.planetgenerator.commands.PlanetCMD;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlanetGenerator extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Online");
        this.getCommand("planetgenerator").setExecutor(new PlanetCMD());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Offline");
    }
}
