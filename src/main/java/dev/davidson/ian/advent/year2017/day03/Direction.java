package dev.davidson.ian.advent.year2017.day03;

import java.util.Map;

public enum Direction {
    R, U, L, D;

    public static final Map<Direction, int[]> SHIFT = Map.of(
            R, new int[]{0, 1},
            U, new int[]{-1, 0},
            L, new int[]{0, -1},
            D, new int[]{1, 0}
    );

    public static final Map<Direction, Direction> LOOK_MAP = Map.of(
            R, U,
            U, L,
            L, D,
            D, R
    );
}
