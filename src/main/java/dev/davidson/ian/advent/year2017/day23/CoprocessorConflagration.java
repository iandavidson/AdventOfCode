package dev.davidson.ian.advent.year2017.day23;

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
public class CoprocessorConflagration {

    private static final String INPUT_PATH = "adventOfCode/2017/day23/input.txt";

    public static void main(String[] args) {
        CoprocessorConflagration coprocessorConflagration = new CoprocessorConflagration();
        List<Instruction> instructions = readFile(INPUT_PATH);

        log.info("Part1: {}", coprocessorConflagration.part1(instructions));
        log.info("Part2: {}", coprocessorConflagration.part2(instructions));
    }

    private static List<Instruction> readFile(final String filePath) {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = CoprocessorConflagration.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
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

    public long part1(final List<Instruction> instructions) {

        int index = 0;
        long mulCount = 0;
        Map<Character, Long> registers = new HashMap<>();

        while (index > -1 && index < instructions.size()) {

            Instruction current = instructions.get(index);
            Operand op1 = current.operands().getFirst();
            Operand op2 = current.operands().get(1);

            switch (current.instructionType()) {
                case jnz -> {
                    if (toValue(registers, op1) != 0) {
                        index += toValue(registers, op2);
                    } else {
                        index++;
                    }
                }
                case mul -> {
                    mulCount++;
                    registers.put(
                            op1.charOp(),
                            toValue(registers, op1) *
                                    toValue(registers, op2)
                    );
                    index++;
                }
                case set -> {
                    registers.put(
                            op1.charOp(),
                            toValue(registers, op2)
                    );
                    index++;
                }
                case sub -> {
                    registers.put(
                            op1.charOp(),
                            toValue(registers, op1) -
                                    toValue(registers, op2)
                    );
                    index++;
                }
            }
        }


        return mulCount;
    }

    public long part2(final List<Instruction> instructions) {
        long b = 107900L;
        long c = 124900L;
        long h = 0L;

        while (b < c + 1) {
            if (!isPrime(b)) {
                h++;
            }

            b += 17;
        }

        return h;
    }

    private boolean isPrime(final long number) {

        for (int i = 2; i <= (long) Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    private Long toValue(final Map<Character, Long> map, final Operand operand) {
        if (operand.isNumber()) {
            return operand.numOp();
        }

        map.putIfAbsent(operand.charOp(), 0L);
        return map.get(operand.charOp());
    }

}
