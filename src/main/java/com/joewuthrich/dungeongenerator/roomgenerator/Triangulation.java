package com.joewuthrich.dungeongenerator.roomgenerator;

import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.DelaunayTriangulator;
import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.NotEnoughPointsException;
import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.Triangle2D;
import com.joewuthrich.dungeongenerator.roomgenerator.triangulator.Vector2D;

import java.util.Arrays;
import java.util.Vector;
import java.util.List;


public class Triangulation {
    /**
     * Triangulate the triangles that make up the triangulation of the shape
     * @param roomList
     * @return
     * @throws NotEnoughPointsException
     */
    public static List<Triangle2D> triangulateEdges(Room[] roomList) throws NotEnoughPointsException {

        Vector<Vector2D> pointSet = new Vector<Vector2D>();

        for (Room room : roomList) {
            pointSet.add(new Vector2D(room.getCoordinates()[0], room.getCoordinates()[1]));
        }

        DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator(pointSet);
        delaunayTriangulator.triangulate();

        return delaunayTriangulator.getTriangles();
    }

}
