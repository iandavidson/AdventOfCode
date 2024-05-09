package advent.year2023.day23;


import java.util.Map;

public enum TILE {
    BLOCK,
    PATH,
    SLOPE_RIGHT,
    SLOPE_DOWN;

    public static final Map<Character, TILE> gridMap =
            Map.of('#', TILE.BLOCK,
                    '.', TILE.PATH,
                    '>', TILE.SLOPE_RIGHT,
                    'v', TILE.SLOPE_DOWN);
}
