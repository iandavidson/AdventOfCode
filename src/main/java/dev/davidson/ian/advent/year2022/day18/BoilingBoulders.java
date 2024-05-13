package dev.davidson.ian.advent.year2022.day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class BoilingBoulders {

    private static final int MAX_COORDINATE_VALUE = 23;
    private static final List<Coordinate> DIRECTION_DELTAS = List.of(
            new Coordinate(1, 0, 0),
            new Coordinate(-1, 0, 0),
            new Coordinate(0, 1, 0),
            new Coordinate(0, -1, 0),
            new Coordinate(0, 0, 1),
            new Coordinate(0, 0, -1)
    );

    private static final String SAMPLE_PATH = "adventOfCode/2022/day18/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day18/input.txt";

    public static void main(String[] args) {
        BoilingBoulders boilingBoulders = new BoilingBoulders();
        System.out.println("Part1: " + boilingBoulders.part1());
        System.out.println("Part2: " + boilingBoulders.part2());
    }

    public long part1() {
        List<Coordinate> boulders = readFile();
        int[][][] grid = new int[MAX_COORDINATE_VALUE][MAX_COORDINATE_VALUE][MAX_COORDINATE_VALUE];

        for (Coordinate boulder : boulders) {
            grid[boulder.x()][boulder.y()][boulder.z()] = 1;
        }

        return findAllSurfaceArea(boulders, grid);
    }

    public long part2() {
        List<Coordinate> boulders = readFile();
        int[][][] grid = new int[MAX_COORDINATE_VALUE][MAX_COORDINATE_VALUE][MAX_COORDINATE_VALUE];

        for (Coordinate boulder : boulders) {
            grid[boulder.x()][boulder.y()][boulder.z()] = 1;
        }

        return findOnlyExposedSurface(boulders, grid);
    }

    private List<Coordinate> readFile() {
        List<Coordinate> boulders = new ArrayList<>();

        ClassLoader cl = BoilingBoulders.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                boulders.add(new Coordinate(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return boulders;
    }

    private long findAllSurfaceArea(final List<Coordinate> boulders, final int[][][] grid) {
        long count = 0L;

        for (Coordinate boulder : boulders) {
            for (Coordinate xyzDelta : DIRECTION_DELTAS) {
                if (isExposed(boulder, grid, xyzDelta, 0)) {
                    count++;
                }
            }
        }

        return count;
    }

    private long findOnlyExposedSurface(final List<Coordinate> boulders, final int[][][] grid) {
        //flood fill:
        Set<Coordinate> visited = new HashSet<>();
        Queue<Coordinate> queue = new LinkedList<>();
        Coordinate current = new Coordinate(0, 0, 0);
        queue.add(current);

        while (!queue.isEmpty()) {
            current = queue.remove();

            if (visited.contains(current)) {
                continue;
            }

            List<Coordinate> validNeighbors = findValidNeighbors(current, grid);

            for (Coordinate coordinate : validNeighbors) {
                if (!visited.contains(coordinate)) {
                    queue.add(coordinate);
                }
            }

            visited.add(current);
        }

        long count = 0L;

        //visited should contain the full set of outside air coordinates
        for (Coordinate airParticle : visited) {
            for (Coordinate xyzDelta : DIRECTION_DELTAS) {
                if (isExposed(airParticle, grid, xyzDelta, 1)) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean isExposed(final Coordinate Coordinate, final int[][][] grid, final Coordinate xyzDelta, final int expectedNeighbor) {
        int newX = Coordinate.x() + xyzDelta.x();
        int newY = Coordinate.y() + xyzDelta.y();
        int newZ = Coordinate.z() + xyzDelta.z();

        //boundary check no coordinates
        if (newX == -1 || newY == -1 || newZ == -1 || newX == MAX_COORDINATE_VALUE || newY == MAX_COORDINATE_VALUE || newZ == MAX_COORDINATE_VALUE) {
            return false;
        }

        return grid[newX][newY][newZ] == expectedNeighbor;
    }

    private List<Coordinate> findValidNeighbors(final Coordinate current, final int[][][] grid) {
        // valid neighbors should only be air, non rocks
        List<Coordinate> neighbors = new ArrayList<>();

        for (Coordinate xyzDelta : DIRECTION_DELTAS) {
            if (isExposed(current, grid, xyzDelta, 0)) {
                neighbors.add(new Coordinate(current.x() + xyzDelta.x(), current.y() + xyzDelta.y(), current.z() + xyzDelta.z()));
            }
        }

        return neighbors;
    }
}
