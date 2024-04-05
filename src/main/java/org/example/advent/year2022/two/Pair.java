package org.example.advent.year2022.two;

import java.util.HashMap;
import java.util.Map;

import static org.example.advent.year2022.two.THROW.PAPER;
import static org.example.advent.year2022.two.THROW.ROCK;
import static org.example.advent.year2022.two.THROW.SCISSOR;

public record Pair(THROW left, THROW right) {
    public static final Map<THROW, Map<THROW, Long>> SCORE_MAP = new HashMap<>();

    static {
        {
            SCORE_MAP.put(ROCK, new HashMap<>());
            SCORE_MAP.get(ROCK).put(PAPER, 8L);
            SCORE_MAP.get(ROCK).put(SCISSOR, 3L);
            SCORE_MAP.get(ROCK).put(ROCK, 4L);

            SCORE_MAP.put(PAPER, new HashMap<>());
            SCORE_MAP.get(PAPER).put(SCISSOR, 9L);
            SCORE_MAP.get(PAPER).put(ROCK, 1L);
            SCORE_MAP.get(PAPER).put(PAPER, 5L);

            SCORE_MAP.put(SCISSOR, new HashMap<>());
            SCORE_MAP.get(SCISSOR).put(ROCK, 7L);
            SCORE_MAP.get(SCISSOR).put(PAPER, 2L);
            SCORE_MAP.get(SCISSOR).put(SCISSOR, 6L);
        }
    }

    public Long computeScore(){
        return SCORE_MAP.get(left).get(right);
    }
}
