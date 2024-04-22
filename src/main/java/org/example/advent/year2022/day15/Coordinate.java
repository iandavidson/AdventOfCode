package org.example.advent.year2022.day15;

import java.util.Arrays;

public record Coordinate(int x, int y) {

    public static Coordinate newCoordinate(String line){
        String [] groups = line.split(",");
        return new Coordinate(Integer.parseInt(groups[0].trim().substring(2)), Integer.parseInt(groups[1].trim().substring(2)));
    }
}
