package dev.davidson.ian.advent.year2016.day21.Instruction;

import dev.davidson.ian.advent.year2016.day21.Applyable;

public record MovePositionInstruction(int position1, int position2) implements Applyable {

    /*
    move position X to position Y means that the letter
    which is at index X should be removed from the string,
    then inserted such that it ends up at index Y.
     */
    @Override
    public String apply(String value) {
        StringBuilder sb = new StringBuilder(value);
        Character toBeMoved = sb.charAt(position1);
        sb.insert(position2, toBeMoved);
        sb.deleteCharAt(position1);
        return sb.toString();
    }
}
