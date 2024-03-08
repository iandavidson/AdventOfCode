package org.example.advent.year2023.twenty;

import java.util.List;

public interface SignalReceiver {
    //could have used an abstract class here b/c all impls have label and output members;
    // could have promoted to abstract class
    PULSE receiveSignal(String label, PULSE pulse);
    default Boolean sendsSignal(PULSE pulse){
        return true;
    }
    String getLabel();
    List<String> getOutputs();
}
