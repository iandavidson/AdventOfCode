package org.example.advent.year2022.day10;

public record Instruction (InstructionType instructionType, int amount){
    public static Instruction parse(String input){
        String [] chunks = input.split("\\s+");
        return new Instruction(InstructionType.valueOf(chunks[0]), Integer.parseInt(chunks[1]));
    }
}
