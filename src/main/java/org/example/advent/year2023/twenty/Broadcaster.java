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

    @Builder.Default
    private final List<SignalReceiver> outputs = new ArrayList<>();

    @Override
    public PULSE receiveSignal(PULSE pulse) {
        return PULSE.LOW;
    }

    @Override
    public List<SignalReceiver> getOutputModules() {
        return this.outputs;
    }
}
