package org.example.advent.year2023.day21;

import lombok.extern.java.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

@Log
public class StepCounter {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day21/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2023/day21/input.txt";
    private int startRow = -1;
    private int startCol = -1;

    public static void main(String[] args) {
        StepCounter stepCounter = new StepCounter();
        log.info("Part1: " + stepCounter.part1(64));
        log.info("Part2: " + stepCounter.part2(26501365));
    }

    public Long part1(int steps) {
        List<List<TILE>> grid = readFile();
        Map<Coordinate, Integer> distances = greedyBFS(grid, Coordinate.builder().row(startRow).col(startCol).build());
        return distances.entrySet().stream().filter(entry -> entry.getValue() <= steps && entry.getValue() % 2 == 0).count();
    }

    public long part2(int steps) {
        List<List<TILE>> grid = readFile();

        //Credit to https://github.com/ash42/adventofcode/blob/main/adventofcode2023/src/nl/michielgraat/adventofcode2023/day21/Day21.java
        //for providing insight on how to calculate potential final coordiantes;
        Map<Coordinate, Integer> walkDistances = greedyBFS(grid, Coordinate.builder().row(startRow).col(startCol).build());

        long evenFoundDistances = walkDistances.entrySet().stream().filter(entry -> entry.getValue() % 2 == 0).count();
        long oddFoundDistances = walkDistances.entrySet().stream().filter(entry -> entry.getValue() % 2 == 1).count();

        long evenCornerFoundDistances = walkDistances.entrySet().stream().filter(entry -> entry.getValue() > 65 && entry.getValue() % 2 == 0).count();
        long oddCornerFoundDistances = walkDistances.entrySet().stream().filter(entry -> entry.getValue() > 65 && entry.getValue() % 2 == 1).count();

        long horizontalSquares = (steps - 65) / 131;

        long totalOddPlots = (horizontalSquares + 1) * (horizontalSquares + 1) * oddFoundDistances;
        long totalEvenPlots = (horizontalSquares) * (horizontalSquares) * evenFoundDistances;

        long totalOddCorners = (horizontalSquares + 1) * oddCornerFoundDistances;
        long totalEvenCorners = horizontalSquares * evenCornerFoundDistances;

        return totalOddPlots + totalEvenPlots - totalOddCorners + totalEvenCorners;
    }

    private List<List<TILE>> readFile() {
        List<List<TILE>> grid = new ArrayList<>();

        try {
            ClassLoader classLoader = StepCounter.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            int i = 0;
            while (myReader.hasNextLine()) {
                List<TILE> row = new ArrayList<>();
                int j = 0;
                for (Character ch : myReader.nextLine().toCharArray()) {
                    if (ch == 'S') {
                        startRow = i;
                        startCol = j;
                    }
                    row.add(TILE.gridMap.get(ch));
                    j++;
                }
                grid.add(row);
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return grid;
    }

    private static boolean isInBounds(List<List<TILE>> grid, int row, int col) {
        if(row > grid.size() - 1 || row < 0 || col > grid.get(0).size() - 1 || col < 0 || grid.get(row).get(col) == TILE.ROCK){
            return false;
        } else{
            return true;
        }
    }

    private static List<Coordinate> getNeighbors(List<List<TILE>> grid, Coordinate current) {
        List<Coordinate> neighbors = new ArrayList<>();

        //up
        if (isInBounds(grid, current.getRow() - 1, current.getCol())) {
            neighbors.add(Coordinate.builder()
                    .row(current.getRow() - 1)
                    .col(current.getCol())
                    .build());
        }

        //down
        if (isInBounds(grid, current.getRow() + 1, current.getCol())) {
            neighbors.add(Coordinate.builder()
                    .row(current.getRow() + 1)
                    .col(current.getCol())
                    .build());
        }

        //right
        if (isInBounds(grid, current.getRow(), current.getCol() + 1)) {
            neighbors.add(Coordinate.builder()
                    .row(current.getRow())
                    .col(current.getCol() + 1)
                    .build());
        }

        //left
        if (isInBounds(grid, current.getRow(), current.getCol() - 1)) {
            neighbors.add(Coordinate.builder()
                    .row(current.getRow())
                    .col(current.getCol() - 1)
                    .build());

        }

        return neighbors;
    }

    private static Map<Coordinate, Integer> greedyBFS(List<List<TILE>> grid, Coordinate start) {
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);
        Map<Coordinate, Integer> distanceMap = new HashMap<>();
        distanceMap.put(start, 0);

        while (!queue.isEmpty()) {
            Coordinate current = queue.remove();
            int currentDistance = distanceMap.get(current);
            List<Coordinate> neighbors = getNeighbors(grid, current);

            for (Coordinate coord : neighbors) {
                int nextDistance = currentDistance + 1;

                if (distanceMap.getOrDefault(coord, Integer.MAX_VALUE) > nextDistance) {
                    distanceMap.put(coord, nextDistance);
                    queue.add(coord);
                }
            }
        }

        return distanceMap;
    }
}
