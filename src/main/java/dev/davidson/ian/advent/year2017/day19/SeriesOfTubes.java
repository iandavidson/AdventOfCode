package dev.davidson.ian.advent.year2017.day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeriesOfTubes {

    private static final String SAMPLE_PATH = "adventOfCode/2017/day19/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2017/day19/input.txt";
    private static final Set<Character> PATHS = Set.of('|', '-', '+');

    public static void main(String[] args) {
        SeriesOfTubes seriesOfTubes = new SeriesOfTubes();
        char[][] grid = readFile(INPUT_PATH);
        seriesOfTubes.execute(grid);
    }

    private static char[][] readFile(final String filePath) {
        List<String> lines = new ArrayList<>();
        int width = -1;

        ClassLoader cl = SeriesOfTubes.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                width = Math.max(width, line.length());
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        char[][] grid = new char[lines.size()][width];
        for (int i = 0; i < lines.size(); i++) {
            Arrays.fill(grid[i], ' ');
            for (int j = 0; j < lines.get(i).length(); j++) {
                grid[i][j] = lines.get(i).charAt(j);
            }
        }

        return grid;
    }

    public void execute(final char[][] grid) {
        Set<Character> letters = findLetters(grid);

        WalkState walkState = new WalkState(findStart(grid), Direction.D);
        char ch = grid[walkState.r()][walkState.c()];

        int stepCount = 0;
        StringBuilder sb = new StringBuilder();
        while (ch != ' ') {

            if (letters.contains(ch)) {
                sb.append(ch);
            }

            walkState = WalkState.nextStep(walkState, grid, PATHS, letters);
            ch = grid[walkState.r()][walkState.c()];
            stepCount++;
        }

        log.info("Part1: {}", sb);
        log.info("Part2: {}", stepCount);
    }

    private Set<Character> findLetters(final char[][] grid) {
        Set<Character> letters = new HashSet<>();
        for (char[] row : grid) {
            for (char ch : row) {
                if (ch != ' ' && !PATHS.contains(ch)) {
                    letters.add(ch);
                }
            }
        }

        return letters;
    }

    private Coordinate findStart(final char[][] grid) {
        int startCol = -1;
        for (int i = 0; i < grid[0].length; i++) {
            if (PATHS.contains(grid[0][i])) {
                startCol = i;
                break;
            }
        }

        return new Coordinate(0, startCol);
    }
}
