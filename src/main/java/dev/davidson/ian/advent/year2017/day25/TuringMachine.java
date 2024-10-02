package dev.davidson.ian.advent.year2017.day25;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record TuringMachine(int totalSteps, Map<Character, MachineState> machineStates) {

    public static TuringMachine newTuringMachine(final List<String> lines) {
        Map<Character, MachineState> machineStateMap = new HashMap<>();

        int totalSteps = Integer.parseInt(lines.get(1).split("\\s+")[5]);

        for (int i = 2; i < lines.size(); i += 10) {
            List<String> temp = lines.subList(i + 1, i + 10);
            MachineState machineState = MachineState.newMachineState(temp);
            machineStateMap.put(machineState.label(), machineState);
        }

        return new TuringMachine(totalSteps, machineStateMap);
    }
}
