package org.example.advent.year2023.twentyone;

import lombok.extern.java.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

@Log
public class StepCounter {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day21/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day21/input.txt";
    private int startRow = -1;
    private int startCol = -1;

    private List<List<TILE>> readFile() {
        List<List<TILE>> grid = new ArrayList<>();

        try {
            ClassLoader classLoader = StepCounter.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(SAMPLE_INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            int i = 0;
            while (myReader.hasNextLine()) {
                List<TILE> row = new ArrayList<>();
                int j = 0;
                for (Character ch : myReader.nextLine().toCharArray()) {
                    if (ch == 'S') {
                        startRow = i;
                        startCol = j;
                    }
                    row.add(TILE.gridMap.get(ch));
                    j++;
                }
                grid.add(row);
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return grid;
    }

    private static boolean isInBounds(List<List<TILE>> grid, int row, int col) {
        if (grid.get(row).get(col) == TILE.ROCK || row > grid.size() - 1 || row < 0 || col > grid.get(0).size() - 1 || col < 0) {
            return false;
        }

        return true;
    }

    private static List<WalkState> getValidNeighbors(List<List<TILE>> grid, WalkState walkState) {
        List<WalkState> walkStates = new ArrayList<>();

        //up
        if (isInBounds(grid, walkState.getRow() - 1, walkState.getCol())) {
            walkStates.add(WalkState.builder()
                    .coordinate(
                            Coordinate.builder()
                                    .row(walkState.getRow() - 1)
                                    .col(walkState.getCol())
                                    .build())
                    .stepsRemaining(walkState.getStepsRemaining() - 1)
                    .build());
        }

        //down
        if (isInBounds(grid, walkState.getRow() + 1, walkState.getCol())) {
            walkStates.add(WalkState.builder()
                    .coordinate(
                            Coordinate.builder()
                                    .row(walkState.getRow() + 1)
                                    .col(walkState.getCol())
                                    .build())
                    .stepsRemaining(walkState.getStepsRemaining() - 1)
                    .build());
        }

        //right
        if (isInBounds(grid, walkState.getRow(), walkState.getCol() + 1)) {
            walkStates.add(WalkState.builder()
                    .coordinate(
                            Coordinate.builder()
                                    .row(walkState.getRow())
                                    .col(walkState.getCol() + 1)
                                    .build())
                    .stepsRemaining(walkState.getStepsRemaining() - 1)
                    .build());
        }

        //left
        if (isInBounds(grid, walkState.getRow(), walkState.getCol() - 1)) {
            walkStates.add(WalkState.builder()
                    .coordinate(
                            Coordinate.builder()
                                    .row(walkState.getRow())
                                    .col(walkState.getCol() - 1)
                                    .build())
                    .stepsRemaining(walkState.getStepsRemaining() - 1)
                    .build());
        }

        return walkStates;
    }


    private static int walk(List<List<TILE>> grid, WalkState start) {
        Set<WalkState> seenStates = new HashSet<>();

        Queue<WalkState> queue = new LinkedList<>();
        queue.add(start);
        seenStates.add(start);
        int count = 0;

        while (!queue.isEmpty()) {
            WalkState current = queue.remove();

            if (current.getStepsRemaining() == 0) {
                count++;

            } else {
                seenStates.remove(current);
                List<WalkState> neighbors = getValidNeighbors(grid, current);
                for (WalkState walkState : neighbors) {
                    if (!seenStates.contains(walkState)) {
                        seenStates.add(walkState);
                        queue.add(walkState);
                    }
                }
            }

        }

        return count;
    }

    public int part1() {
        List<List<TILE>> grid = readFile();
        WalkState start = WalkState.builder()
                .coordinate(
                        Coordinate.builder().row(startRow).col(startCol).build())
                .stepsRemaining(64).build();

        return walk(grid, start);
    }

    private static List<WalkState> getNeighborsPart2(List<List<TILE>> grid, WalkState walkState){
        List<WalkState> walkStates = new ArrayList<>();

        //use this for mod: Math.floorMod( , )

        //up
        if(grid.get((walkState.getRow() -1) % grid.size()).get(walkState.getCol() % grid.get(0).size()) != TILE.ROCK){
//        if (isInBounds(grid, walkState.getRow() - 1, walkState.getCol())) {
            walkStates.add(WalkState.builder()
                    .coordinate(
                            Coordinate.builder()
                                    .row(walkState.getRow() - 1)
                                    .col(walkState.getCol())
                                    .build())
                    .stepsRemaining(walkState.getStepsRemaining() - 1)
                    .build());
        }

        //down
        if(grid.get((walkState.getRow() +1) % grid.size()).get(walkState.getCol() % grid.get(0).size()) != TILE.ROCK){
//        if (isInBounds(grid, walkState.getRow() + 1, walkState.getCol())) {
            walkStates.add(WalkState.builder()
                    .coordinate(
                            Coordinate.builder()
                                    .row(walkState.getRow() + 1)
                                    .col(walkState.getCol())
                                    .build())
                    .stepsRemaining(walkState.getStepsRemaining() - 1)
                    .build());
        }

        //right
        if(grid.get(walkState.getRow() % grid.size()).get((walkState.getCol() + 1) % grid.get(0).size()) != TILE.ROCK){
//        if (isInBounds(grid, walkState.getRow(), walkState.getCol() + 1)) {
            walkStates.add(WalkState.builder()
                    .coordinate(
                            Coordinate.builder()
                                    .row(walkState.getRow())
                                    .col(walkState.getCol() + 1)
                                    .build())
                    .stepsRemaining(walkState.getStepsRemaining() - 1)
                    .build());
        }

        //left
//        if (isInBounds(grid, walkState.getRow(), walkState.getCol() - 1)) {
        if(grid.get(walkState.getRow() % grid.size()).get((walkState.getCol() - 1) % grid.get(0).size()) != TILE.ROCK){
            walkStates.add(WalkState.builder()
                    .coordinate(
                            Coordinate.builder()
                                    .row(walkState.getRow())
                                    .col(walkState.getCol() - 1)
                                    .build())
                    .stepsRemaining(walkState.getStepsRemaining() - 1)
                    .build());
        }

        return walkStates;
    }

    private static long walkPart2(List<List<TILE>> grid, WalkState start){
        Set<WalkState> seenStates = new HashSet<>();

        Queue<WalkState> queue = new LinkedList<>();
        queue.add(start);
        seenStates.add(start);
        long count = 0;

        while (!queue.isEmpty()) {
            WalkState current = queue.remove();

            if (current.getStepsRemaining() == 0) {
                count++;

            } else {
                seenStates.remove(current);
                List<WalkState> neighbors = getNeighborsPart2(grid, current);
                for (WalkState walkState : neighbors) {
                    if (!seenStates.contains(walkState)) {
                        seenStates.add(walkState);
                        queue.add(walkState);
                    }
                }
            }

        }

        return count;
    }

    public long part2(){
        List<List<TILE>> grid = readFile();
        WalkState start = WalkState.builder()
                .coordinate(
                        Coordinate.builder().row(startRow).col(startCol).build())
                .stepsRemaining(500).build();

        return walkPart2(grid, start);
    }


    public static void main(String[] args) {
        StepCounter stepCounter = new StepCounter();
//        log.info("Part1: " + stepCounter.part1());

        log.info("Part2: " + stepCounter.part2());
        /*
        so the problem says how many garden plots can you reach in exactly 64 steps. Should I track state while processing by doing 1 or 2?

        1. Queue<{coordinate{x,y}, remainingSteps}>; then look if left / right / up / down are plots to move to; add all to queue & decrement remainingSteps
        -> additionally use a set to determine if we need to add next directional step to queue.
        --> @Override::equals() { coordinate, remainingSteps }

        2. Queue<{coordinate{x,y}, List<remainingSteps>}>; list represents the different amount of steps remaining that you can end up at that tile.
        --> using list may allow us to process more concurrently. But sounds like more unneeded complexity in class.
         */
    }
}
