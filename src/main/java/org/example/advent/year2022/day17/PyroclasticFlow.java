package org.example.advent.year2022.day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PyroclasticFlow {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day17/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day17/input.txt";

    private static final int LEFT_BOUNDARY = 0;
    private static final int RIGHT_BOUNDARY = 6;

    public static void main(String[] args) {
        PyroclasticFlow pyroclasticFlow = new PyroclasticFlow();
        System.out.println("Part1: " + pyroclasticFlow.part1());
    }


    public int part1() {
        List<DIRECTION> jetStream = readFile();
        return dropRocks(2022, jetStream);
    }

    private List<DIRECTION> readFile() {
        ClassLoader cl = PyroclasticFlow.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        List<DIRECTION> jetStream = null;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                jetStream = Arrays.stream(scanner.nextLine().split("")).map(str -> str.equals(">") ? DIRECTION.LEFT : DIRECTION.RIGHT).toList();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return jetStream;
    }

    private int dropRocks(final int remainingRocks, final List<DIRECTION> jetStream) {
        List<ROCKTYPE> rockTypes = List.of(ROCKTYPE.values());
        List<Rock> rocks = new ArrayList<>();

        int currentHeight = 0;
        int rockCount = 0;
        int streamIndex = 0;

        while (rockCount < remainingRocks && streamIndex < jetStream.size()) {

            ROCKTYPE nextToBeDropped = rockTypes.get(rockCount % 5);

            Rock beforeDropped = nextDropped(nextToBeDropped, currentHeight);

            //first move with jetStream

            //then move down



            Collections.sort(rocks);
        }

        return currentHeight;
    }


    private Rock nextDropped(final ROCKTYPE rocktype, final int currentHeight) {
        List<Coordinate> coordinates = new ArrayList<>();
        int adjustedHeight = currentHeight + 3;
        int highestY = 0;
        switch (rocktype) {
            case HORZ -> {
                coordinates.add(new Coordinate(2, adjustedHeight));
                coordinates.add(new Coordinate(3, adjustedHeight));
                coordinates.add(new Coordinate(4, adjustedHeight));
                coordinates.add(new Coordinate(5, adjustedHeight));
                highestY = adjustedHeight;
/*
|..@@@@.|
|.......|
|.......|
|.......|
+-------+
*/
            }
            case PLUS -> {
                coordinates.add(new Coordinate(3, adjustedHeight));
                coordinates.add(new Coordinate(3, adjustedHeight + 1));
                coordinates.add(new Coordinate(3, adjustedHeight + 2));
                coordinates.add(new Coordinate(2, adjustedHeight + 1));
                coordinates.add(new Coordinate(4, adjustedHeight + 1));
                highestY = adjustedHeight + 2;

/*
|...@...|
|..@@@..|
|...@...|
|.......|
|.......|
|.......|
|..####.|
 */
            }
            case L -> {
                coordinates.add(new Coordinate(2, adjustedHeight));
                coordinates.add(new Coordinate(3, adjustedHeight));
                coordinates.add(new Coordinate(4, adjustedHeight));
                coordinates.add(new Coordinate(4, adjustedHeight + 1));
                coordinates.add(new Coordinate(4, adjustedHeight + 2));
                highestY = adjustedHeight + 2;

/*
|....@..|
|....@..|
|..@@@..|
|.......|
|.......|
|.......|
|...#...|
 */
            }
            case VERT -> {
                coordinates.add(new Coordinate(2, adjustedHeight));
                coordinates.add(new Coordinate(2, adjustedHeight + 1));
                coordinates.add(new Coordinate(2, adjustedHeight + 2));
                coordinates.add(new Coordinate(2, adjustedHeight + 3));
                highestY = adjustedHeight + 3;
/*
|..@....|
|..@....|
|..@....|
|..@....|
|.......|
|.......|
|.......|
|..#....|
 */
            }
            case SQUARE -> {
                coordinates.add(new Coordinate(2, adjustedHeight));
                coordinates.add(new Coordinate(3, adjustedHeight));
                coordinates.add(new Coordinate(2, adjustedHeight + 1));
                coordinates.add(new Coordinate(3, adjustedHeight + 1));
                highestY = adjustedHeight + 1;
/*
|..@@...|
|..@@...|
|.......|
|.......|
|.......|
|....#..|
 */

            }
            case null, default -> {
                throw new IllegalStateException("🥸");
            }

        }

        return Rock.newRock(coordinates, rocktype, highestY);
    }
}
