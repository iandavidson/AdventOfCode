package advent.year2022.day02;

import java.util.Map;

public enum THROW {

    ROCK, PAPER, SCISSOR;

    public static final Map<String, THROW> LEFT = Map.of("A", ROCK, "B", PAPER, "C", SCISSOR);
    public static final Map<String, THROW> RIGHT = Map.of("X", ROCK, "Y", PAPER, "Z", SCISSOR);

}
