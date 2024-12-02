package dev.davidson.ian.advent.year2018.day03;

public record Coordinate(int r, int c) {

    public static Coordinate newCoordinate(final String input){
        String [] parts = input.split(",");
        return new Coordinate(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1].substring(parts[1].length()-1))
        );
    }
}
