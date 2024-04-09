package org.example.advent.year2022.day09;

public record RopeKnot(Coordinate coordinate) {
    public long row(){
        return coordinate.row();
    }

    public long col(){
        return coordinate.col();
    }

}
