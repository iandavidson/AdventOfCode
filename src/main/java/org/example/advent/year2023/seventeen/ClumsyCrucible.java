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

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day17/input-sample.txt";
    private static final String SAMPLE_INPUT2_PATH = "adventOfCode/day17/input-sample2.txt";
    private static final String MINI_SAMPLE_INPUT_PATH = "adventOfCode/day17/input-sample-mini.txt";
    private static final String INPUT_PATH = "adventOfCode/day17/input.txt";


    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public static Direction turnLeft(Direction direction){
            Direction d = null;
            switch(direction){
                case UP -> d = LEFT;
                case DOWN -> d = RIGHT;
                case LEFT -> d = DOWN;
                case RIGHT -> d = UP;
            }
            return d;
        }

        public static Direction turnRight(Direction direction){
            Direction d = null;
            switch(direction){
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
            File file = new File(Objects.requireNonNull(classLoader.getResource(SAMPLE_INPUT2_PATH)).getFile());
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

    private static Block optionalUpdateNeighbor(Block current, Block potentialNeighbor){
        //Instead of immediately veto'ing based on potential weight, need to look forward in
        // potential steps to determine if valid neighbor. (may not be less than current value)
        if (potentialNeighbor.getMinimumHeatLoss() > (current.getMinimumHeatLoss() + potentialNeighbor.getReduction())) {
            potentialNeighbor.setMinimumHeatLoss(current.getMinimumHeatLoss() + potentialNeighbor.getReduction());
//            List<Block> currentBlockPrior = new ArrayList<>(current.getSequenceFromStart());
//            currentBlockPrior.add(current);
//            potentialNeighbor.setSequenceFromStart(currentBlockPrior);
            return potentialNeighbor;
        } else {
            return null;
        }
    }

    private static boolean isNeighborValid(Direction direction, Block current){
//        int size = current.getSequenceFromStart().size();
//        if(size > 2){
//            switch (direction) {
//                case LEFT -> {
//                    return current.getSequenceFromStart().get(size-3).getCol() != current.getCol()+3;
//                }
//                case RIGHT -> {
//                    return current.getSequenceFromStart().get(size-3).getCol() != current.getCol()-3;
//                }
//                case DOWN -> {
//                    return current.getSequenceFromStart().get(size-3).getRow() != current.getRow()-3;
//                }
//                case UP -> {
//                    return current.getSequenceFromStart().get(size-3).getRow() != current.getRow()+3;
//                }
//                default -> System.out.println("idk what happened?? 🥸");
//                }
//        }

        return true;
    }



//    private static List<Block> getNeighborsReal(Block block, List<List<Integer>> grid) {
//
//    }

    private static List<Block> getNeighbors(Block block, List<List<Block>> grid) {
        List<Block> neighbors = new ArrayList<>();
        int currentRow = block.getRow();
        int currentCol = block.getCol();

        //above grid.get(row-1).get(col)
        if (currentRow - 1 >= 0 && isNeighborValid(Direction.UP, block)) {
            Block temp = optionalUpdateNeighbor(block, grid.get(currentRow - 1).get(currentCol));

            if(temp != null){
                neighbors.add(temp);
            }
        }

        //below
        if (currentRow + 1 < grid.size() && isNeighborValid(Direction.DOWN, block)) {
            Block temp = optionalUpdateNeighbor(block, grid.get(currentRow + 1).get(currentCol));

            if(temp != null){
                neighbors.add(temp);
            }
        }

        //left
        if (currentCol - 1 >= 0 && isNeighborValid(Direction.LEFT, block)) {
            Block temp = optionalUpdateNeighbor(block, grid.get(currentRow).get(currentCol - 1));

            if(temp != null){
                neighbors.add(temp);
            }
        }

        //right
        if (currentCol + 1 < grid.get(0).size() && isNeighborValid(Direction.RIGHT, block)) {
            Block temp = optionalUpdateNeighbor(block, grid.get(currentRow).get(currentCol + 1));

            if(temp != null){
                neighbors.add(temp);
            }
        }

        return neighbors;
    }


    public int part1() {
        List<List<Integer>> grid = processInput(readFile());

        Queue<Block> unsettled = new PriorityQueue<>();

        //get rid of this
        Set<Block> settled = new HashSet<>();

        //create Visited Set<Coordinate-Ext>

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
            //grab random
            Block block = unsettled.remove();
//            System.out.println("currentBlock: " + block.getRow() + " : " +  block.getCol());


            if(settled.contains(block)){
                continue;
            }

            //CHECK IF BLOCK IS BOTTOM RIGHT CELL, IF SO RETURN MINHEATLOSS
            if(block.getRow() == grid.size()-1 && block.getCol() == grid.get(0).size()){
                return block.getMinimumHeatLoss();
            }



            //find neighbors and update in grid with respective distance, add to queue
//            List<Block> neighbors = getNeighbors(block, grid);
//
//            for(Block b : neighbors){
//                //should i also check if exists in settled, if so DON'T add??????
//                if(!settled.contains(b)){
//                    unsettled.add(b);
//                }
//            }

            settled.add(block);
        }


//        for(Block prior: grid.get(grid.size()-1).get(grid.get(0).size()-1).getSequenceFromStart()){
//            System.out.println(prior.getRow() + ", " + prior.getCol() + "; heat added: "+ prior.getReduction());
//        }
        return -1;
    }


    public static void main(String[] args) {
        ClumsyCrucible clumsyCrucible = new ClumsyCrucible();
        int heatLost = clumsyCrucible.part1();
        System.out.println("part1: " + heatLost);
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

        @Override
        public int hashCode() {
            return Objects.hash(location);
        }

        @Builder.Default
        private int minimumHeatLoss = Integer.MAX_VALUE;

//        @Builder.Default
//        private List<Block> sequenceFromStart = new ArrayList<>();

        public int getRow() {
            return location.getRow();
        }

        public int getCol() {
            return location.getCol();
        }

        @Override
        public int compareTo(Block o) {
            int difference = this.minimumHeatLoss - o.getMinimumHeatLoss();
            if(difference != 0){
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
