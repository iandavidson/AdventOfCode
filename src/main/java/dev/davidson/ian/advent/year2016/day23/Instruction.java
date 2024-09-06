package dev.davidson.ian.advent.year2016.day23;

import java.util.ArrayList;
import java.util.List;

public record Instruction(
        InstructionType instructionType,
        List<Operand> operands,
        boolean valid) {

    public static Instruction newInstruction(final String line) {
        String[] parts = line.split("\\s+");
        InstructionType it = InstructionType.valueOf(parts[0]);
        List<Operand> operands = new ArrayList<>();
        operands.add(Operand.newOperand(parts[1]));
        if (parts.length == 3) {
            operands.add(Operand.newOperand(parts[2]));
        }
        return new Instruction(it, operands, true);
    }

    public Instruction toggle() {
        boolean nextValid = true;
        InstructionType nextInstructionType = null;
        if (operands.size() == 1) {
//        For one-argument instructions, inc becomes dec, and all other one-argument instructions become inc.
            if (instructionType == InstructionType.inc) {
                nextInstructionType = InstructionType.dec;
            } else {
                nextInstructionType = InstructionType.inc;
            }
        } else {
//        For two-argument instructions, jnz becomes cpy, and all other two-instructions become jnz.
            if (instructionType == InstructionType.jnz) {
                nextInstructionType = InstructionType.cpy;
                if (operands.get(1).isNumber()) {
                    nextValid = false;
                }
            } else {
                nextInstructionType = InstructionType.jnz;
            }
        }

        return new Instruction(nextInstructionType, operands, nextValid);
    }
}
