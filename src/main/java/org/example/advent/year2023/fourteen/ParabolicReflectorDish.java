package org.example.advent.year2023.fourteen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParabolicReflectorDish {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day14/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day14/input.txt";

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = ParabolicReflectorDish.class.getClassLoader();
            File file = new File(classLoader.getResource(SAMPLE_INPUT_PATH).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    private static List<List<String>> processInput(List<String> inputs) {
        List<List<String>> grids = new ArrayList<>();
        List<String> grid = new ArrayList<>();
        for (String row : inputs) {
            if (row.isBlank()) {
                grids.add(grid);
                grid = new ArrayList<>();
            } else {
                grid.add(row);
            }
        }

        grids.add(grid);

        return grids;
    }

    public static Long part1() {
        List<String> inputLines = readFile();
        List<List<String>> inputs = processInput(inputLines);

        return 0L;
    }

    public static void main(String[] args) {
        Long result = part1();

    }
}
