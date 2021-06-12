package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.*;

import java.util.*;

public class Connections {

    /**
     * Get the connections between the rooms
     * @param rooms the list of rooms
     * @return the list of edges connecting the rooms
     */
    public static List<Edge> getConnections(Room[] rooms) {
        Coordinate[] coordinates = new Coordinate[rooms.length];

        for (int i = 0; i < rooms.length; i++)
            coordinates[i] = rooms[i].getCenterCoordinate();

        getIDs(coordinates);
        List<Edge> edgeList = getEdges(coordinates);

        Pair p = getLines(edgeList, coordinates.length);

        edgeList = addCycles(p.b, p.a);

        return edgeList;
    }

    /**
     * Create a complete graph from a set of coordinates
     * @param coordinates the coordinates
     * @return a list containing all the edges of the complete graph, sorted by distance in ascending order
     */
    private static List<Edge> getEdges(Coordinate[] coordinates) {
        List<Edge> edgeList = new ArrayList<>();

        for (int i = 0; i < coordinates.length; i++) {
            for (int j = i; j < coordinates.length; j++) {
                if (coordinates[i] != coordinates[j])
                    edgeList.add(new Edge(coordinates[i], coordinates[j]));
            }
        }

        edgeList.sort(Comparator.comparingDouble(Edge::getDistance));

        return edgeList;
    }

    /**
     * Get the ids of the coordinates
     * @param coordinates a coordinate list
     */
    private static void getIDs(Coordinate[] coordinates) {
        for (int i = 0; i < coordinates.length; i++)
            coordinates[i].id = i;
    }

    /**
     * Reintroduce some cycles (that aren't triangles)
     * @param edges the list of edges to reintroduce from
     * @param newEdges the list of edges to reintroduce to
     * @return a list of edges to add
     */
    private static List<Edge> addCycles(List<Edge> edges, List<Edge> newEdges) {
        double points = newEdges.size() + 1;

        int cycles = (int) Math.round((points / 6.667) + (Math.random() * 2 - 1));

        int startIndex = (int) Math.round((double) edges.size() / 7);

        CYCLES:
        while (cycles > 0) {
            Edge edge = edges.get(startIndex);

            List<Coordinate> aList = new ArrayList<>();
            List<Coordinate> bList = new ArrayList<>();

            for (Edge e : newEdges) {
                if (e.containsCoordinate(edge.a))
                    aList.add(e.opposite(edge.a));

                if (e.containsCoordinate(edge.b))
                    bList.add(e.opposite(edge.b));
            }

            for (Coordinate c : aList) {
                if (bList.contains(c)) {
                    edges.remove(edge);
                    continue CYCLES;
                }
            }

            newEdges.add(edge);
            edges.remove(edge);
            cycles--;
        }

        return newEdges;
    }

    /**
     * Generate connections between graphs using Kruskal’s MST Algorithm
     * @param edges the list of edges
     * @param points the number of points to join
     * @return the list of edges of the new graph
     */
    private static Pair getLines(List<Edge> edges, int points) {
        int[] index = new int[points];
        List<Edge> finalEdges = new ArrayList<>();
        List<Edge> removeEdges = new ArrayList<>();

        for (int i = 0; i < points; i++)
            index[i] = i;

        for (Edge edge : edges) {
            int a = find(index, edge.a.id);
            int b = find(index, edge.b.id);

            if (a == b) {
                continue;
            }

            union(index, a, b);

            finalEdges.add(edge);
            removeEdges.add(edge);
        }

        for (Edge edge : removeEdges)
            edges.remove(edge);

        return new Pair(finalEdges, edges);
    }

    /**
     * Find the parent of a coordinate
     */
    private static int find(int[] index, int i) {
        if (i == index[i])
            return i;

        return find(index, index[i]);
    }

    /**
     * Union two coordinates
     */
    private static void union(int[] index, int a, int b) {
        index[a] = b;
    }
}
