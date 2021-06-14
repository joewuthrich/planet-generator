package com.joewuthrich.planetgenerator.planet.utils;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class WaterFlow implements Listener {
    @EventHandler
    public void onWaterFlow(BlockFromToEvent event) {
        event.setCancelled(true);
    }
}
