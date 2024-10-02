package dev.davidson.ian.advent.year2017.day25;

import java.util.List;

public record Transition(
        int writeValue,
        int tapeMovement,
        Character nextState) {

    public static Transition newTransition(final List<String> inputLines) {
        String valueRaw = inputLines.get(1).trim().split("\\s+")[4];
        valueRaw = valueRaw.substring(0, valueRaw.length() - 1);
        int writeValue = Integer.parseInt(valueRaw);

        String movementRaw = inputLines.get(2).trim().split("\\s+")[6];
        movementRaw = movementRaw.substring(0, movementRaw.length() - 1);
        int tapeMovement = movementRaw.equals("right") ? 1 : -1;

        Character nextState = inputLines.getLast().trim().charAt(
                inputLines.getLast().trim().length() - 2
        );

        return new Transition(writeValue, tapeMovement, nextState);
    }
}
