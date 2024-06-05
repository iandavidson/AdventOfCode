package dev.davidson.ian.advent.year2015.day18;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static dev.davidson.ian.advent.year2015.day18.Status.OFF;
import static dev.davidson.ian.advent.year2015.day18.Status.ON;

@Slf4j
public class GifForYourYard {

    private static final Integer GRID_LENGTH = 100;
    private static final Integer STEPS = 100;
    private static final String INPUT_PATH = "adventOfCode/2015/day18/input.txt";
    private static final Map<Character, Status> TILE_TO_STATUS = Map.of('#', ON, '.', OFF);
    private static final Map<Status, Character> STATUS_TO_TILE = Map.of(ON, '#', OFF, '.');

    public static void main(String[] args) {
        GifForYourYard gifForYourYard = new GifForYourYard();
        log.info("Part1: {}", gifForYourYard.part1());
    }

    public int part1() {
        char[][] grid = readFile();

        for (int i = 0; i < STEPS; i++) {
            grid = nextStep(grid);
        }

        int count = 0;
        for (char[] row : grid) {
            for (char ch : row) {
                if (TILE_TO_STATUS.get(ch) == ON) {
                    count++;
                }
            }
        }

        // 384 too low
        return count;
    }

    private char[][] nextStep(char[][] grid) {
        char[][] nextGrid = new char[GRID_LENGTH][GRID_LENGTH];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                nextGrid[i][j] = STATUS_TO_TILE.get(nextLightValue(grid, i, j, TILE_TO_STATUS.get(grid[i][j])));
            }
        }

        return nextGrid;

    }

    private Status nextLightValue(char[][] grid, int r, int c, Status light) {
        int count = 0;
        for (int i = r - 1; i < r + 2; i++) {
            for (int j = c - 1; j < c + 2; j++) {
                if (Math.min(i, j) >= 0 && Math.max(i, j) <= GRID_LENGTH - 1) { //in bounds
                    if (grid[i][j] == STATUS_TO_TILE.get(ON)) {
                        count++;
                    }
                }
            }
        }

        if (light.equals(ON)) {
            return (count == 2 || count == 3) ? ON : OFF;
        } else {
            return count == 3 ? ON : OFF;
        }
    }

    private char[][] readFile() {
        char[][] grid = new char[GRID_LENGTH][GRID_LENGTH];
        ClassLoader cl = GifForYourYard.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {
                grid[i] = scanner.nextLine().toCharArray();
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        return grid;
    }
}
