package org.example.advent.year2022.day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class RegolithReservoir {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day14/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day14/input.txt";
    private static final Coordinate DROP_SPOT = new Coordinate(500L, 0L);

    public static void main(String [] args){
        RegolithReservoir regolithReservoir = new RegolithReservoir();
        System.out.println("part1: " + regolithReservoir.part1());
    }

    public Long part1(){
        List<RockFormation> rockFormations = readFile();
        Set<Coordinate> fallenSandLocations = new HashSet<>();

        while(!fallenSandLocations.contains(DROP_SPOT)){
            //The sand is pouring into the cave from point 500,0.

            Coordinate sand = new Coordinate(500L, 0L);
            Boolean stillFalling = true;
            while(stillFalling){
                //while -> for new sand block falling, iteration for each layer falling down

                boolean neverMoved = true;
                Coordinate next = null;
                for(List<Integer> changes : List.of(List.of(0,1), List.of(-1,1), List.of(1,1))){
                    next = new Coordinate(sand.x() + changes.getFirst(), sand.y() + changes.get(1));

                    if(locationValid(rockFormations, fallenSandLocations, next)){
                        neverMoved = false;
                        break;
                    }
                }

                if(neverMoved){
                    stillFalling = false;
                } else {
                    sand = next;
                }


                /*
                boolean neverMoved => true

                attempt to move down
                    if not possible
                attempt to move down and to left
                    if not possible
                attempt to move down and to right

                How to check:
                    if fallenSandLocations.contains(x,y)
                    for all rockFormatations
                        -> rockFormation.doesCollide(x,y)

                 */
                /*
//
//                boolean neverMoved = true;
//
//
//                if(!fallenSandLocations.contains(sand)){
//
//                }
//
//                boolean allowedDown = true;
//                //attempt to move down
//                for(RockFormation rockFormation : rockFormations){
//                    if(rockFormation.doesCollide(sand.x(), sand.y() + 1)){
//                        allowedDown = false;
//                        break;
//                    }
//                }
//
//                if(allowedDown){
//
//                }
//
//
//
//
//                //
//                for(List<Integer> changes : List.of(List.of(0,1), List.of(-1,1), List.of(1,1))){
//                    //if directly below is open
//                    //if below and to left is open
//                    //if below and to right is open
//
//                    Boolean allowed = true;
//
//                    for(RockFormation rockFormation : rockFormations){
//                        if(rockFormation.doesCollide(sand.x() + changes.getFirst(), sand.y() + changes.get(1))){
//                            allowed = false;
//                            break;
//                        }
//                    }
//
//                    if(allowed){
//                        sand = new Coordinate(sand.x() + changes.getFirst(), sand.y()+ changes.get(1));
//                        neverMoved = false;
//                        break;
//                    }
//                }
//
//                if(neverMoved){
//                    stillFalling = false;
//                }
*/
            }

            System.out.println("just added: " + sand.x() + "," + sand.y());
            fallenSandLocations.add(sand);
        }

        return (long) fallenSandLocations.size();
    }

    private Boolean locationValid(final List<RockFormation> rockFormations, final Set<Coordinate> fallenSandSet, final Coordinate current){
        //check if fallenSandSet contains
        if(fallenSandSet.contains(current)){
            return false;
        }

        for(RockFormation rockFormation : rockFormations){
            if(rockFormation.doesCollide(current.x(), current.y())){
                return false;
            }
        }

        return true;
    }

    private List<RockFormation> readFile(){
        List<RockFormation> rockFormations = new ArrayList<>();

        ClassLoader cl = RegolithReservoir.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                rockFormations.add(new RockFormation(scanner.nextLine()));
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

        return rockFormations;
    }

}
