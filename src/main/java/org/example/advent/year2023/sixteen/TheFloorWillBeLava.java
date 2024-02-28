package org.example.advent.year2023.sixteen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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

public class TheFloorWillBeLava {


    private static final Map<Character, Map<Direction, List<Direction>>> MAP = new HashMap<>();


    static {
        {
            // .
            MAP.put('.', new HashMap<>());
            MAP.get('.').put(Direction.R, List.of(Direction.R));
            MAP.get('.').put(Direction.L, List.of(Direction.L));
            MAP.get('.').put(Direction.U, List.of(Direction.U));
            MAP.get('.').put(Direction.D, List.of(Direction.D));

            // /
            MAP.put('/', new HashMap<>());
            MAP.get('/').put(Direction.R, List.of(Direction.U));
            MAP.get('/').put(Direction.L, List.of(Direction.D));
            MAP.get('/').put(Direction.U, List.of(Direction.R));
            MAP.get('/').put(Direction.D, List.of(Direction.L));

            // \
            MAP.put('\\', new HashMap<>());
            MAP.get('\\').put(Direction.R, List.of(Direction.D));
            MAP.get('\\').put(Direction.L, List.of(Direction.U));
            MAP.get('\\').put(Direction.U, List.of(Direction.L));
            MAP.get('\\').put(Direction.D, List.of(Direction.R));

            // |
            MAP.put('|', new HashMap<>());
            MAP.get('|').put(Direction.U, List.of(Direction.U));
            MAP.get('|').put(Direction.D, List.of(Direction.D));
            MAP.get('|').put(Direction.L, List.of(Direction.U, Direction.D));
            MAP.get('|').put(Direction.R, List.of(Direction.U, Direction.D));

            // -
            MAP.put('-', new HashMap<>());
            MAP.get('-').put(Direction.L, List.of(Direction.L));
            MAP.get('-').put(Direction.R, List.of(Direction.R));
            MAP.get('-').put(Direction.D, List.of(Direction.L, Direction.R));
            MAP.get('-').put(Direction.U, List.of(Direction.L, Direction.R));
        }
    }

    public enum Direction {
        U, D, L, R
    }

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day16/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day16/input.txt";

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = TheFloorWillBeLava.class.getClassLoader();
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

    private static List<List<Character>> convertInput(List<String> inputLines) {
        List<List<Character>> grid = new ArrayList<>();

        for (String line : inputLines) {
            List<Character> newRow = new ArrayList<>();

            for (Character ch : line.toCharArray()) {
                newRow.add(ch);
            }
            grid.add(newRow);
        }

        return grid;
    }

    private static String encodeCoordinate(Coordinate coordinate) {
        return coordinate.getRow() + ":" + coordinate.getCol();
    }

    private static List<LightBeam> walk(LightBeam lb, List<List<Character>> grid) {
        List<LightBeam> lightBeams = new ArrayList<>();
        Character tile = grid.get(lb.getRow()).get(lb.getCol());
        List<Direction> directions = MAP.get(tile).get(lb.getDirection());

        for (Direction direction : directions) {
            switch (direction) {
                case L -> lightBeams.add(LightBeam.builder()
                        .coordinate(Coordinate.builder().row(lb.getRow()).col(lb.getCol() - 1).build()).direction(direction).build());
                case R -> lightBeams.add(LightBeam.builder()
                        .coordinate(Coordinate.builder().row(lb.getRow()).col(lb.getCol() + 1).build()).direction(direction).build());
                case D -> lightBeams.add(LightBeam.builder()
                        .coordinate(Coordinate.builder().row(lb.getRow() + 1).col(lb.getCol()).build()).direction(direction).build());
                case U -> lightBeams.add(LightBeam.builder()
                        .coordinate(Coordinate.builder().row(lb.getRow() - 1).col(lb.getCol()).build()).direction(direction).build());
                default -> System.out.println("idk what happened?? 🥸");
            }
        }

        return lightBeams;
    }

    public long part1() {
        List<String> inputs = readFile();
        List<List<Character>> grid = convertInput(inputs);

        Queue<LightBeam> lightBeamQueue = new LinkedList<>();
        lightBeamQueue.offer(LightBeam.builder().coordinate(Coordinate.builder().row(0).col(0).build()).direction(Direction.R).build());

        Set<String> count = new HashSet<>();
        Set<LightBeam> visited = new HashSet<>();


        while (!lightBeamQueue.isEmpty()) {
            LightBeam lightBeam = lightBeamQueue.poll();

            if (lightBeam.getCol() >= 0 && lightBeam.getCol() < grid.get(0).size() && lightBeam.getRow() >= 0 && lightBeam.getRow() < grid.size() && !visited.contains(lightBeam)) {
                count.add(encodeCoordinate(lightBeam.getCoordinate()));
                visited.add(lightBeam);

                List<LightBeam> beamsAfterWalk = walk(lightBeam, grid);

                beamsAfterWalk.forEach(lightBeamQueue::offer);
            }
        }

        return count.size();
    }

    public long part2() {
        List<String> inputs = readFile();
        List<List<Character>> grid = convertInput(inputs);

        long topScore = 0;

        for (int i = 0; i < grid.size(); i++) {

            // i,0 -> MAX, 0 left side
            topScore = Math.max(part2Helper(LightBeam.builder().coordinate(Coordinate.builder().row(i).col(0).build()).direction(Direction.R).build(), grid), topScore);

            // i,MAX -> MAX, MAX rightside
            topScore = Math.max(part2Helper(LightBeam.builder().coordinate(Coordinate.builder().row(i).col(grid.size()-1).build()).direction(Direction.L).build(), grid), topScore);

            // MAX, i -> MAX, MAX ; bottom
            topScore = Math.max(part2Helper(LightBeam.builder().coordinate(Coordinate.builder().row(grid.size()-1).col(i).build()).direction(Direction.U).build(), grid), topScore);

            // 0,i -> 0,MAX top
            topScore = Math.max(part2Helper(LightBeam.builder().coordinate(Coordinate.builder().row(0).col(i).build()).direction(Direction.D).build(), grid), topScore);
        }

        return topScore;
    }

    public static long part2Helper(LightBeam lb, List<List<Character>> grid){


        Queue<LightBeam> lightBeamQueue = new LinkedList<>();
        lightBeamQueue.offer(lb);

        Set<String> count = new HashSet<>();
        Set<LightBeam> visited = new HashSet<>();


        while (!lightBeamQueue.isEmpty()) {
            LightBeam lightBeam = lightBeamQueue.poll();

            //walk until we hit a split or hit boundry
            if (lightBeam.getCol() >= 0 && lightBeam.getCol() < grid.get(0).size() && lightBeam.getRow() >= 0 && lightBeam.getRow() < grid.size() && !visited.contains(lightBeam)) {
                count.add(encodeCoordinate(lightBeam.getCoordinate()));
                visited.add(lightBeam);

                List<LightBeam> beamsAfterWalk = walk(lightBeam, grid);

                beamsAfterWalk.forEach(lightBeamQueue::offer);
                //last thing in iteration, walk 1 square in direction given current tile
            }
        }

        return count.size();
    }

    public static void main(String[] args) {
        TheFloorWillBeLava theFloorWillBeLava = new TheFloorWillBeLava();
        long result1 = theFloorWillBeLava.part1();
        System.out.println("part1: " + result1);

        long result2 = theFloorWillBeLava.part2();
        System.out.println("part2: " + result2);
    }

    @Builder
    @Data
    @AllArgsConstructor
    public static class LightBeam {
        private Coordinate coordinate;
        private Direction direction;

        public int getRow() {
            return this.coordinate.getRow();
        }

        public int getCol() {
            return this.coordinate.getCol();
        }
    }

    @Builder
    @Data
    @AllArgsConstructor
    public static class Coordinate {
        private int row;
        private int col;
    }

}
