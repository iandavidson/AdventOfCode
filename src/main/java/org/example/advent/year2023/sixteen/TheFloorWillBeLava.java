package org.example.advent.year2023.sixteen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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

public class TheFloorWillBeLava {


    private static final Map<FloorType, Map<Direction, List<Direction>>> MAP = new HashMap<>();


    static {
        {
            // .
            MAP.put(FloorType.EMPTY, new HashMap<>());
            MAP.get(FloorType.EMPTY).put(Direction.R, List.of(Direction.R));
            MAP.get(FloorType.EMPTY).put(Direction.L, List.of(Direction.L));
            MAP.get(FloorType.EMPTY).put(Direction.U, List.of(Direction.U));
            MAP.get(FloorType.EMPTY).put(Direction.D, List.of(Direction.D));

            // /
            MAP.put(FloorType.FORWARD_SLASH, new HashMap<>());
            MAP.get(FloorType.FORWARD_SLASH).put(Direction.R, List.of(Direction.U));
            MAP.get(FloorType.FORWARD_SLASH).put(Direction.L, List.of(Direction.D));
            MAP.get(FloorType.FORWARD_SLASH).put(Direction.U, List.of(Direction.R));
            MAP.get(FloorType.FORWARD_SLASH).put(Direction.D, List.of(Direction.L));

            // \
            MAP.put(FloorType.BACK_SLASH, new HashMap<>());
            MAP.get(FloorType.BACK_SLASH).put(Direction.R, List.of(Direction.D));
            MAP.get(FloorType.BACK_SLASH).put(Direction.L, List.of(Direction.U));
            MAP.get(FloorType.BACK_SLASH).put(Direction.U, List.of(Direction.L));
            MAP.get(FloorType.BACK_SLASH).put(Direction.D, List.of(Direction.R));

            // |
            MAP.put(FloorType.VERT_SPLIT, new HashMap<>());
            MAP.get(FloorType.VERT_SPLIT).put(Direction.U, List.of(Direction.U));
            MAP.get(FloorType.VERT_SPLIT).put(Direction.D, List.of(Direction.D));
            MAP.get(FloorType.VERT_SPLIT).put(Direction.L, List.of(Direction.U, Direction.D));
            MAP.get(FloorType.VERT_SPLIT).put(Direction.R, List.of(Direction.U, Direction.D));

            // -
            MAP.put(FloorType.HORZ_SPLIT, new HashMap<>());
            MAP.get(FloorType.HORZ_SPLIT).put(Direction.L, List.of(Direction.L));
            MAP.get(FloorType.HORZ_SPLIT).put(Direction.R, List.of(Direction.R));
            MAP.get(FloorType.HORZ_SPLIT).put(Direction.D, List.of(Direction.L, Direction.R));
            MAP.get(FloorType.HORZ_SPLIT).put(Direction.U, List.of(Direction.L, Direction.R));
        }
    }
    //nextDirection(grid, lightBeam)
    //->

    public enum Direction {
        U, D, L, R
    }

    public enum FloorType {
        EMPTY('.'),
        FORWARD_SLASH('/'),
        BACK_SLASH('\\'),
        VERT_SPLIT('|'),
        HORZ_SPLIT('-');

        public final Character floor;

        private FloorType(Character floor) {
            this.floor = floor;
        }
    }

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day16/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day16/input.txt";

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = TheFloorWillBeLava.class.getClassLoader();
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

    private static List<List<Character>> convertInput(List<String> inputLines){
        List<List<Character>> grid = new ArrayList<>();

        for(String line : inputLines){
            List<Character> newRow = new ArrayList<>();

            for(Character ch : line.toCharArray()){
                newRow.add(ch);
            }
            grid.add(newRow);
        }

        return grid;
    }

    private static boolean [][] initDirtyGrid(List<List<Character>> grid){
        boolean [][] dirtyGrid = new boolean[grid.size()][];

        for(int i =0; i < grid.size(); i++){
            dirtyGrid[i] = new boolean[grid.get(0).size()];
        }

        return dirtyGrid;
    }


    public long part1() {
        List<String> inputs = readFile();
        List<List<Character>> grid = convertInput(inputs);
        boolean [][] dirtyGrid = initDirtyGrid(grid);
        //need to also track (so we don't follow the same path):
        // which splits have already happened
        // which diagonals have been followed (from the same starting direction)


        Queue<LightBeam> lightBeams = new LinkedList<>();
        lightBeams.offer(LightBeam.builder().currentColumn(0).currentRow(0).direction(Direction.R).build());

        while (!lightBeams.isEmpty()) {
            LightBeam lightBeam = lightBeams.poll();

            //walk until we hit a split or hit boundry
            while(lightBeam.getCurrentColumn() >= 0 && lightBeam.getCurrentColumn() < grid.get(0).size() && lightBeam.getCurrentRow() >= 0 && lightBeam.getCurrentRow() < grid.size()){
                //check if


                //last thing in iteration, walk 1 square in direction given current tile
            }


        }


        return 0L;
    }

    public static void main(String[] args) {
        TheFloorWillBeLava theFloorWillBeLava = new TheFloorWillBeLava();
        long result1 = theFloorWillBeLava.part1();
        System.out.println("part1: " + result1);
    }

    @Builder
    @Data
    @AllArgsConstructor
    public static class LightBeam {
        private int currentRow;
        private int currentColumn;
        private Direction direction;
    }

}
