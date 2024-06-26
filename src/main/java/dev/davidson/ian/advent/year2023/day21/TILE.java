package dev.davidson.ian.advent.year2023.day21;

import java.util.Map;

public enum TILE {
    START, PLOT, ROCK;

    public static final Map<Character, TILE> gridMap = Map.of('S', TILE.START, '.', TILE.PLOT, '#', TILE.ROCK);
}
