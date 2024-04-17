package org.example.advent.year2022.day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class HillClimbingAlgorithm {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day12/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day12/input.txt";
    private static final Character START = 'S';
    private static final Character END = 'E';
    private static final List<List<Integer>> NEIGHBOR_MAP = List.of(List.of(1, 0), List.of(-1, 0), List.of(0, 1), List.of(0, -1));

    public static void main(String[] args) {
        HillClimbingAlgorithm hillClimbingAlgorithm = new HillClimbingAlgorithm();
        System.out.println("part1: " + hillClimbingAlgorithm.part1());
        System.out.println("part2: " + hillClimbingAlgorithm.part2());
    }

    public Long part1() {
        List<List<Character>> grid = readFile();
        Coordinate start = null;
        Coordinate end = null;
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j).equals('S')) {
                    start = new Coordinate(i, j);
                } else if (grid.get(i).get(j).equals('E')) {
                    end = new Coordinate(i, j);
                }
            }
        }

        return dijkstra(grid, start, end);
    }

    public Long part2() {
        List<List<Character>> grid = readFile();
        List<Coordinate> starts = new ArrayList<>();
        Coordinate end = null;
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j).equals('S') || grid.get(i).get(j).equals('a')) {
                    starts.add(new Coordinate(i, j));
                } else if (grid.get(i).get(j).equals('E')) {
                    end = new Coordinate(i, j);
                }
            }
        }

        Long min = Long.MAX_VALUE;
        for (Coordinate start : starts) {
            min = Math.min(min, dijkstra(grid, start, end));
        }
        return min;
    }

    private Long dijkstra(List<List<Character>> grid, Coordinate start, Coordinate end) {
        WalkState current = new WalkState(start, 0L);
        Queue<WalkState> unsettled = new PriorityQueue<>();
        unsettled.add(current);

        Map<Coordinate, Long> coordinateStepMap = new HashMap<>();

        while (!unsettled.isEmpty()) {
            current = unsettled.remove();

            if (coordinateStepMap.containsKey(current.coordinate()) && coordinateStepMap.get(current.coordinate()) <= current.steps()) {
                continue;
            }

            if (current.row() == end.row() && current.col() == end.col()) {
                return current.steps();
            }

            getNeighbors(current, grid, unsettled, coordinateStepMap);
            coordinateStepMap.put(current.coordinate(), current.steps());
        }


        return Long.MAX_VALUE;
    }

    private void getNeighbors(WalkState current, List<List<Character>> grid, Queue<WalkState> unsettled, Map<Coordinate, Long> walkMap) {
        for (List<Integer> row_col_delta : NEIGHBOR_MAP) {
            int newRow = current.row() + row_col_delta.get(0);
            int newCol = current.col() + row_col_delta.get(1);

            if (newRow < 0 || newRow >= grid.size() || newCol < 0 || newCol >= grid.getFirst().size()) {
                continue;
            }

            char neighbor = treatValue(grid.get(newRow).get(newCol));
            char currentChar = treatValue(grid.get(current.row()).get(current.col()));
            int diff = neighbor - currentChar;


            if (diff < 2) {
                unsettled.add(
                        new WalkState(new Coordinate(newRow, newCol), current.steps() + 1)
                );
            }

        }
    }

    private Character treatValue(Character c) {
        if (c == 'S') {
            return 'a';
        } else if (c == 'E') {
            return 'z';
        }

        return c;
    }


    private List<List<Character>> readFile() {
        List<List<Character>> inputs = new ArrayList<>();

        ClassLoader cl = HillClimbingAlgorithm.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                List<Character> line = new ArrayList<>();
                for (Character ch : scanner.nextLine().toCharArray()) {
                    line.add(ch);
                }
                inputs.add(line);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return inputs;
    }

}
