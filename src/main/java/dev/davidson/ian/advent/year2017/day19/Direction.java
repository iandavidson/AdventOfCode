package dev.davidson.ian.advent.year2017.day19;

import java.util.Map;

public enum Direction {
    L, D, R, U;

    public static final Map<Direction, int[]> SHIFTS = Map.of(
            R, new int[]{0, 1},
            L, new int[]{0, -1},
            D, new int[]{1, 0},
            U, new int[]{-1, 0}
    );
}
