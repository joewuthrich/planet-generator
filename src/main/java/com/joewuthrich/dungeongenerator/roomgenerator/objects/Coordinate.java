package com.joewuthrich.dungeongenerator.roomgenerator.objects;

public class Coordinate {
    public int x;
    public int y;
    public int z;

    public Coordinate(int _x, int _z) {
        this.x = _x;
        this.y = 100;
        this.z = _z;
    }

    public Coordinate(int _x, int _y, int _z) {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    public String toString2D() {
        return x + "," + z;
    }
}