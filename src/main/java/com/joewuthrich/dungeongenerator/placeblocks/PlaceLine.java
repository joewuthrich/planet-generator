package com.joewuthrich.dungeongenerator.placeblocks;

import com.joewuthrich.dungeongenerator.layoutgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.layoutgenerator.objects.Edge;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

import static com.joewuthrich.dungeongenerator.roomgenerator.PlaceBlob.generateBlob;

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
                for (int z = sz; z < sz + Math.abs(dz); z++)
                    w.getBlockAt(edge.a.x, 100, z).setType(material);
                continue;
            }

            m = dz/dx;

            c = edge.a.z - (m * edge.a.x);

            dx = Math.abs(dx);
            dz = Math.abs(dz);

            for (int x = sx; x < sx + dx; x += 3)
                generateBlob(new Coordinate(x, (int) Math.round(m * x + c)), 5);
                //w.getBlockAt(x, 100, (int) Math.round(m * x + c)).setType(material);

            for (int z = sz; z < sz + dz; z += 3)
                generateBlob(new Coordinate((int) Math.round((z - c) / m), z), 5);
                //w.getBlockAt((int) Math.round((z - c) / m), 100, z).setType(material);
        }
    }
}
