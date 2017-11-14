package com.wniemiec.cheat.ptanks;

public class Position {
    public double x;
    public double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x + ", y=" + y +
                '}';
    }
}
