package dev.davidson.ian.advent.year2015.day07;

import dev.davidson.ian.advent.year2015.day07.instruction.AndInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.AssignmentInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.NotInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.OrInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.ShiftInstruction;

public class OperationFactory {
    public static Operation newOperation(final String line) {
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
            return new AndInstruction(new Wire(operationTokens[0].trim()), new Wire(operationTokens[2]), new Wire(split[1].trim()));

        } else if (line.contains("OR")) {
//            x OR y -> e
            return new OrInstruction(new Wire(operationTokens[0].trim()), new Wire(operationTokens[2]), new Wire(split[1].trim()));

        } else if (line.contains("SHIFT")) {
//            x LSHIFT 2 -> f
            return new ShiftInstruction(operationTokens[1].trim(), new Wire(operationTokens[0].trim()),
                    Integer.parseInt(operationTokens[2].trim()), new Wire(split[1].trim()));

        } else if (line.contains("NOT")) {
            //NOT x -> h
            return new NotInstruction(new Wire(operationTokens[1].trim()), new Wire(split[1].trim()));

        } else {
            //outright assignment
            //123 -> x
            // y -> x !!!!!
            return new AssignmentInstruction(new Wire(split[0].trim()), new Wire(split[1].trim())) ;
        }
    }
}
