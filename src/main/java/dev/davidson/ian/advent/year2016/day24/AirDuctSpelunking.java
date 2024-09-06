package dev.davidson.ian.advent.year2016.day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AirDuctSpelunking {
    private static final String SAMPLE_PATH = "adventOfCode/2016/day24/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day24/input.txt";
    private static final Character WALL = '#';
    private static final Character SPACE = '.';

    public static void main(String[] args) {
        AirDuctSpelunking airDuctSpelunking = new AirDuctSpelunking();
        log.info("Part1: {}", airDuctSpelunking.part1());
    }


    public Integer part1() {
        List<List<Character>> grid = readFile();
        int rows = grid.size();
        int cols = grid.getFirst().size();

        int r = 1;
        int c = 1;
    }

    private List<List<Character>> readFile() {
        List<List<Character>> grid = new ArrayList<>();

        ClassLoader cl = AirDuctSpelunking.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                grid.add(
                        scanner.nextLine()
                                .chars()
                                .mapToObj(ch -> (char) ch)
                                .toList()
                );
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided file path");
        }

        return grid;
    }
}
