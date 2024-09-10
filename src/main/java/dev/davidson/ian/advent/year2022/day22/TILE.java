package dev.davidson.ian.advent.year2022.day22;

import java.util.Map;

public enum TILE {
    EMPTY, BLOCKED, OUT;

    static Map<String, TILE> TILE_MAP = Map.of(
            " ", OUT,
            "#", BLOCKED,
            ".", EMPTY
    );
}
