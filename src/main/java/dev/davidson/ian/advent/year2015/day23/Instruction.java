package dev.davidson.ian.advent.year2015.day23;

import java.util.Map;

public record Instruction(InstructionType instructionType, String register, int instructionOffset) {

    public static Instruction newInstruction(final String line){
        String [] tokens = line.split("\\s+");

        InstructionType instructionType = InstructionType.valueOf(tokens[0]);
        Instruction instruction;
        switch(instructionType){
            case InstructionType.hlf, InstructionType.tpl, InstructionType.inc -> {
                instruction = new Instruction(instructionType, tokens[1],1);
            }
            case InstructionType.jmp -> {
                int offset = Integer.parseInt(tokens[1].substring(1));
                if(tokens[1].charAt(0) == '-'){
                    offset *= -1;
                }

                instruction = new Instruction(instructionType, null, offset);
            }
            case InstructionType.jie, InstructionType.jio -> {
                //jie a, +4

                int offset = Integer.parseInt(tokens[2].substring(1));
                if(tokens[2].charAt(0) == '-'){
                    offset *= -1;
                }

                instruction = new Instruction(instructionType, tokens[1].substring(0, 1), offset);

            }
            default -> throw new IllegalStateException();
        }

        return instruction;
    }

    public int processInstruction(Map<String, Long>  registers){
        int tempOffset = this.instructionOffset;

        switch(this.instructionType){
            case hlf -> registers.put(this.register, registers.get(this.register) / 2);
            case tpl -> registers.put(this.register, registers.get(this.register) * 3);
            case inc -> registers.put(this.register, registers.get(this.register) + 1);
            case jie -> tempOffset = registers.get(this.register) % 2 == 0 ? this.instructionOffset : 1;
            case jio -> tempOffset = registers.get(this.register) == 1 ? this.instructionOffset : 1;
        }

        return tempOffset;
    }
}
