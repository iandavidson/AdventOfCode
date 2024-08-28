package dev.davidson.ian.advent.year2016.day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MazeOfCubicles {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day13/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day13/input.txt";

    private static final Coordinate START = new Coordinate(1, 1);
    //    private static final Coordinate END = new Coordinate(4, 7);
    private static final Coordinate END = new Coordinate(39, 31);

    private static final List<int[]> NEIGHBOR_SHIFTS = List.of(
            new int[]{1, 0},
            new int[]{0, 1},
            new int[]{-1, 0},
            new int[]{0, -1}
    );

    public static void main(String[] args) {
        MazeOfCubicles mazeOfCubicles = new MazeOfCubicles();
        log.info("Part1: {}", mazeOfCubicles.part1());
        log.info("Part2: {}", mazeOfCubicles.part2());
    }

    public int part1() {
        int designerCode = readFile();

        Set<Coordinate> visited = new HashSet<>();
        Map<Coordinate, Boolean> isTileMap = new HashMap<>();
        isTileMap.put(START, true);

        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(START);
        int stepsTaken = 0;

        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Coordinate current = queue.remove();

                if (current.equals(END)) {
                    return stepsTaken;
                }

                for (Coordinate newNeighbor : findValidNeighbors(current, isTileMap, designerCode)) {
                    if (!visited.contains(newNeighbor)) {
                        queue.add(newNeighbor);
                    }
                }

                visited.add(current);
            }

            stepsTaken++;
        }

        return 0;
    }

    public Integer part2() {
        int designerCode = readFile();

        Set<Coordinate> visited = new HashSet<>();
        Map<Coordinate, Boolean> isTileMap = new HashMap<>();
        isTileMap.put(START, true);

        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(START);
        int stepsTaken = 0;

        while (stepsTaken < 50) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Coordinate current = queue.remove();

                for (Coordinate newNeighbor : findValidNeighbors(current, isTileMap, designerCode)) {
                    if (!visited.contains(newNeighbor)) {
                        visited.add(newNeighbor);
                        queue.add(newNeighbor);
                    }
                }
            }

            stepsTaken++;
        }

        return visited.size();
    }


    private List<Coordinate> findValidNeighbors(final Coordinate current, final Map<Coordinate, Boolean> isTileMap,
                                                final Integer designerCode) {
        List<Coordinate> coordinates = new ArrayList<>();

        for (int[] shift : NEIGHBOR_SHIFTS) {

            Coordinate next = new Coordinate(current.row() + shift[0], current.col() + shift[1]);

            if (next.row() < 0 || next.col() < 0) {
                continue;
            }

            if (next.isPath(designerCode)) {
                coordinates.add(next);
            }
        }

        return coordinates;
    }

    private int readFile() {
        ClassLoader cl = MazeOfCubicles.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return Integer.parseInt(scanner.nextLine());
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path.");
        }
    }
}
