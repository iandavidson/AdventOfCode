package dev.davidson.ian.advent.year2023.day20;

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
    private List<String> outputs = new ArrayList<>();

    @Builder.Default
    private Map<String, PULSE> inputs = new HashMap<>();

    @Override
    public PULSE receiveSignal(String label, PULSE pulse) {
        inputs.put(label, pulse);

        for(Map.Entry<String, PULSE> entry : inputs.entrySet()){
            if(entry.getValue().equals(PULSE.LOW)){
                return PULSE.HIGH;
            }
        }

        return PULSE.LOW;
    }

    public static Map<String, PULSE> toInputMap(List<String> inputs){
        Map<String, PULSE> inputMap = new HashMap<>();
        for(String input : inputs){
            inputMap.put(input, PULSE.LOW);
        }
        return inputMap;
    }
        /*
        Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their connected input modules;
        they initially default to remembering a low pulse for each input. When a pulse is received, the conjunction module first updates its memory for that input.
        Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
         */
}
