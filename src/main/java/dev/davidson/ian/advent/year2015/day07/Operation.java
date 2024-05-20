package dev.davidson.ian.advent.year2015.day07;

import java.util.Map;

public interface Operation {
    Integer evaluate(final Map<String, Integer> labelMap);
    String getResultLabel();
    Boolean isEligible(final Map<String, Integer> labelMap);
}
