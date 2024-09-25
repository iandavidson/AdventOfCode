package dev.davidson.ian.advent.year2017.day18;

import java.util.ArrayList;
import java.util.List;

public record Instruction(InstructionType instructionType, List<Operand> operands) {

    public static Instruction newInstruction(final String line) {
        String[] parts = line.split("\\s+");
        List<Operand> operands = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) {
            operands.add(Operand.newOperand(parts[i]));
        }

        return new Instruction(InstructionType.valueOf(parts[0]), operands);
    }
}
