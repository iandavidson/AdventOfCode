package advent.year2022.day10;

public record Instruction (InstructionType instructionType, Integer amount){
    public static Instruction parse(String input){
        String [] chunks = input.split("\\s+");

        if(chunks.length == 1){
            return new Instruction(InstructionType.valueOf(chunks[0]), 0);
        }
        return new Instruction(InstructionType.valueOf(chunks[0]), Integer.parseInt(chunks[1]));
    }
}
