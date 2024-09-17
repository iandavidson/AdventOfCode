package dev.davidson.ian.advent.year2017.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HexEd {

    private static final String INPUT_PATH = "adventOfCode/2017/day11/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day11/sample.txt";

                            /*
                          \ n  /
                        nw +--+ ne
                          /    \
                        -+      +-
                          \    /
                        sw +--+ se
                          / s  \

             SE, SW is the same as S
             NE, NW is the same as N
             which means N and S should shift vertically 2 coordinate spaces
             while diagonal movements should only shift vert and horz by 1
                             */

    private static final Map<String, int[]> SHIFTS = Map.of(
            "n", new int[]{-2, 0},
            "ne", new int[]{-1, 1},
            "nw", new int[]{-1, -1},
            "s", new int[]{2, 0},
            "se", new int[]{1, 1},
            "sw", new int[]{1, -1}
    );

    public static void main(String[] args) {
        HexEd hexEd = new HexEd();
        List<String> input = readFile(INPUT_PATH);
        log.info("Part1: {}", hexEd.part1(input));
        log.info("Part2: {}", hexEd.part2(input));
    }

    private static List<String> readFile(final String filePath) {
        ClassLoader cl = HexEd.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return Arrays.stream(scanner.nextLine().split(",")).toList();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Failed to read file at provided path");
        }
    }

    public Integer part1(final List<String> inputs) {
        int currentRow = 0;
        int currentCol = 0;
        for (String input : inputs) {
            currentRow += SHIFTS.get(input)[0];
            currentCol += SHIFTS.get(input)[1];
        }

        return Math.abs(Math.abs(currentRow) - Math.abs(currentCol)) / 2 + Math.abs(currentCol);
    }

    public Integer part2(final List<String> inputs) {
        int max = 0;
        int currentRow = 0;
        int currentCol = 0;
        for (String input : inputs) {
            currentRow += SHIFTS.get(input)[0];
            currentCol += SHIFTS.get(input)[1];
            max = Math.max(max, Math.abs(Math.abs(currentRow) - Math.abs(currentCol)) / 2 + Math.abs(currentCol));
        }

        return max;
    }

}
