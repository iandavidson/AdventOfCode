package dev.davidson.ian.advent.year2022.day24;

import java.util.Map;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public static final Map<Direction, int[]> DIRECTION_MAP = Map.of(
            UP, new int[]{-1, 0},
            RIGHT, new int[]{0, 1},
            DOWN, new int[]{1, 0},
            LEFT, new int[]{0, -1}
    );

    public static final Map<Character, Direction> TILE_TO_DIRECTION_MAP = Map.of(
            '^', UP,
            '>', RIGHT,
            'v', DOWN,
            '<', LEFT
    );

}
