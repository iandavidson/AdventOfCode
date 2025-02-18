package dev.davidson.ian.advent.year2024.day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuardGallivant {

    private static final String SAMPLE_PATH = "adventOfCode/2024/day06/sample.txt";
    private static final String REAL_PATH = "adventOfCode/2024/day06/real.txt";
    private static final Character GUARD = '^';
    private static final Character WALL = '#';
    private static final List<int[]> DIRECTIONS = List.of(
            new int[]{-1, 0},
            new int[]{0, 1},
            new int[]{1, 0},
            new int[]{0, -1}
    );

    public static void main(String[] args) {
        Grid sampleInput = readFile(SAMPLE_PATH);
        Grid realInput = readFile(REAL_PATH);

        GuardGallivant guardGallivant = new GuardGallivant();
        log.info("Part1 sample: {}", guardGallivant.part1(sampleInput));
        log.info("Part1 real: {}", guardGallivant.part1(realInput));

        log.info("Part2 sample: {}", guardGallivant.part2(sampleInput));
        log.info("Part2 real: {}", guardGallivant.part2(realInput));
    }

    private static Grid readFile(final String filePath) {
        List<String> input = new ArrayList<>();

        ClassLoader cl = GuardGallivant.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file");
        }

        return Grid.newGrid(input);
    }

    public Integer part1(final Grid grid) {

        Coordinate current = null;
        for (int i = 0; i < grid.rows(); i++) {
            for (int j = 0; j < grid.cols(); j++) {
                if (grid.grid().get(i).charAt(j) == GUARD) {
                    current = new Coordinate(i, j);
                }
            }
        }

        Set<Coordinate> traveled = new HashSet<>();
        int direction = 0;
        while (grid.inBounds(current)) {
            traveled.add(current);

            while (!canGoStraight(current, direction, grid)) {
                //turn to the right
                direction = (direction + 1) % 4;
            }
            current = move(current, direction);
        }

        return traveled.size();
    }

    public Integer part2(final Grid grid) {
        Coordinate current = null;
        for (int i = 0; i < grid.rows(); i++) {
            for (int j = 0; j < grid.cols(); j++) {
                if (grid.grid().get(i).charAt(j) == GUARD) {
                    current = new Coordinate(i, j);
                }
            }
        }

        int count = 0;
        for (int i = 0; i < grid.rows(); i++) {
            for (int j = 0; j < grid.cols(); j++) {
                //don't attempt to drop a wall where the guard starts
                if (i == current.r() && j == current.c()) {
                    continue;
                }

                if (part2(current, grid, i, j)) {
                    count++;
                }
            }
        }

        return count;
    }

    public boolean part2(final Coordinate start, final Grid oldGrid, final int r, final int c) {
        List<String> gridLines = new ArrayList<>(oldGrid.grid());

        StringBuilder sb = new StringBuilder();
        String line = gridLines.get(r);
        for (int i = 0; i < line.length(); i++) {
            if (i == c) {
                sb.append('#');
            } else {
                sb.append(line.charAt(i));
            }
        }

        gridLines.set(r, sb.toString());
        Grid grid = new Grid(gridLines, oldGrid.rows(), oldGrid.cols());

        Set<State> states = new HashSet<>();
        Coordinate current = start;
        int direction = 0;

        while (grid.inBounds(current)) {
            State currentState = new State(current, direction);
            if (states.contains(currentState)) {
                return true;
            }

            states.add(currentState);

            while (!canGoStraight(current, direction, grid)) {
                //turn to the right
                direction = (direction + 1) % 4;
            }
            current = move(current, direction);

        }


        return false;
    }

    private boolean canGoStraight(final Coordinate current, final int direction, final Grid grid) {
        Coordinate potential = move(current, direction);
        Character potentialTile = grid.get(potential);
        return potentialTile != WALL;
    }

    private Coordinate move(final Coordinate coordinate, final Integer direction) {
        return new Coordinate(coordinate.r() + DIRECTIONS.get(direction)[0],
                coordinate.c() + DIRECTIONS.get(direction)[1]);
    }
}
