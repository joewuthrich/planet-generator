package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Edge;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;

import java.util.ArrayList;
import java.util.List;

public class MST {

    public static List<Edge> generateMST(List<Edge> edges, Room[] roomList) {
        double smallest = Double.MAX_VALUE;

        Edge currentEdge = edges.get(0);
        List<Coordinate> coords = new ArrayList<>();
        List<Edge> MSTEdges = new ArrayList<>();
        coords.add(roomList[0].getCoordinates());
        Coordinate coordinate = roomList[0].getCoordinates();

        for (int i = 0; i < roomList.length - 1; i++) {
            for (Edge edge : edges) {
                for (Coordinate c : coords) {
                    if (edge.containsCoordinate(c) && edge.getDistance() < smallest) {
                        smallest = edge.getDistance();
                        currentEdge = edge;
                        coordinate = c;
                    }
                }
            }
            coords.add(currentEdge.opposite(coordinate));
            MSTEdges.add(currentEdge);
            edges.remove(currentEdge);
            smallest = Double.MAX_VALUE;
        }

        return MSTEdges;
    }
}