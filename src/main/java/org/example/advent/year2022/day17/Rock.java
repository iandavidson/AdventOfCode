package org.example.advent.year2022.day17;

import lombok.Getter;

import java.util.List;

@Getter
public class Rock implements Comparable<Rock> {
    private final List<Coordinate> occupied;
    private final ROCKTYPE rockType;
    private final int highestY;

    private Rock(final List<Coordinate> occupied, final ROCKTYPE rockType, final int highestY) {
        this.occupied = occupied;
        this.rockType = rockType;
        this.highestY = highestY;
    }

    public static Rock newRock(final List<Coordinate> occupied, final ROCKTYPE rockType, final int highestY) {
        return new Rock(occupied, rockType, highestY);
    }


    @Override
    public int compareTo(Rock o) {
        return Integer.compare(highestY, o.highestY);
    }
}
