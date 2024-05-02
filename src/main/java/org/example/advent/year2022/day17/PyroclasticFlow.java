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

    /*
    part 2 intuition:

    look for condition: streamIndex % jetStream.size() == 0
    this should give enough information to naively figure out cycle size, height gained per cycle, etc.

change in rocks per cycle of jetstream: 1715
0
1723
3438
5153
6868
8583


height diffs with respect to above; diff: 2574
0
2605
5179
7753
10327
12901

     */

    private static final String SAMPLE_PATH = "adventOfCode/2022/day17/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day17/input.txt";

    public static void main(String[] args) {
        PyroclasticFlow pyroclasticFlow = new PyroclasticFlow();
        System.out.println("Part1: " + pyroclasticFlow.part1());
        System.out.println("Part2: " + pyroclasticFlow.part2());
    }


    public long part1() {
        List<DIRECTION> jetStream = readFile();
        return dropRocks(2022L, jetStream, false);
    }

    public long part2() {
        List<DIRECTION> jetStream = readFile();
        return dropRocks(1730, jetStream, true);
    }

    private List<DIRECTION> readFile() {
        ClassLoader cl = PyroclasticFlow.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        List<DIRECTION> jetStream = null;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                jetStream = Arrays.stream(scanner.nextLine().split("")).map(str -> str.equals("<") ? DIRECTION.LEFT : DIRECTION.RIGHT).toList();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return jetStream;
    }

    private long dropRocks(final long remainingRocks, final List<DIRECTION> jetStream, final boolean part2) {
        List<ROCKTYPE> rockTypes = List.of(ROCKTYPE.values());
        List<Rock> rocks = new ArrayList<>();

        int currentHeight = 0;
        int rockCount = 0;
        int streamIndex = 0;

        while (rockCount < remainingRocks) {

            ROCKTYPE nextToBeDropped = rockTypes.get(rockCount % 5);
            Rock current = nextDropped(nextToBeDropped, currentHeight);
            boolean stillFalling = true;

            while (stillFalling) {

                DIRECTION direction = jetStream.get(streamIndex % jetStream.size());

                Rock next = current.applyJetStream(direction, rocks);
                if (next != null) {
                    current = next;
                }

                streamIndex++;

                next = current.applyMoveDown(rocks);
                if (next != null) {
                    // next contains current rock with y-1
                    current = next;
                } else {
                    stillFalling = false;
                    currentHeight = Math.max(currentHeight, current.highestY());
                }
            }

            rocks.add(current);
            rockCount++;
            Collections.sort(rocks);

        }

        // 1 Trillion // |myCycleSize(1715)| => 583090379L
        // height gained from one cycle => 2574L
        //method actually passes over 1 full cycle plus offset (1Trillion % |cycle|), so subtract 1 from 583...9L
        long part2Rest = 583090378L * 2574L; //height gained from cycles we aren't going to simulate.
        return part2 ? currentHeight + part2Rest : currentHeight;
    }


    private Rock nextDropped(final ROCKTYPE rocktype, final int currentHeight) {
        List<Coordinate> coordinates = new ArrayList<>();
        int adjustedHeight = currentHeight + 4;
        int highestY;
        int leftMostX;
        int rightMostX;
        switch (rocktype) {
            case HORZ -> {
                coordinates.add(new Coordinate(2, adjustedHeight));
                coordinates.add(new Coordinate(3, adjustedHeight));
                coordinates.add(new Coordinate(4, adjustedHeight));
                coordinates.add(new Coordinate(5, adjustedHeight));
                highestY = adjustedHeight;
                leftMostX = 2;
                rightMostX = 5;

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
                leftMostX = 2;
                rightMostX = 4;

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
                leftMostX = 2;
                rightMostX = 4;

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
                leftMostX = 2;
                rightMostX = 2;
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
                leftMostX = 2;
                rightMostX = 3;
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

        return new Rock(coordinates, rocktype, highestY, leftMostX, rightMostX);
    }
}
