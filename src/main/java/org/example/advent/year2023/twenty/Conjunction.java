package org.example.advent.year2023.twenty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Conjunction implements SignalReceiver {
    private final String label;

    @Builder.Default
    private List<SignalReceiver> outputs = new ArrayList<>();

    @Builder.Default
    private Map<String, PULSE> history = new HashMap<>();

    @Override
    public PULSE receiveSignal(PULSE pulse) {
        return null;
    }

    @Override
    public List<SignalReceiver> getOutputModules() {
        return this.outputs;
    }
        /*
        Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their connected input modules;
        they initially default to remembering a low pulse for each input. When a pulse is received, the conjunction module first updates its memory for that input.
        Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
         */
}
