package org.example.advent.year2022.day18;

import java.util.Objects;

public record Coordinate(int x, int y, int z) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate boulder = (Coordinate) o;
        return x == boulder.x && y == boulder.y && z == boulder.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
