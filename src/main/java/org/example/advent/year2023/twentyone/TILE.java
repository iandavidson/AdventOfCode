package org.example.advent.year2023.twentyone;

import java.util.Map;

public enum TILE {
    START, PLOT, ROCK;

    public static final Map<Character, TILE> gridMap = Map.of('S', TILE.START, '.', TILE.PLOT, '#', TILE.ROCK);
}
