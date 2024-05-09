package advent.year2023.day20;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Flipflop implements SignalReceiver {
    private final String label;

    @Builder.Default
    private List<String> outputs = new ArrayList<>();

    @Builder.Default
    private Boolean power = false;

    @Override
    public PULSE receiveSignal(String label, PULSE pulse) {
        if (pulse.equals(PULSE.LOW)) {
            if (power) {
                this.power = false;
                return PULSE.LOW;
            } else {
                this.power = true;
                return PULSE.HIGH;
            }
        }

        return null;
    }

    @Override
    public Boolean sendsSignal(PULSE pulse) {
        return pulse.equals(PULSE.LOW);
    }


    /*
    Flip-flop modules (prefix %) are either on or off; they are initially off. If a flip-flop module receives a high pulse,
    it is ignored and nothing happens. However, if a flip-flop module receives a low pulse, it flips between on and off.
    If it was off, it turns on and sends a high pulse. If it was on, it turns off and sends a low pulse.
     */
}
