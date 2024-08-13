package dev.davidson.ian.advent.year2016.day03;

import java.util.List;

public record Triangle(int side1, int side2, int side3, boolean isValid) {

    public static Triangle newTriangle(final List<Integer> sides) {
        if (sides.size() != 3) {
            throw new IllegalStateException("More than 3 sides provided");
        }

        return new Triangle(sides.get(0), sides.get(1), sides.get(2), findValidity(sides.get(0), sides.get(1), sides.get(2)));
    }

    private static Boolean findValidity(final int side1, final int side2, final int side3) {
        return side1 + side2 > side3 && side2 + side3 > side1 && side3 + side1 > side2;
    }
}
