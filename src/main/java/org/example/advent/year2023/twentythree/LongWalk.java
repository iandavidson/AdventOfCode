package org.example.advent.year2023.twentythree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class LongWalk {

    public static final Map<DIRECTION, Map<TILE, Boolean>> IS_VALID_MOVE_MAP = new HashMap<>();
    private static final List<DIRECTION> DIRECTIONS = new ArrayList<>();

    static {
        {
            DIRECTIONS.add(DIRECTION.LEFT);
            DIRECTIONS.add(DIRECTION.RIGHT);
            DIRECTIONS.add(DIRECTION.UP);
            DIRECTIONS.add(DIRECTION.DOWN);

            for (DIRECTION dir : DIRECTIONS) {
                IS_VALID_MOVE_MAP.put(dir, new HashMap<>());
                IS_VALID_MOVE_MAP.get(dir).put(TILE.BLOCK, Boolean.FALSE);
                IS_VALID_MOVE_MAP.get(dir).put(TILE.PATH, Boolean.TRUE);

                //part2: flipped these 2 below from FALSE -> TRUE
                IS_VALID_MOVE_MAP.get(dir).put(TILE.SLOPE_RIGHT, Boolean.TRUE);
                IS_VALID_MOVE_MAP.get(dir).put(TILE.SLOPE_DOWN, Boolean.TRUE);
            }

            //part2 commented these
//            IS_VALID_MOVE_MAP.get(DIRECTION.RIGHT).put(TILE.SLOPE_RIGHT, Boolean.TRUE);
//            IS_VALID_MOVE_MAP.get(DIRECTION.DOWN).put(TILE.SLOPE_DOWN, Boolean.TRUE);
        }
    }


    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day23/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day23/input.txt";

    public static void main(String[] args) {
        LongWalk longWalk = new LongWalk();
        System.out.println("part1: " + longWalk.part1());
    }

    public long part1() {
        //good golly, part 2 took super long but eventually found the right answer.

        char[][] grid = readFile();

        final Coordinate start = Coordinate.builder().row(0).col(1).build();
        final Coordinate end = Coordinate.builder().row(grid.length - 1).col(grid[0].length - 2).build();
        List<Coordinate> vertices = new ArrayList<>();
        vertices.add(start);
        vertices.add(end);
        findVerticies(vertices, grid);

        return findLongestPath(vertices, start, end, grid);
    }

    private char[][] readFile() {
        List<String> inputs = new ArrayList<>();
        try {
            ClassLoader classLoader = LongWalk.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                inputs.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        char[][] grid = new char[inputs.size()][inputs.get(0).length()];
        for (int i = 0; i < inputs.size(); i++) {
            grid[i] = inputs.get(i).toCharArray();
        }

        return grid;
    }

    private void findVerticies(List<Coordinate> verts, char[][] grid) {
        List<Character> slides = List.of('>', 'v');

        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                int count = 0;

                for (DIRECTION dir : DIRECTIONS) {
                    int[] shift = DIRECTION.move(dir);
                    char nextTile = grid[i + shift[0]][j + shift[1]];

                    if (slides.contains(nextTile)) {
                        count++;
                    }
                }

                if (count > 2) {
                    verts.add(Coordinate.builder().row(i).col(j).build());
                }
            }
        }
    }

    private Long findLongestPath(List<Coordinate> coordinates, Coordinate start, Coordinate end, char[][] grid) {
        Deque<WalkState> deque = new ArrayDeque<>();
        Set<WalkState> seen = new HashSet<>();
        WalkState init = WalkState.builder().history(new ArrayList<>()).steps(0L).current(start).build();
        deque.add(init);

        Long maxScore = -1L;
        WalkState walkState;

        while (!deque.isEmpty()) {
            walkState = deque.remove();

            if (seen.contains(walkState)) {
                continue;
            }

            if (walkState.getCurrent().equals(end)) {
                if (maxScore < walkState.getSteps()) {
                    System.out.println("Current longest steps from start -> end: " + walkState.getSteps());
                    maxScore = walkState.getSteps();
                }
                continue;
            }

            List<WalkState> optionalPaths = findJunctions(walkState, coordinates, grid);
            for (WalkState option : optionalPaths) {
                if (!seen.contains(option)) {
                    deque.add(option);
                }
            }

            seen.add(walkState);
        }
        return maxScore;
    }


    private List<WalkState> findJunctions(WalkState walkState, List<Coordinate> junctions, char[][] grid) {
        Queue<PathState> queue = new LinkedList<>();
        queue.add(PathState.builder().coordinate(walkState.getCurrent()).steps(walkState.getSteps()).build());
        Set<Coordinate> seen = new HashSet<>();

        //part 2 addition
        seen.addAll(walkState.getHistory());

        List<WalkState> results = new ArrayList<>();

        PathState current;
        while (!queue.isEmpty()) {
            current = queue.remove();

            if (seen.contains(current.getCoordinate())) {
                continue;
            }

            //if we are currently at a junction, that we didn't start at, add to return list.
            if (current.getCoordinate() != walkState.getCurrent() && junctions.contains(current.getCoordinate())) {
                List<Coordinate> history = new ArrayList<>(walkState.getHistory());

                history.add(walkState.getCurrent());
                results.add(
                        WalkState.builder()
                                .history(history)
                                .steps(current.getSteps())
                                .current(current.getCoordinate())
                                .build());

                continue;
            }

            List<PathState> neighbors = findValidNeighbors(current, grid);
            for (PathState pathState : neighbors) {
                if (!seen.contains(pathState.getCoordinate())) {
                    queue.add(pathState);
                }
            }

            seen.add(current.getCoordinate());
        }

        return results;
    }

    private List<PathState> findValidNeighbors(PathState current, char[][] grid) {
        List<PathState> paths = new ArrayList<>();

        for (DIRECTION dir : DIRECTIONS) {
            int[] shift = DIRECTION.move(dir);

            if (inbounds(current.getRow() + shift[0], current.getCol() + shift[1], grid.length, grid[0].length)) {
                char nextTile = grid[current.getRow() + shift[0]][current.getCol() + shift[1]];

                Boolean valid = IS_VALID_MOVE_MAP.get(dir).get(TILE.gridMap.get(nextTile));
                if (valid != null && valid) {
                    paths.add(
                            PathState
                                    .builder()
                                    .coordinate(
                                            Coordinate.builder().row(current.getRow() + shift[0]).col(current.getCol() + shift[1]).build())
                                    .steps(current.getSteps() + 1)
                                    .build());
                }

            }
        }

        return paths;
    }


    private boolean inbounds(int row, int col, int rowMax, int colMax) {
        return row > -1 && col > -1 && row < rowMax && col < colMax;
    }
}
