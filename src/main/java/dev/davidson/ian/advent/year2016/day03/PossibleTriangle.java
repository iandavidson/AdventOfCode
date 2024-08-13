package dev.davidson.ian.advent.year2016.day03;

import java.util.List;

public record PossibleTriangle(List<Integer> possibleSides) {

    public Long possibleCombos() {
        long count = 0L;

        for (int i = 0; i < possibleSides.size() - 2; i += 3) {
            count += isValidCombo(possibleSides.get(i), possibleSides.get(i + 1), possibleSides.get(i + 2)) ? 1 : 0;
        }

        return count;
    }

    private boolean isValidCombo(final int side1, final int side2, final int side3) {
        return side1 + side2 > side3 && side2 + side3 > side1 && side3 + side1 > side2;
    }
}
