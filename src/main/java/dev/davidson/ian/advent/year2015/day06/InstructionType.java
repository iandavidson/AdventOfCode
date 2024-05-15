package dev.davidson.ian.advent.year2015.day06;

public enum InstructionType {

    ON, OFF, TOGGLE;

    public static int apply(final InstructionType instructionType, final int value, final boolean part1) {

        if (part1) {
            return switch (instructionType) {
                case ON -> 1;
                case OFF -> 0;
                case TOGGLE -> value == 1 ? 0 : 1;
            };

        } else {
            return switch (instructionType) {
                case ON -> value + 1;
                case OFF -> Math.max(value - 1, 0);
                case TOGGLE -> value + 2;
            };
        }
    }
}
