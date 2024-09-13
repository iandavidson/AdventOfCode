package dev.davidson.ian.advent.year2017.day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IHeardYouLikeRegisters {

    private static final String INPUT_PATH = "adventOfCode/2017/day08/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day08/sample.txt";

    public static void main(String[] args) {
        IHeardYouLikeRegisters iHeardYouLikeRegisters = new IHeardYouLikeRegisters();
        List<Instruction> instructions = readFile(INPUT_PATH);
        log.info("Part1: {}", iHeardYouLikeRegisters.part1(instructions));
        log.info("Part2: {}", iHeardYouLikeRegisters.part2(instructions));
    }

    private static List<Instruction> readFile(final String filePath) {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = IHeardYouLikeRegisters.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                instructions.add(Instruction.newInstruction(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided file path");
        }

        return instructions;
    }

    public Long part1(final List<Instruction> instructions) {
        Map<String, Long> registers = new HashMap<>();

        for (Instruction instruction : instructions) {
            registers.putIfAbsent(instruction.applyLabel(), 0L);
            registers.putIfAbsent(instruction.condLabel(), 0L);

            if (conditionSatisfied(instruction, registers)) {
                registers.put(instruction.applyLabel(), registers.get(instruction.applyLabel()) + instruction.shift());
            }
        }

        return registers.values().stream().max(Comparator.naturalOrder()).orElseThrow(
                () -> new IllegalStateException("couldn't find max from registers")
        );
    }

    public Long part2(final List<Instruction> instructions) {
        Map<String, Long> registers = new HashMap<>();

        long maxRegisterValue = Long.MIN_VALUE;

        for (Instruction instruction : instructions) {
            registers.putIfAbsent(instruction.applyLabel(), 0L);
            registers.putIfAbsent(instruction.condLabel(), 0L);

            if (conditionSatisfied(instruction, registers)) {
                long updatedRegisterValue = registers.get(instruction.applyLabel()) + instruction.shift();
                registers.put(instruction.applyLabel(), updatedRegisterValue);
                maxRegisterValue = Math.max(maxRegisterValue, updatedRegisterValue);
            }
        }

        return maxRegisterValue;
    }

    private boolean conditionSatisfied(Instruction instruction, Map<String, Long> registers) {
         /*
            ==
            >
            <
            !=
            >=
            <=
         */

        Long conditionValue = registers.get(instruction.condLabel());
        switch (instruction.comparison()) {
            case "==" -> {
                return Objects.equals(conditionValue, instruction.comparator());
            }
            case ">" -> {
                return conditionValue > instruction.comparator();
            }
            case "<" -> {
                return conditionValue < instruction.comparator();
            }
            case "!=" -> {
                return !Objects.equals(conditionValue, instruction.comparator());
            }
            case ">=" -> {
                return conditionValue >= instruction.comparator();
            }
            case "<=" -> {
                return conditionValue <= instruction.comparator();
            }
            case null, default -> {
                throw new IllegalStateException("found this instead... : " + instruction.comparison());
            }
        }
    }
}
