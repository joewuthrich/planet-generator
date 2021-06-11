package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.roomgenerator.objects.Coordinate;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Edge;
import com.joewuthrich.dungeongenerator.roomgenerator.objects.Room;
import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.DelaunayTriangulator;
import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.NotEnoughPointsException;
import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.Triangle2D;
import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.List;


public class Triangulation {
    /**
     * Triangulate the triangles that make up the triangulation of the shape
     * @param roomList the list of rooms to get the triangulation from
     * @return a list of all edges in the triangulation
     * @throws NotEnoughPointsException if there are less than three points.
     */
    public static List<Edge> triangulateEdges(Room[] roomList) throws NotEnoughPointsException {
        Vector<Vector2D> pointSet = new Vector<>();

        Coordinate c;
        for (Room room : roomList) {
            c = room.getCoordinates();
            pointSet.add(new Vector2D(c.x, c.z));
        }

        DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator(pointSet);
        delaunayTriangulator.triangulate();

        List<Triangle2D> triangles = delaunayTriangulator.getTriangles();

        Edge[] edgeArray = new Edge[3];

        List<Edge> edges = new ArrayList<>();

        for (Triangle2D triangle : triangles) {
            edgeArray[0] = getEdge(triangle.a, triangle.b);
            edgeArray[1] = getEdge(triangle.b, triangle.c);
            edgeArray[2] = getEdge(triangle.c, triangle.a);

            System.out.println(Arrays.toString(edgeArray));

            for (Edge edge : edgeArray) {
                if (!containsEdge(edges, edge))
                    edges.add(edge);
            }
        }

        System.out.println(edges);

        return edges;
    }

    /**
     * Get an edge from two vectors
     */
    public static Edge getEdge(Vector2D v1, Vector2D v2) {
        Coordinate c1 = new Coordinate((int) Math.round(v1.x), (int) Math.round(v1.y)),
                c2 = new Coordinate((int) Math.round(v2.x), (int) Math.round(v2.y));

        return new Edge(c1, c2);
    }


    /**
     * Check if a list of edges already contains an edge
     * @param edges the list of edges
     * @param e the edge to check
     * @return true or false
     */
    private static boolean containsEdge(List<Edge> edges, Edge e) {
        for (Edge edge : edges) {
            if (edge.a.x == e.a.x && edge.a.z == e.a.z && edge.b.x == e.b.x && edge.b.z == e.b.z ||
                    edge.a.x == e.b.x && edge.a.z == e.b.z && edge.b.x == e.a.x && edge.b.z == e.a.z)
                return true;
        }

        return false;
    }
}