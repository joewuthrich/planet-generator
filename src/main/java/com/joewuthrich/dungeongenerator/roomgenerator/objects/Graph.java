package com.joewuthrich.dungeongenerator.roomgenerator.objects;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    public List<List<Edge>> adjacency = null;

    public Graph(List<Edge> edges, Coordinate[] coordinates) {
        adjacency = new ArrayList<>();

        for (int i = 0; i < coordinates.length; i++) {
            adjacency.add(new ArrayList<>());
            for (Edge edge : edges) {
                if (edge.containsCoordinate(coordinates[i]))
                    adjacency.get(i).add(edge);
            }
        }
    }

    public List<Edge> getEdges(int i) {
        return adjacency.get(i);
    }
}
