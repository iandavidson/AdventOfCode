package dev.davidson.ian.advent.year2023.day22;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@AllArgsConstructor
@Builder
@Data
public class Coordinate implements Comparable<Coordinate> {
    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
        this.z = coordinate.z;
    }

    private final Integer x;
    private final Integer y;
    private Integer z;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y) && Objects.equals(z, that.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }


    public void fall(int delta) {
        setZ(z - delta);
    }

    @Override
    public int compareTo(Coordinate o) {
        return this.z - o.getZ();
    }
}
