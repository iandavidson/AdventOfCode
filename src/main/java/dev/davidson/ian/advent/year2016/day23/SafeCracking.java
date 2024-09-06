package dev.davidson.ian.advent.year2016.day23;

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
public class SafeCracking {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day23/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day23/input.txt";

    public static void main(String[] args) {
        SafeCracking safeCracking = new SafeCracking();
        List<Instruction> instructions = readFile(INPUT_PATH);
        log.info("Part1: {}", safeCracking.part1(instructions));
    }

    private static List<Instruction> readFile(final String filePath) {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = SafeCracking.class.getClassLoader();
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

    public Integer part1(final List<Instruction> instructions) {
        Map<Character, Integer> registers = new HashMap<>();
        int n = instructions.size();
        registers.put('a', 7);
        registers.put('b', 0);
        registers.put('c', 0);
        registers.put('d', 0);
        int currentIndex = 0;
        while (currentIndex < n) {
            log.info("currentIndex: {}", currentIndex);
            Instruction current = instructions.get(currentIndex);
            if (!current.valid()) {
                currentIndex++;
                continue;
            }

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
                    Integer condValue = toValue(registers, current.operands().getFirst());
                    currentIndex += condValue != 0
                            ? toValue(registers, current.operands().get(1))
                            : 1;
                }
                case tgl -> {
                    int toBeToggledIndex = currentIndex + toValue(registers, current.operands().getFirst());
                    if (toBeToggledIndex > -1 && toBeToggledIndex < n) {
                        Instruction temp = instructions.get(toBeToggledIndex);
                        Instruction next = temp.toggle();
                        instructions.set(toBeToggledIndex, next);
                    }
                    currentIndex++;
                }
                case null, default -> throw new IllegalStateException("Shouldn't be here");
            }

        }

        return registers.get('a');
    }

    private Integer toValue(final Map<Character, Integer> map, final Operand operand) {
        if (operand.numOp() != null) {
            return operand.numOp();
        } else {
            return map.get(operand.charOp());
        }
    }


}
