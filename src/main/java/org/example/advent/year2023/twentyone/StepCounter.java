package org.example.advent.year2023.twentyone;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class StepCounter {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day21/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day21/input.txt";
    private Coordinate start = null;

    private List<List<TILE>> readFile() {
        List<List<TILE>> grid = new ArrayList<>();

        try {
            ClassLoader classLoader = StepCounter.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(SAMPLE_INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            int i =0;
            while (myReader.hasNextLine()) {
                List<TILE> row = new ArrayList<>();
                int j = 0;
                for (Character ch : myReader.nextLine().toCharArray()) {
                    if(ch == 'S'){
                        start = Coordinate.builder().row(i).col(j).build();
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

    public int part1() {
        List<List<TILE>> grid = readFile();

    }


    public static void main(String[] args) {
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
