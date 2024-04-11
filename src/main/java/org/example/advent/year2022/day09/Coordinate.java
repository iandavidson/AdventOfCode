package org.example.advent.year2022.day09;

import java.util.Objects;

public record Coordinate(Long row, Long col) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(row, that.row) && Objects.equals(col, that.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public static Coordinate newCoordinate(final Coordinate coordinate, final Long rowDelta, final Long colDelta) {
        Coordinate newCoordinate = new Coordinate(coordinate.row() + rowDelta, coordinate.col() + colDelta);

        return newCoordinate;
    }

    public static Coordinate newCoordinate(final Coordinate coordinate, final Coordinate delta){
        return newCoordinate(coordinate, delta.row(), delta.col());
    }

    public boolean isDiagonal(Coordinate prior){
        Long deltaX = Math.abs(prior.col() - this.col);
        Long deltaY = Math.abs(prior.row() - this.row);

        return deltaY.equals(deltaX) && deltaX == 1L && deltaY == 1L;
    }
}
