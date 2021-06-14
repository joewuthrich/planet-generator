package com.joewuthrich.planetgenerator;

import com.joewuthrich.planetgenerator.planet.commands.PlanetCMD;
import com.joewuthrich.planetgenerator.planet.utils.WaterFlow;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlanetGenerator extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Online");
        this.getCommand("planetgenerator").setExecutor(new PlanetCMD());
        getServer().getPluginManager().registerEvents(new WaterFlow(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Offline");
    }
}
