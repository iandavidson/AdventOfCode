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

    (|potential shapes| * |jet-stream|) * (n) == cycle size

    (5 * 10092) * (n); how to find N

    we can naively detect cycle by checking:
        - see the same rock 3 times,
           - same x values
           - same difference between y values

           //we kind of expect that the difference in streamIndex for each is larger than |jet-stream|, maybe even multiple |jet-streams|



           We see state of the top repeat it self
     */

    private static final String SAMPLE_PATH = "adventOfCode/2022/day17/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day17/input.txt";

    public static void main(String[] args) {
        PyroclasticFlow pyroclasticFlow = new PyroclasticFlow();
//        System.out.println("Part1: " + pyroclasticFlow.part1());
        System.out.println("Part2: " + pyroclasticFlow.part2());
    }


    public int part1() {
        List<DIRECTION> jetStream = readFile();
        return dropRocks(2022L, jetStream);
    }

    public int part2() {
        List<DIRECTION> jetStream = readFile();
        return dropRocks(1_000_000_000_000L, jetStream);
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

    private int dropRocks(final long remainingRocks, final List<DIRECTION> jetStream) {
        List<ROCKTYPE> rockTypes = List.of(ROCKTYPE.values());
        List<Rock> rocks = new ArrayList<>();
//        List<Integer> cycleStatesL = new ArrayList<>();
        List<CycleState> cycleStates = new ArrayList<>();

        int currentHeight = 0;
        int rockCount = 0;
        int streamIndex = 0;
        int priorLIndex = 0;
        int priorLRockIndex = 0;

        while (rockCount < remainingRocks) {

            ROCKTYPE nextToBeDropped = rockTypes.get(rockCount % 5);
            Rock current = nextDropped(nextToBeDropped, currentHeight);
            boolean stillFalling = true;

            while (stillFalling) {

                DIRECTION direction = jetStream.get(streamIndex % jetStream.size());

                Rock next = current.applyJetStream(direction, rocks);
                if(next != null){
                    current = next;
                }

                streamIndex++;

                next = current.applyMoveDown(rocks);
                if(next != null){
                    // next contains current rock with y-1
                    current = next;
                }else{
                    stillFalling = false;
                    currentHeight = Math.max(currentHeight, current.highestY());
                }
            }

            if(current.rockType() == ROCKTYPE.L){
//                System.out.println("StreamIndex: " + streamIndex + ", moddedIndex: " + (streamIndex % jetStream.size()) + " ; rockType: " + current.rockType().name() + "; indexDiff from last: " + (streamIndex - priorLIndex));
//                int jetDiff = ;
                CycleState cycleState = new CycleState(streamIndex - priorLIndex, rockCount);
                for(CycleState other : cycleStates){
                    if(cycleState.equals(other)){

                        System.out.println("potential cycle, diff: " + cycleState.jetDiff() + "; diff in rock#: " + (cycleState.rockNumber() - other.rockNumber()));
//                        break;
                    }
                }

                System.out.println("\n");
                cycleStates.add(cycleState);
//                System.out.println("rock#: " + rockCount + "; jetstreamIndex Diff from last L: " + (streamIndex - priorLIndex));

                priorLRockIndex = rockCount;
                priorLIndex = streamIndex;

            }

            rocks.add(current);
            rockCount++;
            Collections.sort(rocks);
        }

        return currentHeight;
    }


    private Rock nextDropped(final ROCKTYPE rocktype, final int currentHeight) {
        List<Coordinate> coordinates = new ArrayList<>();
        int adjustedHeight = currentHeight + 4;
        int highestY = 0;
        int leftMostX = 0;
        int rightMostX = 7;
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
