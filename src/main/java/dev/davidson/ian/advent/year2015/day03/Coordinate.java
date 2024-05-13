package dev.davidson.ian.advent.year2015.day03;

public record Coordinate(int row, int col) {
    public static Coordinate newCoordinate(final Coordinate current, final Coordinate shift){
        return new Coordinate(current.row() + shift.row(), current.col() + shift.col());
    }
}
