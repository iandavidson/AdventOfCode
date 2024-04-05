package org.example.advent.year2023.seventeen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class ClumsyCrucible {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day17/input-sample.txt";
    private static final String SAMPLE_INPUT2_PATH = "adventOfCode/2023/day17/input-sample2.txt";
    private static final String MINI_SAMPLE_INPUT_PATH = "adventOfCode/2023/day17/input-sample-mini.txt";
    private static final String INPUT_PATH = "adventOfCode/2023/day17/input.txt";


    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public static Direction turnLeft(Direction direction) {
            Direction d = null;
            switch (direction) {
                case UP -> d = LEFT;
                case DOWN -> d = RIGHT;
                case LEFT -> d = DOWN;
                case RIGHT -> d = UP;
            }
            return d;
        }

        public static Direction turnRight(Direction direction) {
            Direction d = null;
            switch (direction) {
                case UP -> d = RIGHT;
                case DOWN -> d = LEFT;
                case LEFT -> d = UP;
                case RIGHT -> d = DOWN;
            }
            return d;
        }
    }

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = ClumsyCrucible.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    private List<List<Integer>> processInput(List<String> inputs) {
        List<List<Integer>> grid = new ArrayList<>();

        for (String input : inputs) {
            List<Integer> gridRow = new ArrayList<>();
            for (int j = 0; j < input.length(); j++) {
                gridRow.add(input.charAt(j) - '0');
            }
            grid.add(gridRow);
        }

        return grid;
    }

    private static void getNeighborsHelper(Block parent, Direction direction, List<List<Integer>> grid, Queue<Block> unsettled) {
        int r = parent.getRow();
        int c = parent.getCol();
        switch (direction) {
            case UP -> r--;
            case DOWN -> r++;
            case LEFT -> c--;
            case RIGHT -> c++;
        }

        if (r >= 0 && r < grid.size() && c >= 0 && c < grid.get(0).size()) {
            int steps = parent.getLocation().getDirection().equals(direction) ? parent.getLocation().getSteps() + 1 : 0;
            unsettled.add(Block.builder()
                    .minimumHeatLoss(parent.getMinimumHeatLoss() + grid.get(r).get(c))
                    .reduction(grid.get(r).get(c))
                    .location(
                            Location.builder()
                                    .row(r)
                                    .col(c)
                                    .direction(direction)
                                    .steps(steps)
                                    .build())
                    .build());
        }
    }

    private static void getNeighbors(Block block, List<List<Integer>> grid, Queue<Block> unsettled) {
        if (block.getLocation().getSteps() < 2) {
            getNeighborsHelper(block, block.getLocation().getDirection(), grid, unsettled);
        }

        getNeighborsHelper(block, Direction.turnLeft(block.location.getDirection()), grid, unsettled);
        getNeighborsHelper(block, Direction.turnRight(block.location.getDirection()), grid, unsettled);
    }

    private static void getNeighborsPart2(Block block, List<List<Integer>> grid, Queue<Block> unsettled) {
        if (block.getLocation().getSteps() < 9) {
            getNeighborsHelper(block, block.getLocation().getDirection(), grid, unsettled);
        }

        if (block.getLocation().getSteps() > 2) {
            getNeighborsHelper(block, Direction.turnLeft(block.location.getDirection()), grid, unsettled);
            getNeighborsHelper(block, Direction.turnRight(block.location.getDirection()), grid, unsettled);
        }
    }


    public int dijkstra(boolean part1) {
        List<List<Integer>> grid = processInput(readFile());

        Queue<Block> unsettled = new PriorityQueue<>();
        Set<Block> settled = new HashSet<>();

        //Below start
        unsettled.add(Block.builder()
                .location(
                        Location.builder()
                                .row(1)
                                .col(0)
                                .steps(1)
                                .direction(Direction.DOWN)
                                .build())
                .minimumHeatLoss(grid.get(1).get(0))
                .reduction(grid.get(1).get(0))
                .build());

        //Right of start
        unsettled.add(Block.builder()
                .location(
                        Location.builder()
                                .row(0)
                                .col(1)
                                .steps(1)
                                .direction(Direction.RIGHT)
                                .build())
                .minimumHeatLoss(grid.get(0).get(1))
                .reduction(grid.get(0).get(1))
                .build());

        while (!unsettled.isEmpty()) {
            //grab minHeat from heap
            Block block = unsettled.remove();

            if (settled.contains(block)) {
                continue;
            }

            if (block.getRow() == grid.size() - 1 && block.getCol() == grid.get(0).size() - 1 && (part1 || block.getLocation().getSteps() > 2)) {
                return block.getMinimumHeatLoss();
            }

            //find neighbors and update in grid with respective distance, add to queue
            if (part1) {
                getNeighbors(block, grid, unsettled);
            } else {
                getNeighborsPart2(block, grid, unsettled);
            }

            settled.add(block);
        }

        return -1;
    }


    public static void main(String[] args) {
        ClumsyCrucible clumsyCrucible = new ClumsyCrucible();
        int heatLost = clumsyCrucible.dijkstra(true);
        System.out.println("part1: " + heatLost);

        heatLost = clumsyCrucible.dijkstra(false);
        System.out.println("part2: " + heatLost);
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Block implements Comparable<Block> {

        //Coordinate should encapsulate direction and how many steps that way
        private final Location location;
        private final int reduction;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Block block = (Block) o;
            return Objects.equals(location, block.location);
        }

        @Builder.Default
        private int minimumHeatLoss = Integer.MAX_VALUE;

        @Override
        public int hashCode() {
            return Objects.hash(location);
        }

        public int getRow() {
            return location.getRow();
        }

        public int getCol() {
            return location.getCol();
        }

        @Override
        public int compareTo(Block o) {
            int difference = this.minimumHeatLoss - o.getMinimumHeatLoss();
            if (difference != 0) {
                return difference;
            }

            return this.reduction - o.getReduction();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Location {
        private final int row;
        private final int col;
        private int steps;
        private Direction direction;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return row == location.row && col == location.col && steps == location.steps && direction == location.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col, steps, direction);
        }
    }


}


/*
        A(5) ----  B(10) ----- C(who cares)
            \                 /
              D(9) -----------

Directed graph with cycles
A -> B (5)
B -> A (10)

Process A:
B.setHeat(5)
D.setHeat(5)
-> unsettled.add(B-5, D-5)
-> settled.add(A)

Process B:
C.setHeat(15)
-> unsettled.add(C-15)
-> settled.add(B)

Process D:
C.setHeat(14)
-> unsettled.add(C-14) //C already member of unsettled
-> settled.add(D)




 */
