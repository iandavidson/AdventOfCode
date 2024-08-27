package dev.davidson.ian.advent.year2016.day13;

public record Coordinate(int row, int col) {

    public boolean isPath(final Integer designerCode) {
        //x*x + 3*x + 2*x*y + y + y*y
        //Add the office designer's favorite number (your puzzle input).
        //Find the binary representation of that sum; count the number of bits that are 1.
        //
        //    If the number of bits that are 1 is even, it's an open space.
        //    If the number of bits that are 1 is odd, it's a wall.

        long code = (long) col * col
                + 3L * col
                + 2L * col * row
                + row
                + (long) row * row
                + designerCode;

        int bitCount = Long.bitCount(code);
        return bitCount % 2 == 0;
    }
}
