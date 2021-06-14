package com.joewuthrich.planetgenerator.layoutgenerator.objects;

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
        return (c.equals(a) || c.equals(b));
    }

    /**
     * Get the opposing coordinate
     * @param c the first coordinate
     * @return the other coordinate
     */
    public Coordinate opposite(Coordinate c) {
        if (c.equals(a))
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