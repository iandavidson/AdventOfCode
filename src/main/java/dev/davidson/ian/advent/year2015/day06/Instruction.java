package dev.davidson.ian.advent.year2015.day06;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Instruction(InstructionType instructionType, int beginRow, int beginCol, int endRow, int endCol) {

    public static Instruction newInstruction(String s) {
        Pattern p = Pattern.compile("(\\D+)(\\d+),(\\d+)(\\D+)(\\d+),(\\d+)");

        Matcher m = p.matcher(s);


        if (m.find()) {
            String instructionTypeRaw = m.group(1).trim();

            InstructionType it =
                    switch (instructionTypeRaw) {
                        case "turn off" -> InstructionType.OFF;
                        case "turn on" -> InstructionType.ON;
                        case "toggle" -> InstructionType.TOGGLE;
                        default -> throw new IllegalStateException("Unexpected value: " + instructionTypeRaw);
                    };


            return new Instruction(
                    it, Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(5)), Integer.parseInt(m.group(6))
            );
        }

        throw new IllegalStateException("🥸");
    }
}
