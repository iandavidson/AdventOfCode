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
            pathCorners.add(new Coordinate(Long.parseLong(axis[0].trim()), Long.parseLong(axis[1].trim())));
        }

        this.pathCorners = pathCorners;
    }

    public Boolean doesCollide(long x, long y){
        for(int i = 0; i < pathCorners.size()-1; i++){
            long xMin= Math.min(pathCorners.get(i).x(), pathCorners.get(i+1).x());
            long xMax = Math.max(pathCorners.get(i).x(), pathCorners.get(i+1).x());

            long yMin = Math.min(pathCorners.get(i).y(), pathCorners.get(i+1).y());
            long yMax = Math.max(pathCorners.get(i).y(), pathCorners.get(i+1).y());

            boolean withinX = x >= xMin && x <= xMax;
            boolean withinY = y >= yMin && y <= yMax;

            if(withinX && withinY){
                return true;
            }
        }

        return false;
    }
}
