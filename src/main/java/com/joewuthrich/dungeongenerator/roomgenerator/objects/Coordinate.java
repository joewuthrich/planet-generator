package com.joewuthrich.dungeongenerator.roomgenerator.objects;

public class Coordinate {
    public int x;
    public int y;
    public int z;

    public int id;

    public Coordinate(int _x, int _z) {
        this.x = _x;
        this.y = 100;
        this.z = _z;
    }

    public String toString() {
        return x + "," + z;
    }

    public boolean equals(Coordinate c) {
        return (x == c.x && y == c.y && z == c.z);
    }
}