package org.example.advent.year2023.twenty;

import java.util.List;

public interface SignalReceiver {
    PULSE receiveSignal(PULSE pulse);
    default Boolean sendsSignal(PULSE pulse){
        return true;
    }
    List<SignalReceiver> getOutputModules();
}
