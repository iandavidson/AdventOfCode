package dev.davidson.ian.advent.year2015.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class PerfectlySphericalHousesInAVacuum {

    private static final String INPUT_PATH = "adventOfCode/2015/day03/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day03/sample.txt";

    private static final Map<Character, Coordinate> SHIFT_MAP = Map.of(
            '>', new Coordinate(0, 1),
            '<', new Coordinate(0, -1),
            '^', new Coordinate(-1, 0),
            'v', new Coordinate(1, 0)
    );

    public static void main(String[] args) {
        PerfectlySphericalHousesInAVacuum perfectlySphericalHousesInAVacuum = new PerfectlySphericalHousesInAVacuum();
        System.out.println(perfectlySphericalHousesInAVacuum.part1());
        System.out.println(perfectlySphericalHousesInAVacuum.part2());
    }

    public Integer part1() {
        char[] inputs = readFile();
        Set<Coordinate> houseCount = new HashSet<>();
        Coordinate current = new Coordinate(0, 0);
        houseCount.add(current);

        for (char ch : inputs) {
            current = Coordinate.newCoordinate(current, SHIFT_MAP.get(ch));

            houseCount.add(current);
        }

        return houseCount.size();
    }

    public Integer part2() {
        char[] inputs = readFile();
        Set<Coordinate> houseCount = new HashSet<>();
        Coordinate realSanta = new Coordinate(0, 0);
        Coordinate robotSanta = new Coordinate(0, 0);
        houseCount.add(realSanta);

        for (int i = 0; i < inputs.length; i++) {
            if (i % 2 == 0) {
                //real
                realSanta = Coordinate.newCoordinate(realSanta, SHIFT_MAP.get(inputs[i]));
                houseCount.add(realSanta);
            } else {
                //robo
                robotSanta = Coordinate.newCoordinate(robotSanta, SHIFT_MAP.get(inputs[i]));
                houseCount.add(robotSanta);
            }
        }

        return houseCount.size();
    }


    private char[] readFile() {
        ClassLoader cl = PerfectlySphericalHousesInAVacuum.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                return scanner.nextLine().toCharArray();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return new char[]{};
    }
}
