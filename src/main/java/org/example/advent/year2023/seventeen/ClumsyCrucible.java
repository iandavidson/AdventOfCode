package org.example.advent.year2023.seventeen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class ClumsyCrucible {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day17/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day17/input.txt";


    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = ClumsyCrucible.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(SAMPLE_INPUT_PATH)).getFile());
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

    private List<List<Block>> processInput(List<String> inputs) {
        List<List<Block>> grid = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i++) {
            List<Block> gridRow = new ArrayList<>();
            for (int j = 0; j < inputs.get(i).length(); j++) {
                if (i == 0 && j == 0) {
                    gridRow.add(Block.builder()
                            .reduction(
                                    inputs.get(i).charAt(j) - '0')
                            .minimumHeatLoss(0)
                            .coordinate(Coordinate.builder().row(i).col(j).build())
                            .build());
                } else {
                    gridRow.add(Block.builder()
                            .reduction(
                                    inputs.get(i).charAt(j) - '0')
                            .coordinate(Coordinate.builder().row(i).col(j).build())
                            .build());

                }
            }
            grid.add(gridRow);
        }

        return grid;
    }

    private static List<Block> getNeighbors(Block block, List<List<Block>> grid) {
        List<Block> neighbors = new ArrayList<>();
        int currentRow = block.getRow();
        int currentCol = block.getCol();

        //above grid.get(row-1).get(col)
        if (currentRow - 1 >= 0) {
            Block temp = grid.get(currentRow - 1).get(currentCol);

            if (temp.getMinimumHeatLoss() > (block.getMinimumHeatLoss() + temp.getReduction())) {
                temp.setMinimumHeatLoss(block.getMinimumHeatLoss() + temp.getReduction());
                neighbors.add(temp);
            }

        }

        //below
        if (currentRow + 1 < grid.size()) {
            Block temp = grid.get(currentRow + 1).get(currentCol);

            if (temp.getMinimumHeatLoss() > (block.getMinimumHeatLoss() + temp.getReduction())) {
                temp.setMinimumHeatLoss(block.getMinimumHeatLoss() + temp.getReduction());
                neighbors.add(temp);
            }

        }

        //left
        if (currentCol - 1 >= 0) {
            Block temp = grid.get(currentRow).get(currentCol - 1);

            if (temp.getMinimumHeatLoss() > (block.getMinimumHeatLoss() + temp.getReduction())) {
                temp.setMinimumHeatLoss(block.getMinimumHeatLoss() + temp.getReduction());
                neighbors.add(temp);
            }

        }

        //right
        if (currentCol + 1 < grid.get(0).size()) {
            Block temp = grid.get(currentRow).get(currentCol + 1);

            if (temp.getMinimumHeatLoss() > (block.getMinimumHeatLoss() + temp.getReduction())) {
                temp.setMinimumHeatLoss(block.getMinimumHeatLoss() + temp.getReduction());
                neighbors.add(temp);
            }
        }

        return neighbors;
    }


    public int part1() {
        List<List<Block>> grid = processInput(readFile());

        Queue<Block> unsettled = new PriorityQueue<>();
        Queue<Block> settled = new PriorityQueue<>();

        unsettled.add(grid.get(0).get(0));

        while (!unsettled.isEmpty()) {
            //grab random
            Block block = unsettled.remove();
            System.out.println("currentBlock: " + block.getRow() + " : " +  block.getCol());
            //find neighbors and update in grid with respective distance, add to queue
            List<Block> neighbors = getNeighbors(block, grid);

//            unsettled.addAll(neighbors);
            for(Block b : neighbors){
                if(!unsettled.contains(b)){
                    unsettled.add(b);
                }
//
            }

            settled.add(block);
        }

        return grid.get(grid.size()-1).get(grid.get(0).size()-1).getMinimumHeatLoss();

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
        private final Coordinate coordinate;
        private final int reduction;

        @Builder.Default
        private int minimumHeatLoss = Integer.MAX_VALUE;
        //sequence until that point?
        //List<Block> sequenceFromStart

        public int getRow() {
            return coordinate.getRow();
        }

        public int getCol() {
            return coordinate.getCol();
        }

        @Override
        public int compareTo(Block o) {
            return o.getMinimumHeatLoss() - this.minimumHeatLoss;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Coordinate {
        private final int row;
        private final int col;
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
