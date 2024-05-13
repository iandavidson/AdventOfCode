package dev.davidson.ian.advent.year2023.day20;

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
        return pulse;
    }
}
