package dev.davidson.ian.advent.year2016.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SquaresWithThreeSides {

    private static final String INPUT_PATH = "adventOfCode/2016/day03/input.txt";

    public static void main(String[] args) {
        SquaresWithThreeSides squaresWithThreeSides = new SquaresWithThreeSides();
        log.info("Part1: {}", squaresWithThreeSides.part1());
        log.info("Part2: {}", squaresWithThreeSides.part2());
    }

    public long part1() {
        List<String> lines = readFile();
        List<Triangle> triangles = new ArrayList<>();

        for (String line : lines) {
            triangles.add(Triangle.newTriangle(
                    Arrays.stream(line.trim().split("\\s+")).map(Integer::parseInt).toList()
            ));
        }

        return triangles.stream().filter(Triangle::isValid).count();
    }

    public long part2(){
        List<String> lines = readFile();
        PossibleTriangle p1 = new PossibleTriangle(new ArrayList<>());
        PossibleTriangle p2 = new PossibleTriangle(new ArrayList<>());
        PossibleTriangle p3 = new PossibleTriangle(new ArrayList<>());

        for(String line : lines){
            String [] chunks = line.trim().split("\\s+");
            p1.possibleSides().add(Integer.parseInt(chunks[0]));
            p2.possibleSides().add(Integer.parseInt(chunks[1]));
            p3.possibleSides().add(Integer.parseInt(chunks[2]));
        }

        return p1.possibleCombos() + p2.possibleCombos() + p3.possibleCombos();
    }

    private List<String> readFile() {
        List<String> inputLines = new ArrayList<>();

        ClassLoader cl = SquaresWithThreeSides.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return inputLines;
    }
}
