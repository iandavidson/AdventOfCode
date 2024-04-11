package org.example.advent.year2022.day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class RopeBridge {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day09/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day09/input.txt";

    public static void main(String[] args) {
        RopeBridge ropeBridge = new RopeBridge();
        System.out.println("part1: " + ropeBridge.part1());
    }

    public long part1() {
        List<Instruction> instructions = readFile();
        return executeInstructions(instructions);
    }

    private List<Instruction> readFile() {
        List<String> inputLines = new ArrayList<>();
        ClassLoader cl = RopeBridge.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.err.println("File could not be found");
            throw new RuntimeException(e);
        }


        List<Instruction> instructions = new ArrayList<>();

        for (String line : inputLines) {
            String[] chunks = line.split("\\s+");
            instructions.add(new Instruction(Direction.valueOf(chunks[0]), Integer.parseInt(chunks[1])));
        }

        return instructions;
    }


    private long executeInstructions(final List<Instruction> instructions) {
        Set<Coordinate> tailVisited = new HashSet<>();
        RopeKnot head = new RopeKnot(new Coordinate(0L, 0L));
        RopeKnot tail = new RopeKnot(new Coordinate(0L, 0L));
        tailVisited.add(tail.coordinate());
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.magnitude(); i++) {
                Coordinate headPrior = head.coordinate();

                //move head
                head = new RopeKnot(Coordinate.newCoordinate(headPrior, Direction.MOVE_MAP.get(instruction.direction())));

                //if tail location is still
                if (!isTailNeighbor(head, tail)) {
                    //if tail is no long a neighbor we'll need to move it to head's prior location
                    tailVisited.add(headPrior);
                    tail = new RopeKnot(headPrior);
                }
            }


        }

        return tailVisited.size();
    }

    private long executeInstructionsPart2(final List<Instruction> instructions) {
        Set<Coordinate> tailVisited = new HashSet<>();
        List<RopeKnot> ropeKnots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ropeKnots.add(new RopeKnot(new Coordinate(0L, 0L)));
        }

        tailVisited.add(new Coordinate(0L, 0L));

        for (Instruction instruction : instructions) {

            for (int i = 0; i < instruction.magnitude(); i++) {
                //head movement
                RopeKnot headPrior = ropeKnots.getFirst();

                RopeKnot headCurrent = new RopeKnot(Coordinate.newCoordinate(
                        headPrior.coordinate(),
                        Direction.MOVE_MAP.get(instruction.direction())));

                Boolean movedDiagonal = headCurrent.coordinate().isDiagonal(headPrior.coordinate());
                Direction moveDirection =

                ropeKnots.set(0, headCurrent);

                //9 tail segments movement
                for (int j = 1; j < 10; j++) {
                    //  ropeKnots.get(i)

                    //if head went diagonally, current should go same direction
                    if(movedDiagonal){

                    }

                    if (isTailNeighbor(headPrior, ropeKnots.get(j))) {
                        //if tail
                        headPrior = ropeKnots.get(j);
                    } else if (ropeKnots.get(j).coordinate().isDiagonal(headPrior.coordinate())) {
//                1 moved diagonal ^>
//                2 is no longer a neighbor of 1
//-> therefore 2 needs to go the same direction
//
//                need to keep track of prior direction of movement


                        //needs to move the same direction




                    /*

......
......
......
....H.
4321..

......
......
....H.
....P.
4321..

......
......
....H.
....1.
432P..

1 moved diagonal ^>
2 is no longer a neighbor of 1
-> therefore 2 needs to go the same direction



need to keep track of prior direction of movement

......
......
....H.
...21.
43P...

prior


                     */

                    }

                    //case that wasn't obvious in part 1: when non head moves diagonal, its follower must also

                    //reset headPrior
                }
            }


        }


        return tailVisited.size();
    }

    private boolean isTailNeighbor(final RopeKnot head, final RopeKnot tail) {
        return Math.abs(head.row() - tail.row()) < 2 && Math.abs(head.col() - tail.col()) < 2;
    }

    private Direction moveDirection(Coordinate current Coordinate prior){

    }

}
