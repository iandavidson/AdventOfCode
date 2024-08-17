package dev.davidson.ian.advent.year2016.day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwoFactorAuth {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day08/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day08/input.txt";
    private static final Integer ROW_MAX = 6;
    private static final Integer COL_MAX = 50;

    public static void main(String[] args) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.execute();
    }

    public void execute() {
        List<Instruction> instructions = readFile();

        List<Coordinate> coordinates = new ArrayList<>();
        for (Instruction instruction : instructions) {
            coordinates = instruction.execute(coordinates);
        }
        log.info("Part1: {}", coordinates.size());
        log.info("Part2:");
        char [][] board = new char[ROW_MAX][COL_MAX];
        for(int i = 0 ; i < board.length; i++){
            Arrays.fill(board[i], ' ');
        }

        for(Coordinate coordinate : coordinates){
            board[coordinate.row()][coordinate.col()] = '#';
        }

        for(int i = 0; i < ROW_MAX; i ++){
            log.info("{}", board[i]);
        }

        //ZFHFSFOGPO
    }

    private List<Instruction> readFile() {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = TwoFactorAuth.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                instructions.add(newInstruction(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Failed to read file");
        }

        return instructions;
    }

    private static Instruction newInstruction(final String line) {
        String[] parts = line.split("\\s+");
        Instruction instruction;

        if (parts.length == 2) {
            //rectangle
            String[] spans = parts[1].split("x");
            instruction = Rectangle.builder()
                    .rows(Integer.parseInt(spans[1]))
                    .cols(Integer.parseInt(spans[0]))
                    .build();
        } else {
            //shift
            String[] axis_index = parts[2].split("=");
            instruction =
                    Shift.builder()
                            .axis(axis_index[0].equals("x") ? Axis.X : Axis.Y)
                            .index(Integer.parseInt(axis_index[1]))
                            .magnitude(Integer.parseInt(parts[4]))
                            .build();
        }

        return instruction;
    }

}
