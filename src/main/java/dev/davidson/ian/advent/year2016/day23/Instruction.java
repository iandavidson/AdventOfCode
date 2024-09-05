package dev.davidson.ian.advent.year2016.day23;

public record Instruction(
        InstructionType instructionType,
        Operand op1,
        Operand op2) {

    public static Instruction newInstruction(final String line){
        String [] parts = line.split("\\s+");
        InstructionType it = InstructionType.valueOf(parts[0]);
        Operand op1 = Operand.newOperand(parts[1]);
        Operand op2 = parts.length == 3 ? Operand.newOperand(parts[2]) : null;
        return new Instruction(it, op1, op2);
    }
}
