package dev.davidson.ian.advent.year2016.day21.Instruction;


import dev.davidson.ian.advent.year2016.day21.Applyable;

public record RotatePositionInstruction(Character letter) implements Applyable {
    @Override
    public String apply(String value) {
        /*
        rotate based on position of letter X means that the whole string should be rotated to the
        right based on the index of letter X (counting from 0) as determined before this instruction does any rotations.
        Once the index is determined, rotate the string to the right one time, plus a number of times equal to that index,
        plus one additional time if the index was at least 4.

        eg:
        start with: abdec
        rotate based on position of letter b finds the index of letter b (1),
        then rotates the string right once plus a number of times equal to that index (2): ecabd.
         */
        int magnitude = value.indexOf(letter) + 1;
        int startingIndex = value.length() - magnitude; //5 -3= 2
        String newLeft = value.substring(startingIndex);
        String newRight = value.substring(0, startingIndex);
        return newLeft + newRight;
    }
}
