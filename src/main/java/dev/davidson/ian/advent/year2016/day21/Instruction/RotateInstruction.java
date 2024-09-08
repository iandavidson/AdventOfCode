package dev.davidson.ian.advent.year2016.day21.Instruction;


import dev.davidson.ian.advent.year2016.day21.Applyable;
import dev.davidson.ian.advent.year2016.day21.RotationDirection;

public record RotateInstruction(int magnitude, RotationDirection rotationDirection) implements Applyable {

    @Override
    public String apply(final String value) {
        switch(rotationDirection){
            case Left -> {
                return applyLeft(value);
            }
            case Right ->{
                return applyRight(value);
            }
            case null, default -> throw new IllegalArgumentException();
        }
    }

    private String applyLeft(final String value){
        int startingIndex = magnitude;
        String newRight = value.substring(0, startingIndex);
        String newLeft = value.substring(startingIndex);
        return newLeft + newRight;
    }

    private String applyRight(final String value){
        int startingIndex = value.length() - magnitude; //5 -3= 2
        String newLeft = value.substring(startingIndex);
        String newRight = value.substring(0, startingIndex);
        return newLeft + newRight;
    }
}
