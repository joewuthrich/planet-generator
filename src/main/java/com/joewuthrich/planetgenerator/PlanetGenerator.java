package com.joewuthrich.planetgenerator;

import com.joewuthrich.planetgenerator.planet.commands.PlanetCMD;
import com.joewuthrich.planetgenerator.planet.utils.CancelListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class PlanetGenerator extends JavaPlugin {

    public static Plugin plugin;

    public static FileConfiguration textures;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Online");
        Objects.requireNonNull(this.getCommand("planetgenerator")).setExecutor(new PlanetCMD());
        getServer().getPluginManager().registerEvents(new CancelListener(), this);

        textures = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "textures.yml"));

        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Offline");
    }
}
