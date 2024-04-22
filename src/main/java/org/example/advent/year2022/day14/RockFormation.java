package org.example.advent.year2022.day14;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RockFormation {//implements Comparable<RockFormation> {
    private final List<Coordinate> pathCorners;

    public RockFormation(String line){
        List<Coordinate> pathCorners = new ArrayList<>();

        String [] coords = line.split("->");
        for(String coord : coords){
            String [] axis = coord.split(",");
            pathCorners.add(new Coordinate(Integer.parseInt(axis[0].trim()), Integer.parseInt(axis[1].trim())));
        }

        this.pathCorners = pathCorners;
    }
}
