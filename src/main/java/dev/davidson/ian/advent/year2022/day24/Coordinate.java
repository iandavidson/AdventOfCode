package dev.davidson.ian.advent.year2022.day24;

public record Coordinate(int row, int col) {
    public String toId(){
        return row + ":" + col;
    }
}
