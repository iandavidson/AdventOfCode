package dev.davidson.ian.advent.year2022.day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CampCleanup {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day04/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day04/input.txt";

    public static void main(String[] args) {
        CampCleanup campCleanup = new CampCleanup();
        System.out.println("Part1: " + campCleanup.part1());
        System.out.println("Part2: " + campCleanup.part2());
    }

    public int part1() {
        List<String> inputLines = readFile();
        List<Pair> pairs = processInput(inputLines);
        int count = 0;

        for (Pair pair : pairs) {
            if (pair.fullyContainsPartner()) {
                count++;
            }
        }

        return count;
    }

    public int part2() {
        List<String> inputLines = readFile();
        List<Pair> pairs = processInput(inputLines);
        int count = 0;

        for (Pair pair : pairs) {
            if (pair.anyOverlap()) {
                count++;
            }
        }

        return count;
    }

    private List<String> readFile() {
        List<String> lines = new ArrayList<>();
        try {
            ClassLoader classLoader = CampCleanup.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return lines;

    }

    private List<Pair> processInput(List<String> inputLines) {
        List<Pair> pairs = new ArrayList<>();

        for (String line : inputLines) {
            String[] split = line.split(",");
            String[] left = split[0].split("-");
            String[] right = split[1].split("-");

            pairs.add(new Pair(new Range(Integer.parseInt(left[0]), Integer.parseInt(left[1])), new Range(Integer.parseInt(right[0]), Integer.parseInt(right[1]))));
        }

        return pairs;
    }

}
