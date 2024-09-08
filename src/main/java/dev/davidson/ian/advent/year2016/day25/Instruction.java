package dev.davidson.ian.advent.year2016.day25;

import java.util.ArrayList;
import java.util.List;

public record Instruction(
        InstructionType instructionType,
        List<Operand> operands) {

    public static Instruction newInstruction(final String line) {
        String[] parts = line.split("\\s+");
        InstructionType it = InstructionType.valueOf(parts[0]);
        List<Operand> operands = new ArrayList<>();
        operands.add(Operand.newOperand(parts[1]));
        if (parts.length == 3) {
            operands.add(Operand.newOperand(parts[2]));
        }
        return new Instruction(it, operands);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(instructionType.toString());
        operands.forEach(sb::append);
        return sb.toString();
    }
}
