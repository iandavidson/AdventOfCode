package org.example.advent.year2022.day09;

import java.util.Map;

public enum Direction {
    U, D, L, R, UR, UL, DR,DL;


    public static Map<Direction, Coordinate> MOVE_MAP = Map.of(
            U, new Coordinate(1L, 0L),
            D, new Coordinate(-1L, 0L),
            L, new Coordinate(0L, -1L),
            R, new Coordinate(0L, 1L),
            UR, new Coordinate(1L, 1L),
            UL, new Coordinate(1L, -1L),
            DR, new Coordinate(-1L, 1L),
            DL, new Coordinate(-1L, -1L)
    );
}
