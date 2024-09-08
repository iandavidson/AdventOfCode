package dev.davidson.ian.advent.year2016.day21.Instruction;

import dev.davidson.ian.advent.year2016.day21.Applyable;

public record MovePositionInstruction(int p1, int p2) implements Applyable {
    @Override
    public String apply(String value) {
        final char ch = value.charAt(p1);
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            if (i != p1) {
                if (i == p2 && p1 > p2) {
                    result.append(ch);
                }
                result.append(value.charAt(i));
                if (i == p2 && p1 < p2) {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }
}
