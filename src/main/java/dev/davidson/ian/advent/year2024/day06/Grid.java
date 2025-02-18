package dev.davidson.ian.advent.year2024.day06;

import java.util.List;


public record Grid(List<String> grid, int rows, int cols) {

    public static Grid newGrid(List<String> grid) {
        return new Grid(grid, grid.size(), grid.getFirst().length());
    }

    public Character get(final Coordinate coordinate) {
        if(!inBounds(coordinate)){
            return null;
        }

        return grid.get(coordinate.r()).charAt(coordinate.c());
    }

    public boolean inBounds(final Coordinate coordinate){
        return coordinate.r() >= 0 && coordinate.r() < rows && coordinate.c() >= 0 && coordinate.c() < cols;
    }
}
