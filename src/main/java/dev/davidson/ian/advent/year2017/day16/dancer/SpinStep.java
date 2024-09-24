package dev.davidson.ian.advent.year2017.day16.dancer;

import java.util.ArrayList;
import java.util.List;

public record SpinStep(Integer width, String rawInstruction) implements Dancer {

    @Override
    public List<Character> applyMove(final List<Character> sequence) {
        List<Character> newStart = sequence.subList(sequence.size() - width, sequence.size());
        List<Character> newEnd = sequence.subList(0, sequence.size() - width);

        List<Character> newSequence = new ArrayList<>(newStart);
        newSequence.addAll(newEnd);

        return newSequence;
    }
}
