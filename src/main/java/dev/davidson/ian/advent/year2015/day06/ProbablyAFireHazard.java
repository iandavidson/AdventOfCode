package dev.davidson.ian.advent.year2015.day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProbablyAFireHazard {

    private static final String INPUT_PATH = "adventOfCode/2015/day06/input.txt";

    public static void main(String[] args) {
        ProbablyAFireHazard probablyAFireHazard = new ProbablyAFireHazard();
        System.out.println(probablyAFireHazard.execute(true));
        System.out.println(probablyAFireHazard.execute(false));
    }

    public int execute(final boolean part1) {
        List<Instruction> instructions = readFile();
        int[][] grid = new int[1000][1000];
        processPart1(instructions, grid, part1);

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                count += grid[i][j];
            }
        }

        return count;
    }

    private void processPart1(final List<Instruction> instructions, final int[][] grid, final boolean part1) {
        for (Instruction instruction : instructions) {
            for (int r = instruction.beginRow(); r < instruction.endRow() + 1; r++) {
                for (int c = instruction.beginCol(); c < instruction.endCol() + 1; c++) {
                    grid[r][c] = InstructionType.apply(instruction.instructionType(), grid[r][c], part1);
                }
            }
        }
    }


    private List<Instruction> readFile() {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = ProbablyAFireHazard.class.getClassLoader();
        File file = new File(cl.getResource(INPUT_PATH).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                instructions.add(Instruction.newInstruction(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return instructions;
    }
}
