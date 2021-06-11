package com.joewuthrich.dungeongenerator.roomgenerator.objects;

public class Edge {
    public Coordinate a;
    public Coordinate b;

    public Edge(Coordinate _a, Coordinate _b) {
        this.a = _a;
        this.b = _b;
    }

    public double getDistance() {
        return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.z - b.z), 2));
    }

    public String toString() {
        return a.x + "," + a.z + " " + b.x + "," + b.z;
    }
}