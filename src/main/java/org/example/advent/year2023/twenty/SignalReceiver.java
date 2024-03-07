package org.example.advent.year2023.twenty;

import java.util.List;

public interface SignalReceiver {
    PULSE receiveSignal(String label, PULSE pulse);
    default Boolean sendsSignal(PULSE pulse){
        return true;
    }
    String getLabel();
    List<String> getOutputModules();
}
