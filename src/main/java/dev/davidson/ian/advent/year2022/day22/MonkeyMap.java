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
//        Map<Integer, Map<Direction, Integer>> wrapMap = wrapMap(cube);
//        Map<Integer, Map<Direction, Direction>> deltaMap = transformMap(cube);
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
                current = current.moveDistance(Integer.parseInt(instruction), cube);
            }
        }

//        int row = (state.getCoordinate().row() +1) * 1000;
//        int col =  (state.getCoordinate().col() +1) * 4;
//        return row + col + (state.getDirectionIndex());

        Coordinate finish = cubeToFlatCoordinate(current);
        //too low : 107193
        return ((finish.row()+1) * 1000) + ((finish.col()+1) * 4) + current.getDirection().ordinal();
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


    private Coordinate cubeToFlatCoordinate(final CubeMovementState cubeMovementState){
        int rowAdd = 0;
        int colAdd = 0;
        switch(cubeMovementState.getCubeFace()){
            case 0 -> {
                colAdd = 50;
            }
            case 1 -> {
                colAdd = 100;
            }
            case 2 -> {
                colAdd = 50;
                rowAdd = 50;
            }
            case 3 -> {
                rowAdd = 100;
            }
            case 4 -> {
                colAdd = 50;
                rowAdd = 100;
            }
            case 5 -> {
                rowAdd = 150;
            }
        }

        return new Coordinate(
                cubeMovementState.getCoordinate().row() + rowAdd,
                cubeMovementState.getCoordinate().col() + colAdd);
    }

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
