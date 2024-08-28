package dev.davidson.ian.advent.year2016.day12;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Builder
public record Instruction(InstructionType instructionType, List<Operand> operands) {

    public static Instruction newInstruction(final String line) {
        String[] parts = line.split("\\s+");

        InstructionType instructionType = InstructionType.valueOf(parts[0]);
        List<Operand> operands = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) {
            operands.add(Operand.newOperand(parts[i]));
        }

        return Instruction.builder()
                .instructionType(instructionType)
                .operands(operands)
                .build();
    }


}
