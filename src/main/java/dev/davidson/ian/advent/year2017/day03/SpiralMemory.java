package dev.davidson.ian.advent.year2017.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpiralMemory {

    private static final String INPUT_PATH = "adventOfCode/2017/day03/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day03/sample.txt";

    public static void main(String [] args){
        SpiralMemory spiralMemory = new SpiralMemory();
        Integer input = readFile(SAMPLE_PATH);
        log.info("Part1: {}", spiralMemory.part1(input));
    }

    public Integer part1(final Integer input){
        int endRow = 0;
        int endCol = 0;

        int currentRow = 1;
        int currentCol = 2;

        Direction dir = Direction.U;
        Set<Coordinate> occupied = new HashSet<>();
        occupied.add(new Coordinate(1,1));
        for(int i = 0; i < input; i++){
            //compute where we move to forthis turn
            currentRow += Direction.SHIFT.get(dir)[0];
            currentCol += Direction.SHIFT.get(dir)[1];
            occupied.add(new Coordinate(currentRow, currentCol));

            //
            Direction look = Direction.LOOK_MAP.get(dir);
            int tempRow = currentRow + Direction.SHIFT.get(look)[0];
            int tempCol = currentCol + Direction.SHIFT.get(look)[1];

            if (!occupied.contains(new Coordinate(tempRow, tempCol))) {
                dir = look;
            }

            endRow = currentRow;
            endCol = currentCol;
        }

        return Math.abs(endRow - 1) + Math.abs(endCol - 1);
    }

    private static Integer readFile(final String filePath){
        ClassLoader cl = SpiralMemory.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            return Integer.parseInt(scanner.nextLine());
        }catch (FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }


}
