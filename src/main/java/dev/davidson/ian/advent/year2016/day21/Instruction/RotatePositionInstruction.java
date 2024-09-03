package dev.davidson.ian.advent.year2016.day21.Instruction;


import dev.davidson.ian.advent.year2016.day21.Applyable;

public record RotatePositionInstruction(Character letter) implements Applyable {
    @Override
    public String apply(final String value) {
        int magnitude = value.indexOf(letter);
        String result = rotateRight(value, 1);
        result = rotateRight(result, magnitude);
        if(magnitude >= 4){
            result = rotateRight(result,1);
        }
        return result;
    }

    private String rotateRight(final String value, final int magnitude){
        int startingIndex = value.length() - magnitude;
        String newLeft = value.substring(startingIndex);
        String newRight = value.substring(0, startingIndex);
        return newLeft + newRight;
    }
}
