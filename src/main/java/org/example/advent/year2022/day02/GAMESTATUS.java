package org.example.advent.year2022.day02;

import java.util.Map;

public enum GAMESTATUS {

    LOSE, TIE, WIN;

    public static Map<String, GAMESTATUS> STATUS_MAP = Map.of("X", LOSE, "Y", TIE, "Z", WIN);
}
