package dev.davidson.ian.advent.year2016.day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoTimeForTaxiCab {

    private static final String INPUT_PATH = "adventOfCode/2016/day01/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2016/day01/sample.txt";
    private static final List<int[]> DIRECTION_MOVES = List.of(
            new int[]{-1, 0}, // north
            new int[]{0, 1}, // east
            new int[]{1, 0}, // south
            new int[]{0, -1} // west
    );

    public static void main(String[] args) {
        NoTimeForTaxiCab noTimeForTaxiCab = new NoTimeForTaxiCab();
        noTimeForTaxiCab.execute();
    }

    public void execute() {
        int currentDirection = 0; //north
        int currentRow = 0;
        int currentCol = 0;

        List<Instruction> instructions = readfile();

        Set<String> visited = new HashSet<>();
        visited.add(currentRow + ":" + currentCol);
        boolean lookingForPart2 = true;
        Integer part2Answer = 0;

        for (Instruction instruction : instructions) {

            currentDirection = (currentDirection + instruction.turn()) % 4;
            if (currentDirection < 0) {
                currentDirection += 4;
            }

            for (int i = 0; i < instruction.magnitude(); i++) {
                currentRow += (DIRECTION_MOVES.get(currentDirection)[0]);
                currentCol += (DIRECTION_MOVES.get(currentDirection)[1]);

                String current = currentRow + ":" + currentCol;

                if (visited.contains(current) && lookingForPart2) {
                    part2Answer = Math.abs(currentRow) + Math.abs(currentCol);
                    lookingForPart2 = false;
                } else {
                    visited.add(current);
                }
            }
        }

        log.info("Part1: {}", Math.abs(currentRow) + Math.abs(currentCol));
        log.info("Part2: {}", part2Answer);
    }

    private List<Instruction> readfile() {
        List<String> inputLines = new ArrayList<>();
        ClassLoader cl = NoTimeForTaxiCab.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            log.error("Failed to read input file");
            throw new IllegalStateException();
        }

        List<Instruction> instructions = new ArrayList<>();
        for (String line : inputLines) {
            String[] chunks = line.split("\\s+");
            for (String chunk : chunks) {
                instructions.add(Instruction.newInstruction(chunk));
            }
        }

        return instructions;
    }
}
