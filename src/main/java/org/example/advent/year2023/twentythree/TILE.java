package org.example.advent.year2023.twentythree;


import java.util.Map;

public enum TILE {
    BLOCK,
    PATH,
//    SLOPE_LEFT,
    SLOPE_RIGHT,
//    SLOPE_UP,
    SLOPE_DOWN;

    public static final Map<Character, TILE> gridMap =
            Map.of('#', TILE.BLOCK,
                    '.', TILE.PATH,
                    '>', TILE.SLOPE_RIGHT,
                    'v', TILE.SLOPE_DOWN);
}
