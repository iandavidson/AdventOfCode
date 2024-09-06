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
        log.info("Part1: {}", safeCracking.execute(true));
        log.info("Part2: {}", safeCracking.execute(false));
    }

    private List<Instruction> readFile(final String filePath) {
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

    public Long execute(final boolean part1) {
        List<Instruction> instructions = readFile(INPUT_PATH);
        Map<Character, Long> registers = new HashMap<>();
        int n = instructions.size();
        registers.put('a', part1 ? 7L : 12L);
        registers.put('b', 0L);
        registers.put('c', 0L);
        registers.put('d', 0L);
        int currentIndex = 0;
        while (currentIndex < n) {

            Instruction current = instructions.get(currentIndex);

            if (!current.valid()) {
                currentIndex++;
                continue;
            } else if (currentIndex + 4 < n && canMultiply(instructions, currentIndex)) {
                registers.put('a', registers.get('b') * registers.get('d'));
                registers.put('d', 0L);
                currentIndex += 5;
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
                    Long condValue = toValue(registers, current.operands().getFirst());
                    currentIndex += condValue != 0
                            ? toValue(registers, current.operands().get(1))
                            : 1;
                }
                case tgl -> {
                    long toBeToggledIndex = currentIndex + toValue(registers, current.operands().getFirst());
                    if (toBeToggledIndex > -1 && toBeToggledIndex < n) {
                        Instruction temp = instructions.get((int) toBeToggledIndex);
                        Instruction next = temp.toggle();
                        instructions.set((int) toBeToggledIndex, next);
                    }
                    currentIndex++;
                }
                case null, default -> throw new IllegalStateException("Shouldn't be here");
            }
        }

        return registers.get('a');
    }


    /**
     * Determine if we have this; starting at currentIndex
     * inc a
     * dec c
     * jnz c -2
     * dec d
     * jnz d -5
     *
     * Skip by doing:
     * a = b * d
     */
    private boolean canMultiply(List<Instruction> instructions, int currentIndex) {
        return instructions.get(currentIndex).toString().startsWith("inc a")
                && instructions.get(currentIndex + 1).toString().startsWith("dec c")
                && instructions.get(currentIndex + 2).toString().startsWith("jnz d -5")
                && instructions.get(currentIndex + 3).toString().startsWith("dec d")
                && instructions.get(currentIndex + 4).toString().startsWith("jnz d -5");
    }

    private Long toValue(final Map<Character, Long> map, final Operand operand) {
        if (operand.numOp() != null) {
            return operand.numOp();
        } else {
            return map.get(operand.charOp());
        }
    }
}
