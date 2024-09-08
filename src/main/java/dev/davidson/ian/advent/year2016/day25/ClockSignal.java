package dev.davidson.ian.advent.year2016.day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClockSignal {

    private static final String INPUT_PATH = "adventOfCode/2016/day25/input.txt";
    private static final String MATCH = "01010101010101";

    public static void main(String[] args) {
        ClockSignal clockSignal = new ClockSignal();
        log.info("Part1: {}", clockSignal.part1());
    }

    public Long part1() {
        List<Instruction> instructions = readFile();

        Map<Character, Long> registers = new HashMap<>();
        registers.put('a', 1L);
        registers.put('b', 0L);
        registers.put('c', 0L);
        registers.put('d', 0L);

        boolean found = false;
        Long aStart = 1L;
        while (!found) {
            registers.put('a', aStart);
            if (findSignal(registers, instructions)) {
                return aStart;
            }

            aStart++;
        }
        return -1L;
    }

    private boolean findSignal(final Map<Character, Long> registers, final List<Instruction> instructions) {
        StringBuilder outBuild = new StringBuilder();
        int currentIndex = 0;
        while (currentIndex > -1 && currentIndex < instructions.size()) {
            Instruction current = instructions.get(currentIndex);

            switch (current.instructionType()) {
                case inc -> {
                    Character label = current.operands().getFirst().charOp();
                    registers.put(label,
                            toValue(registers, current.operands().getFirst()) + 1);
                    currentIndex++;
                }
                case dec -> {
                    Character label = current.operands().getFirst().charOp();
                    registers.put(label,
                            toValue(registers, current.operands().getFirst()) - 1);

                    currentIndex++;
                }
                case cpy -> {
                    Character label = current.operands().get(1).charOp();
                    registers.put(
                            label,
                            toValue(registers, current.operands().getFirst()));

                    currentIndex++;
                }
                case jnz -> {
                    Long condValue = toValue(registers, current.operands().getFirst());
                    currentIndex += condValue != 0
                            ? toValue(registers, current.operands().get(1))
                            : 1;
                }
                case out -> {
                    outBuild.append(toValue(registers, current.operands().getFirst()));
                    if (outBuild.length() == MATCH.length()) {
                        return outBuild.toString().equals(MATCH);
                    }
                    currentIndex++;
                }
            }
        }

        return false;
    }

    private Long toValue(final Map<Character, Long> map, final Operand operand) {
        if (operand.numOp() != null) {
            return operand.numOp();
        } else {
            return map.get(operand.charOp());
        }
    }

    private List<Instruction> readFile() {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = ClockSignal.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                instructions.add(Instruction.newInstruction(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return instructions;
    }
}
