package org.example.advent.year2022.day11;

import java.util.Map;

public enum Operation {

    PLUS, MULT;

    public static final Map<Character, Operation> OPERATION_MAP = Map.of('+', PLUS, '*', MULT);

}
