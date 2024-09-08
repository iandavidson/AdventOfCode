package dev.davidson.ian.advent.year2016.day18;

import java.util.Map;

public enum FloorTile {
    SAFE("."), TRAP("^");

    public static Map<String, FloorTile> FLOOR_MAP = Map.of(
            ".", SAFE,
            "^", TRAP
    );

    private final String label;

    FloorTile(String c) {
        this.label = c;
    }

    public String getLabel() {
        return this.getLabel();
    }
}
