package com.joewuthrich.dungeongenerator.roomgenerator.objects;

import java.util.List;

public class Edge {
    public Coordinate a;
    public Coordinate b;

    public Edge(Coordinate _a, Coordinate _b) {
        this.a = _a;
        this.b = _b;
    }

    /**
     * Get the length of the edge
     */
    public double getDistance() {
        return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.z - b.z), 2));
    }

    public String toString() {
        return a.x + "," + a.z + " " + b.x + "," + b.z;
    }

    /**
     * Check if the edge contains a coordinate.
     */
    public boolean containsCoordinate(Coordinate c) {
        return (c.x == a.x && c.y == a.y && c.z == a.z || c.x == b.x && c.y == b.y && c.z == b.z);
    }

    /**
     * Check if the edge contains more than one coordinate from a list
     */
    public boolean containsMultipleCoordinates(List<Coordinate> coordinates) {
        int i = 0;
        for (Coordinate c : coordinates) {
            if (c == null)
                return (i > 1);

            if (containsCoordinate(c))
                i++;
        }


        return (i > 1);
    }

    /**
     * Get the opposing coordinate
     * @param c the first coordinate
     * @return the other coordinate
     */
    public Coordinate opposite(Coordinate c) {
        if (c.x == a.x && c.y == a.y && c.z == a.z)
            return b;
        else
            return a;
    }

    /**
     * @return the smallest x value in the edge
     */
    public int smallestX() {
        return (Math.min(a.x, b.x));
    }

    /**
     * @return the smallest z value in the edge
     */
    public int smallestZ() {
        return (Math.min(a.z, b.z));
    }
}