package dev.davidson.ian.advent.year2015.day07;

import dev.davidson.ian.advent.year2015.day07.instruction.AssignmentInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.NotInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.OrInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.ShiftInstruction;

public class InstructionFactory {
    public static Instruction newInstruction(final String line) {
        /*

456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
         */
        String[] split = line.split("\\s+->\\s+");
        String[] operationTokens = split[0].split("\\s+");

        if (line.contains("AND")) {
//            x AND y -> d
            return new OrInstruction(operationTokens[0].trim(), operationTokens[2], split[1].trim());

        } else if (line.contains("OR")) {
//            x OR y -> e
            return new OrInstruction(operationTokens[0].trim(), operationTokens[2], split[1].trim());

        } else if (line.contains("SHIFT")) {
//            x LSHIFT 2 -> f
            return new ShiftInstruction(operationTokens[1].trim(), operationTokens[0].trim(),
                    Integer.parseInt(operationTokens[2].trim()), split[1].trim());

        } else if (line.contains("NOT")) {
            //NOT x -> h
            return new NotInstruction(operationTokens[1].trim(), split[1].trim());

        } else {
            //outright assignment
            //123 -> x
            return new AssignmentInstruction(Integer.parseInt(split[0].trim()), split[1].trim());

        }
    }
}
