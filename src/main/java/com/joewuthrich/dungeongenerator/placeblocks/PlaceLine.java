package com.joewuthrich.dungeongenerator.placeblocks;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Edge;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

public class PlaceLine {
    public static void placeLines(List<Edge> edges) {
        double m, c, dx, dz;
        int sx, sz;

        World w = Bukkit.getServer().getWorld("world");
        assert w != null;

        Material material = Material.YELLOW_CONCRETE;

        for (Edge edge : edges) {
            sx = edge.smallestX();
            sz = edge.smallestZ();

            dx = edge.a.x - edge.b.x;
            dz = edge.a.z - edge.b.z;

            if (dx == 0) {
                for (int z = sz; z < sz + Math.abs(dz); z++) {
                    w.getBlockAt(edge.a.x, 100, z).setType(material);
                    System.out.println(edge.a.x + "," + z + " X");
                }
                System.out.println("X: " + sz + " " + dz);
                continue;
            }

            m = dz/dx;

            c = edge.a.z - (m * edge.a.x);

            dx = Math.abs(dx);
            dz = Math.abs(dz);

            for (int x = sx; x < sx + dx; x++)
                w.getBlockAt(x, 100, (int) Math.round(m * x + c)).setType(material);

            for (int z = sz; z < sz + dz; z++)
                w.getBlockAt((int) Math.round((z - c) / m), 100, z).setType(material);
        }
    }
}
