package org.example.advent.year2022.day09;

import java.util.Map;

public enum Direction {
    U, D, L, R;

    public static Map<Direction, Coordinate> MOVE_MAP = Map.of(
            U, new Coordinate(1L, 0L),
            D, new Coordinate(-1L, 0L),
            L, new Coordinate(0L, -1L),
            R, new Coordinate(0L, 1L)
    );
}
