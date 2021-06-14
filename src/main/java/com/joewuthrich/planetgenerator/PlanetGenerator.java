package com.joewuthrich.planetgenerator;

import com.joewuthrich.planetgenerator.planet.commands.PlanetCMD;
import com.joewuthrich.planetgenerator.planet.utils.CancelListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PlanetGenerator extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Online");
        Objects.requireNonNull(this.getCommand("planetgenerator")).setExecutor(new PlanetCMD());
        getServer().getPluginManager().registerEvents(new CancelListener(), this);

        plugin = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Offline");
    }
}
