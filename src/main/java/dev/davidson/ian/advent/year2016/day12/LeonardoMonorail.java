package dev.davidson.ian.advent.year2016.day12;

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
public class LeonardoMonorail {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day12/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day12/input.txt";

    public static void main(String[] args) {
        LeonardoMonorail leonardoMonorail = new LeonardoMonorail();
        log.info("Part1: {}", leonardoMonorail.execute(true));
        log.info("Part2: {}", leonardoMonorail.execute(false));
    }

    public int execute(final boolean part1) {
        List<Instruction> instructions = readFile();
        Map<String, Integer> registerMap = new HashMap<>();

        registerMap.put("a", 0);
        registerMap.put("b", 0);
        registerMap.put("c", part1 ? 0 : 1);
        registerMap.put("d", 0);

        int current = 0;
        while (current < instructions.size()) {
            Instruction instruction = instructions.get(current);

            switch (instruction.instructionType()) {
                case inc -> {
                    //inc d
                    String label = instruction.operands().getFirst().label();
                    registerMap.put(label,
                            toValue(registerMap, instruction.operands().getFirst()) + 1);
                    current++;
                }
                case dec -> {
                    //dec d
                    String label = instruction.operands().getFirst().label();
                    registerMap.put(label, toValue(registerMap, instruction.operands().getFirst()) - 1);
                    current++;
                }
                case cpy -> {
                    String label = instruction.operands().get(1).label();
                    if (instruction.operands().getFirst().label() == null) {
                        //copy 1 a
                        registerMap.put(
                                label,
                                toValue(registerMap, instruction.operands().getFirst()));
                    } else {
                        //copy c b
                        registerMap.put(
                                label,
                                toValue(registerMap, instruction.operands().getFirst()));
                    }
                    current++;

                }
                case jnz -> {
                    //jnz x y jumps to an instruction y away (positive means forward; negative means backward),
                    // but only if x is not zero.

                    Integer condValue = toValue(registerMap, instruction.operands().getFirst());
                    current += condValue != 0
                            ? toValue(registerMap, instruction.operands().get(1))
                            : 1;
                }
                case null, default -> throw new IllegalStateException("Invalid InstructionType found");
            }

        }

        return registerMap.get("a");
    }

    private Integer toValue(final Map<String, Integer> map, final Operand operand) {
        if (operand.value() != null) {
            return operand.value();
        } else {
            return map.get(operand.label());
        }
    }

    private List<Instruction> readFile() {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = LeonardoMonorail.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                instructions.add(Instruction.newInstruction(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file");
        }

        return instructions;
    }

}
