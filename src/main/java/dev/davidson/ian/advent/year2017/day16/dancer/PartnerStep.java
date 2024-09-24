package dev.davidson.ian.advent.year2017.day16.dancer;

import java.util.ArrayList;
import java.util.List;

public record PartnerStep(Character label1, Character label2, String rawInstruction) implements Dancer {

    @Override
    public List<Character> applyMove(final List<Character> sequence) {
        List<Character> newSequence = new ArrayList<>(sequence);

        int index1 = newSequence.indexOf(label1);
        int index2 = newSequence.indexOf(label2);

        newSequence.set(index1, label2);
        newSequence.set(index2, label1);

        return newSequence;
    }
}
