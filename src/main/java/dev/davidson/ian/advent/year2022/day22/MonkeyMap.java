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

    public static void main(String[] args) {
        MonkeyMap monkeyMap = new MonkeyMap();
        log.info("Part1: {}", monkeyMap.part1());
        //187030 too high
    }

    public int part1() {
        List<String> instructions = new ArrayList<>();
        Grid grid = readFile(instructions);

        State state = new State(grid.getStart());
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

    private void move(final Grid grid, final State state, final int distance) {
        for(int i = 0 ; i < distance; i++){
            Coordinate nextCoord = grid.move(state);

            if(state.getCoordinate().equals(nextCoord)){
                break;
            }else{
                state.setCoordinate(nextCoord);
            }
        }

        //update coordinate in state
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
