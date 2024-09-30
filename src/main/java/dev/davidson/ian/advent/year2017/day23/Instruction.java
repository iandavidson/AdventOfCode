package dev.davidson.ian.advent.year2017.day23;

import java.util.ArrayList;
import java.util.List;

public record Instruction(InstructionType instructionType, List<Operand> operands) {

    public static Instruction newInstruction(final String input){
        String [] parts = input.split("\\s+");

        List<Operand> operands = new ArrayList<>();
        operands.add(Operand.newOperand(parts[1]));
        operands.add(Operand.newOperand(parts[2]));

        return new Instruction(InstructionType.valueOf(parts[0]), operands);
    }
}
