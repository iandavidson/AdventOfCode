package dev.davidson.ian.advent.year2016.day21.Instruction;

import dev.davidson.ian.advent.year2016.day21.Applyable;

public record SwapPositionInstruction(int position1, int position2) implements Applyable {
    @Override
    public String apply(final String value) {
        StringBuilder result = new StringBuilder(value);

        char p1 = value.charAt(position1);
        char p2 = value.charAt(position2);

        result.setCharAt(position1, p2);
        result.setCharAt(position2, p1);

        return result.toString();
    }
}
