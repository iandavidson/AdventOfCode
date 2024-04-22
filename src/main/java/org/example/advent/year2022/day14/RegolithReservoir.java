package org.example.advent.year2022.day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class RegolithReservoir {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day14/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day14/input.txt";
    private static final Coordinate DROP_SPOT = new Coordinate(500, 0);


    public static void main(String[] args) {
        RegolithReservoir regolithReservoir = new RegolithReservoir();
        System.out.println("part1: " + regolithReservoir.part1());
        System.out.println("part2: " + regolithReservoir.part2());
    }

    public int part1() {
        List<RockFormation> rockFormations = readFile();

        Map<Coordinate, Wall> rockMap = new HashMap<>();
        // x , y
        buildMap(rockMap, rockFormations);
        int count = 0;

        while (!rockMap.containsKey(DROP_SPOT)) {
            //drop another piece of sand
            Coordinate coordinate = new Coordinate(DROP_SPOT.x(), DROP_SPOT.y());

            int fallingCount = 0;

            while (true) {

                //fall until it can't
                if (!rockMap.containsKey(new Coordinate(coordinate.x(), coordinate.y() + 1))) {
                    coordinate = new Coordinate(coordinate.x(), coordinate.y() + 1);
                    fallingCount++;
                    if(fallingCount > 100){
                        return count;
                    }

                } else if (!rockMap.containsKey(new Coordinate(coordinate.x() - 1, coordinate.y() + 1))) {
                    coordinate = new Coordinate(coordinate.x() - 1, coordinate.y() + 1);

                } else if (!rockMap.containsKey(new Coordinate(coordinate.x() + 1, coordinate.y() + 1))) {
                    coordinate = new Coordinate(coordinate.x() + 1, coordinate.y() + 1);

                } else {
                    rockMap.put(coordinate, Wall.SAND);
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    public Integer part2(){
        List<RockFormation> rockFormations = readFile();
        Map<Coordinate, Wall> rockMap = new HashMap<>();
        Integer highestY = buildMap(rockMap, rockFormations);

        int count = 0;

        while (!rockMap.containsKey(DROP_SPOT)) {
            //drop another piece of sand
            Coordinate coordinate = new Coordinate(DROP_SPOT.x(), DROP_SPOT.y());
            int z = 0;
            while (true) {

                if(coordinate.y() == highestY-1){
                    System.out.println("whoops");
                }

                //fall until it can't
                if (!rockMap.containsKey(new Coordinate(coordinate.x(), coordinate.y() + 1)) && (coordinate.y() + 1) < highestY ) {
                    coordinate = new Coordinate(coordinate.x(), coordinate.y() + 1);
                    z++;
                    if(z > 10){
                        System.out.println("whoops");
                    }

                } else if (!rockMap.containsKey(new Coordinate(coordinate.x() - 1, coordinate.y() + 1)) && (coordinate.y() + 1) < highestY) {
                    coordinate = new Coordinate(coordinate.x() - 1, coordinate.y() + 1);

                } else if (!rockMap.containsKey(new Coordinate(coordinate.x() + 1, coordinate.y() + 1)) && (coordinate.y() + 1) < highestY) {
                    coordinate = new Coordinate(coordinate.x() + 1, coordinate.y() + 1);

                } else {
                    rockMap.put(coordinate, Wall.SAND);
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    private Integer buildMap(Map<Coordinate, Wall> rockMap, final List<RockFormation> rockFormations){
        Integer highest = 0;
        for (RockFormation rockFormation : rockFormations) {
            for (int i = 1; i < rockFormation.getPathCorners().size(); i++) {
                Coordinate start = rockFormation.getPathCorners().get(i - 1);
                Coordinate end = rockFormation.getPathCorners().get(i);

                if (start.x() == end.x()) {

                    int min = Math.min(start.y(), end.y());
                    int max = Math.max(start.y(), end.y());

                    for (int j = min; j < max + 1; j++) {
                        rockMap.put(new Coordinate(start.x(), j), Wall.ROCK);
                    }
                } else {
                    //start.y() == end.y()

                    int min = Math.min(start.x(), end.x());
                    int max = Math.max(start.x(), end.x());

                    for (int j = min; j < max + 1; j++) {
                        rockMap.put(new Coordinate(j, start.y()), Wall.ROCK);
                    }
                }

                highest = Math.max(Math.max(start.y(), end.y()), highest);
            }
        }

        return highest;
    }

//    private Map<Coordinate, Wall> buildMap(final List<RockFormation> rockFormations) {
//
//    }

    private List<RockFormation> readFile() {
        List<RockFormation> rockFormations = new ArrayList<>();

        ClassLoader cl = RegolithReservoir.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                rockFormations.add(new RockFormation(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return rockFormations;
    }

}
