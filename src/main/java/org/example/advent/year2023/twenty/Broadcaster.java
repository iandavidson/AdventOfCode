package org.example.advent.year2023.twenty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Broadcaster implements SignalReceiver {
    @Builder.Default
    private final String label = "broadcaster";

    public static final String BROADCASTER = "broadcaster";

    @Builder.Default
    private final List<String> outputs = new ArrayList<>();

    @Override
    public PULSE receiveSignal(String label, PULSE pulse) {
        return PULSE.LOW;
    }

    @Override
    public List<String> getOutputModules() {
        return this.outputs;
    }
}
