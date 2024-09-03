package dev.davidson.ian.advent.year2016.day21.Instruction;

import dev.davidson.ian.advent.year2016.day21.Applyable;

public record SwapLetterInstruction(char letter1, char letter2) implements Applyable {
    @Override
    public String apply(final String value) {
        StringBuilder result = new StringBuilder(value);
        int index1 = value.indexOf(letter1);
        int index2 = value.indexOf(letter2);

        result.setCharAt(index1, letter2);
        result.setCharAt(index2, letter1);

        return result.toString();
    }
}
