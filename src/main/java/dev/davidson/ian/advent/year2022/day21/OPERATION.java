package dev.davidson.ian.advent.year2022.day21;

import java.util.Map;

public enum OPERATION {
    ADD, SUB, MULT, DIV;

    static final Map<String, OPERATION> OPERATION_MAP = Map.of(
            "+", ADD,
            "-", SUB,
            "*", MULT,
            "/", DIV
    );

    static Long executeOperation(final OPERATION operation, final Long dep1Value, final Long dep2Value){
        return switch(operation){
            case ADD -> dep1Value + dep2Value;
            case SUB -> dep1Value - dep2Value;
            case MULT -> dep1Value * dep2Value;
            case DIV -> dep1Value / dep2Value;
        };
    }
}
