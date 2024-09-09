package dev.davidson.ian.advent.year2022.day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonkeyMap {

    //    private static final Coordinate START = new Coordinate(0, 50);
    private static final String INPUT_PATH = "adventOfCode/2022/day22/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2022/day22/sample.txt";
    private static final int CUBE_LENGTH = 50;

    public static void main(String[] args) {
        MonkeyMap monkeyMap = new MonkeyMap();
        log.info("Part1: {}", monkeyMap.part1());
        log.info("Part2: {}", monkeyMap.part2());
    }

    public int part1() {
        List<String> instructions = new ArrayList<>();
        Grid grid = readFile(instructions);

        MovementState state = new MovementState(grid.getStart());
        for (String instruction : instructions) {
            if (Character.isAlphabetic(instruction.charAt(0))) {
                //change dir
                state.changeDirection(instruction);
            } else if (Character.isDigit(instruction.charAt(0))) {
                //move dist
                move(grid, state, Integer.parseInt(instruction));
            } else {
                log.info("we got a problem");

            }
        }

        int row = (state.getCoordinate().row() + 1) * 1000;
        int col = (state.getCoordinate().col() + 1) * 4;
        return row + col + (state.getDirectionIndex());
    }

    private void move(final Grid grid, final MovementState state, final int distance) {
        for (int i = 0; i < distance; i++) {
            Coordinate nextCoord = grid.move(state);

            if (state.getCoordinate().equals(nextCoord)) {
                break;
            } else {
                state.setCoordinate(nextCoord);
            }
        }
    }

    public int part2() {
        List<String> instructions = new ArrayList<>();
        Grid grid = readFile(instructions);
        TILE[][][] cube = buildCube(grid);
        Map<Integer, Map<Direction, Integer>> wrapMap = wrapMap(cube);
        Map<Integer, Map<Direction, Direction>> deltaMap = transformMap(cube);
        CubeMovementState current = new CubeMovementState(
                0,
                new Coordinate(0, 0),
                Direction.R
        );

        for (String instruction : instructions) {
            if (Character.isAlphabetic(instruction.charAt(0))) {
                //change dir
                current = current.changeDirection(instruction);
            } else {
                //move dist
                move(grid, state, Integer.parseInt(instruction));
            }
        }


//        int row = (state.getCoordinate().row() +1) * 1000;
//        int col =  (state.getCoordinate().col() +1) * 4;
//        return row + col + (state.getDirectionIndex());
        return 0;
    }

    private TILE[][][] buildCube(final Grid grid) {
        TILE[][][] cube = new TILE[6][CUBE_LENGTH][CUBE_LENGTH];

        //0
        for (int row = 0; row < 50; row++) {
            for (int col = 50; col < 100; col++) {
                cube[0][row][col - 50] = grid.getGrid().get(row).get(col);
            }
        }

        //1
        for (int row = 0; row < 50; row++) {
            for (int col = 100; col < 150; col++) {
                cube[1][row][col - 100] = grid.getGrid().get(row).get(col);
            }
        }

        //2
        for (int row = 50; row < 100; row++) {
            for (int col = 50; col < 100; col++) {
                cube[2][row - 50][col - 50] = grid.getGrid().get(row).get(col);
            }
        }

        //3
        for (int row = 100; row < 150; row++) {
            for (int col = 0; col < 50; col++) {
                cube[3][row - 100][col] = grid.getGrid().get(row).get(col);
            }
        }


        //4
        for (int row = 100; row < 150; row++) {
            for (int col = 50; col < 100; col++) {
                cube[4][row - 100][col - 50] = grid.getGrid().get(row).get(col);
            }
        }

        //5
        for (int row = 150; row < 200; row++) {
            for (int col = 0; col < 50; col++) {
                cube[5][row - 150][col] = grid.getGrid().get(row).get(col);
            }
        }

        return cube;
    }

    private Map<Integer, Map<Direction, Integer>> wrapMap(final TILE[][][] cube) {
        Map<Integer, Map<Direction, Integer>> map = new HashMap<>();

        //0
        map.put(0, new HashMap<>());
        map.get(0).put(Direction.L, 3);
        map.get(0).put(Direction.R, 1);
        map.get(0).put(Direction.U, 5);
        map.get(0).put(Direction.D, 2);

        //1
        map.put(1, new HashMap<>());
        map.get(1).put(Direction.L, 0);
        map.get(1).put(Direction.R, 4);
        map.get(1).put(Direction.U, 5);
        map.get(1).put(Direction.D, 2);

        //2
        map.put(2, new HashMap<>());
        map.get(2).put(Direction.L, 3);
        map.get(2).put(Direction.R, 1);
        map.get(2).put(Direction.U, 0);
        map.get(2).put(Direction.D, 4);

        //3
        map.put(3, new HashMap<>());
        map.get(3).put(Direction.L, 0);
        map.get(3).put(Direction.R, 4);
        map.get(3).put(Direction.U, 2);
        map.get(3).put(Direction.D, 5);

        //4
        map.put(4, new HashMap<>());
        map.get(4).put(Direction.L, 3);
        map.get(4).put(Direction.R, 1);
        map.get(4).put(Direction.U, 2);
        map.get(4).put(Direction.D, 5);

        //5
        map.put(5, new HashMap<>());
        map.get(5).put(Direction.L, 0);
        map.get(5).put(Direction.R, 4);
        map.get(5).put(Direction.U, 3);
        map.get(5).put(Direction.D, 1);

        return map;
    }

    private Map<Integer, Map<Direction, Direction>> transformMap(final TILE[][][] cube) {
        Map<Integer, Map<Direction, Direction>> map = new HashMap<>();

        //0
        map.put(0, new HashMap<>());
        map.get(0).put(Direction.L, Direction.R);
        map.get(0).put(Direction.R, Direction.R);
        map.get(0).put(Direction.U, Direction.R);
        map.get(0).put(Direction.D, Direction.D);

        //1
        map.put(1, new HashMap<>());
        map.get(1).put(Direction.L, Direction.L);
        map.get(1).put(Direction.R, Direction.L);
        map.get(1).put(Direction.U, Direction.U);
        map.get(1).put(Direction.D, Direction.L);

        //2
        map.put(2, new HashMap<>());
        map.get(2).put(Direction.L, Direction.D);
        map.get(2).put(Direction.R, Direction.U);
        map.get(2).put(Direction.U, Direction.U);
        map.get(2).put(Direction.D, Direction.D);

        //3
        map.put(3, new HashMap<>());
        map.get(3).put(Direction.L, Direction.R);
        map.get(3).put(Direction.R, Direction.R);
        map.get(3).put(Direction.U, Direction.R);
        map.get(3).put(Direction.D, Direction.D);

        //4
        map.put(4, new HashMap<>());
        map.get(4).put(Direction.L, Direction.L);
        map.get(4).put(Direction.R, Direction.L);
        map.get(4).put(Direction.U, Direction.U);
        map.get(4).put(Direction.D, Direction.L);

        //5
        map.put(5, new HashMap<>());
        map.get(5).put(Direction.L, Direction.D);
        map.get(5).put(Direction.R, Direction.U);
        map.get(5).put(Direction.U, Direction.U);
        map.get(5).put(Direction.D, Direction.D);

        return map;
    }

    /*


     12
     3
    45
    6

    1: R: 2
    1: D: 3
    1: U

     ..
     .
    ..
    .
     */


    private Grid readFile(final List<String> instructions) {
        List<List<TILE>> initialGrid = new ArrayList<>();
        ClassLoader cl = MonkeyMap.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            String rawRow = scanner.nextLine();
            int maxWidth = 0;
            while (!rawRow.isBlank()) {
                List<TILE> tempList = Arrays.stream(rawRow.split("")).map(val -> TILE.TILE_MAP.get(val)).toList();
                initialGrid.add(tempList);
                maxWidth = Math.max(maxWidth, tempList.size());
                rawRow = scanner.nextLine();
            }


            setInstructions(scanner.nextLine(), instructions);
            return new Grid(initialGrid, maxWidth);

        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }
    }

    private void setInstructions(final String line, final List<String> instructions) {
        Pattern pattern = Pattern.compile("\\d+|L|R");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            instructions.add(matcher.group());
        }
    }


}
