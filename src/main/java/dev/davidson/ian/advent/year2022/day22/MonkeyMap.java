package dev.davidson.ian.advent.year2022.day22;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        int row = (state.getCoordinate().row() +1) * 1000;
        int col =  (state.getCoordinate().col() +1) * 4;
        return row + col + (state.getDirectionIndex());
    }

    private void move(final Grid grid, final MovementState state, final int distance) {
        for(int i = 0 ; i < distance; i++){
            Coordinate nextCoord = grid.move(state);

            if(state.getCoordinate().equals(nextCoord)){
                break;
            }else{
                state.setCoordinate(nextCoord);
            }
        }
    }

    public int part2(){
        List<String> instructions = new ArrayList<>();
        Grid grid = readFile(instructions);
        TILE [][][] cube = buildCube(grid);


//        int row = (state.getCoordinate().row() +1) * 1000;
//        int col =  (state.getCoordinate().col() +1) * 4;
//        return row + col + (state.getDirectionIndex());
        return 0;
    }

    private TILE[][][] buildCube(final Grid grid){
        TILE[][][] cube = new TILE[6][CUBE_LENGTH][CUBE_LENGTH];

        //0
        for(int row = 0; row < 50; row++){
            for(int col = 50; col < 100; col++){
                cube[0][row][col-50] = grid.getGrid().get(row).get(col);
            }
        }

        //1
        for(int row = 0; row < 50; row++){
            for(int col = 100; col < 150; col++){
                cube[1][row][col-100] = grid.getGrid().get(row).get(col);
            }
        }

        //2
        for(int row = 50; row < 100; row++){
            for(int col = 50; col < 100; col++){
                cube[2][row-50][col-50] = grid.getGrid().get(row).get(col);
            }
        }

        //3
        for(int row = 100; row < 150; row++){
            for(int col = 0; col < 50; col++){
                cube[3][row-100][col] = grid.getGrid().get(row).get(col);
            }
        }


        //4
        for(int row = 100; row < 150; row++){
            for(int col = 50; col < 100; col++){
                cube[4][row-100][col-50] = grid.getGrid().get(row).get(col);
            }
        }

        //5
        for(int row = 150; row < 200; row++){
            for(int col =0; col < 50; col++){
                cube[5][row-150][col] = grid.getGrid().get(row).get(col);
            }
        }

        /*
        X01
        X2X
        34X
        5XX
             */


        //1 => front (x,y,0); no rotation
        // -> plane (0, 49) -> (49, 99)

        //2 => rightside (CUBE_LEN,y,z); no rotation
        // -> plane (0, 100) -> (49, 149)

        //3 => bottom (x,0,z);  no rotation
        // -> plane (50, 50) -> (99, 99)

        //4 => left (0, y, z); rotate 2 times (doesn't matter which way)
        // -> plane (100, 0) -> (149, 49)

        //5 => back (x,y,CUBE_LEN); rotate 2 times(doesn't matter)
        // -> plane (100, 50) -> (149, 99)

        //6 => top (x,CUBE_LEN,z); rotate right once
        // -> plane (150, 0) -> (199, 49)
        return cube;
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