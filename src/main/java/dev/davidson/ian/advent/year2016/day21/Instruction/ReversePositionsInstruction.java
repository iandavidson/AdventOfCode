package dev.davidson.ian.advent.year2016.day21.Instruction;

import dev.davidson.ian.advent.year2016.day21.Applyable;

public record ReversePositionsInstruction(int start, int finish) implements Applyable {
    @Override
    public String apply(String value) {
        String reverseMiddle = new StringBuilder(value.substring(start, finish+1)).reverse().toString();
        return value.substring(0, start) + reverseMiddle + value.substring(finish+1);
    }
}
