package dev.davidson.ian.advent.year2016.day12;

import lombok.Builder;

@Builder
public record Operand(String label, Integer value) {

    public static Operand newOperand(final String op) {
        //leveraging the fact that we only see labels that are single characters
        if (op.length() == 1 && Character.isAlphabetic(op.charAt(0))) {
            return newLabelOperand(op);
        } else {
            return newValueOperand(Integer.parseInt(op));
        }
    }

    private static Operand newValueOperand(final Integer value) {
        return Operand.builder()
                .value(value)
                .build();
    }

    private static Operand newLabelOperand(final String label) {
        return Operand.builder()
                .label(label)
                .build();
    }
}
