package dev.davidson.ian.advent.year2022.day15;

import java.util.Objects;

public record Coordinate(int x, int y) {
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

    public static Coordinate newCoordinate(String line){
        String [] groups = line.split(",");
        return new Coordinate(Integer.parseInt(groups[0].trim().substring(2)), Integer.parseInt(groups[1].trim().substring(2)));
    }
}
