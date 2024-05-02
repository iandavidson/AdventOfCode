package org.example.advent.year2022.day17;

import java.util.Objects;

public record Coordinate(int x, int y) implements Comparable<Coordinate> {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    //sort high to low
    @Override
    public int compareTo(Coordinate o) {
        if (y == o.y) {
            return x - o.x;
        }

        return o.y - y;
    }


}
