package com.joewuthrich.planetgenerator.planet.utils;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class CancelListener implements Listener {
    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) { event.setCancelled(true); }

    @EventHandler
    public void onTreeGrow(StructureGrowEvent event) { event.setCancelled(true); }
}
