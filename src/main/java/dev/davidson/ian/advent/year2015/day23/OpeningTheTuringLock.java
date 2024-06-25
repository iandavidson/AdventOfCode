package dev.davidson.ian.advent.year2015.day23;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class OpeningTheTuringLock {

    private static final String INPUT_PATH = "adventOfCode/2015/day23/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day23/sample.txt";

    public static void main(String[] args) {
        OpeningTheTuringLock openingTheTuringLock = new OpeningTheTuringLock();
        log.info("Part1: {}", openingTheTuringLock.execute(false));
        log.info("Part2: {}", openingTheTuringLock.execute(true));
    }

    private Long execute(boolean part2) {
        List<Instruction> instructions = readFile();

        Map<String, Long> registers = new HashMap<>();

        registers.put("a", part2 ? 1L : 0L);
        registers.put("b", 0L);

        int executionIndex = 0;
        while (executionIndex < instructions.size()) {
            int shift = instructions.get(executionIndex).processInstruction(registers);
            executionIndex += shift;
        }

        return registers.get("b");
    }

    private List<Instruction> readFile() {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = OpeningTheTuringLock.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                instructions.add(Instruction.newInstruction(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        return instructions;
    }
}
