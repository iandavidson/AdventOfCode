package dev.davidson.ian.advent.year2017.day16.dancer;

import java.util.ArrayList;
import java.util.List;

public record ExchangeStep(Integer index1, Integer index2, String rawInstruction) implements Dancer {

    @Override
    public List<Character> applyMove(final List<Character> sequence) {
        List<Character> newSequence = new ArrayList<>(sequence);

        char val1 = newSequence.get(index1);
        char val2 = newSequence.get(index2);

        newSequence.set(index1, val2);
        newSequence.set(index2, val1);

        return newSequence;
    }
}
