package dev.davidson.ian.advent.year2017.day25;

import java.util.ArrayList;
import java.util.List;

public record MachineState(
        Character label,
        List<Transition> transitions) {

    private static final Integer TRANSITION_WIDTH = 4;

    public static MachineState newMachineState(final List<String> inputLines) {

        String labelRaw = inputLines.getFirst();
        Character label = labelRaw.charAt(labelRaw.length() - 2);

        List<Transition> transitions = new ArrayList<>();

        transitions.add(Transition.newTransition(inputLines.subList(1, 5)));
        transitions.add(Transition.newTransition(inputLines.subList(5, 9)));

        return new MachineState(label, transitions);
    }
}
