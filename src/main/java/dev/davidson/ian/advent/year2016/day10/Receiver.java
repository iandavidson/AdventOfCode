package dev.davidson.ian.advent.year2016.day10;

import java.util.Map;

public enum Receiver {
    BOT, OUTPUT;

    public static final Map<String, Receiver> STRING_TO_RECEIVER = Map.of(
        "bot", BOT,
        "output", OUTPUT
    );
}
