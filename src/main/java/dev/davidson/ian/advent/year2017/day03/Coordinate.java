package dev.davidson.ian.advent.year2017.day03;

public record Coordinate(int r, int c) {

    public static Coordinate combineCoordinate(final Coordinate a, final Coordinate b) {
        return new Coordinate(a.r + b.r, a.c + b.c);
    }
}
