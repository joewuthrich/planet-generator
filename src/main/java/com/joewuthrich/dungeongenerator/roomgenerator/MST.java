package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Edge;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;

import java.util.ArrayList;
import java.util.List;

public class MST {
    public static List<Edge> generateMST(List<Edge> edges, Room[] roomList) {
        double smallest = Double.MAX_VALUE;

        Edge currentEdge = null;
        List<Coordinate> coords = new ArrayList<>();
        List<Edge> MSTEdges = new ArrayList<>();

        coords.add(roomList[0].getCenterCoordinate());
        Coordinate coordinate = roomList[0].getCenterCoordinate();
        Edge removeEdge = null;

        for (int i = 0; i < roomList.length - 1; i++) {
            for (Edge edge : edges) {
                for (Coordinate c : coords) {
                    if (edge.containsCoordinate(c) && edge.getDistance() < smallest) {
                        if (edge.containsMultipleCoordinates(coords)) {
                            removeEdge = edge;
                            continue;
                        }
                        smallest = edge.getDistance();
                        currentEdge = edge;
                        coordinate = c;
                    }
                }
            }
            if (removeEdge != null) {
                edges.remove(removeEdge);
                removeEdge = null;
            }
            if (currentEdge != null)
                coords.add(currentEdge.opposite(coordinate));
            else
                continue;

            MSTEdges.add(currentEdge);
            edges.remove(currentEdge);
            smallest = Double.MAX_VALUE;
            currentEdge = null;
        }

        return MSTEdges;
    }

    /**
     * Add edges from one list into another with a 10% chance of adding each edge.
     * @param edges the list of edges to add from
     * @param addList the list of edges to add to
     * @return the new list of edges
     */
    public static List<Edge> addEdges(List<Edge> edges, List<Edge> addList)  {
        for (Edge edge : edges) {
            if (addList.contains(edge))
                continue;

            if (0.1 > Math.random())
                addList.add(edge);
        }
        return addList;
    }
}