package org.example.advent.year2023.twenty;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

import static org.example.advent.year2023.twenty.Broadcaster.BROADCASTER;

@Log
public class PulsePropagation {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day20/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day20/input.txt";

    private List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = PulsePropagation.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(SAMPLE_INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    private static Map<String, SignalReceiver> processInputs(List<String> inputLines) {
        Map<String, SignalReceiver> labelMap = new HashMap<>();
        Map<String, List<String>> inputMap = new HashMap<>();
        for (String inputLine : inputLines) {

            List<String> outputs = Arrays.stream(inputLine.substring(inputLine.indexOf("->") + 2).split(","))
                    .map(String::trim).toList();

            String label = inputLine.charAt(0) != 'b' ? inputLine.substring(1, 3) : BROADCASTER;

            for (String output : outputs) {
                if (!inputMap.containsKey(output)) {
                    List<String> inputs = new ArrayList<>();
                    inputs.add(label);
                    inputMap.put(output, inputs);
                } else {
                    inputMap.get(output).add(label);
                }
            }

            if (inputLine.charAt(0) == '%') {
                labelMap.put(label, Flipflop.builder()
                        .label(label)
                        .outputs(outputs)
                        .build());

            } else if (inputLine.charAt(0) == '&') {
                labelMap.put(label, Conjunction.builder()
                        .label(label)
                        .outputs(outputs)
                        .build());

            } else {
                labelMap.put(label, Broadcaster.builder()
                        .outputs(outputs).build());
            }
        }

        for (SignalReceiver signalReceiver : labelMap.values()) {
            if (signalReceiver instanceof Conjunction conjunction) {
                //law of demeter 🥸
                conjunction.setInputs(Conjunction.toInputMap(Objects.requireNonNull(inputMap.get(conjunction.getLabel()))));
            }
        }

        return labelMap;
    }

    private static Long propagate(Map<String, SignalReceiver> circuit) {
        Queue<PulseVector> queue = new LinkedList<>();
        queue.add(PulseVector.builder().signalReceiver(circuit.get(BROADCASTER)).pulse(PULSE.LOW).build());

        long highCharges = 0;
        long lowCharges = 0;

        while (!queue.isEmpty()) {
            PulseVector pulseVector = queue.remove();
            SignalReceiver signalReceiver = pulseVector.getSignalReceiver();
            PULSE pulse = pulseVector.getPulse();
            if (pulse.equals(PULSE.LOW)) {
                lowCharges++;
            } else {
                highCharges++;
            }

            //apply pulse
            if (signalReceiver.sendsSignal(pulse)) {
                PULSE next = signalReceiver.receiveSignal(pulseVector.getSenderLabel(), pulse);
                for (String label : pulseVector.getSignalReceiver().getOutputModules()) {
                    queue.add(PulseVector.builder().signalReceiver(circuit.get(label)).pulse(next).senderLabel(signalReceiver.getLabel()).build());
                }
            }
        }
        log.info("high:" + highCharges + " low:" + lowCharges);
        return highCharges * lowCharges * 1000;
    }


    public long part1() {
        List<String> inputs = readFile();
        Map<String, SignalReceiver> receivers = processInputs(inputs);
        return propagate(receivers);
    }


    public static void main(String[] args) {
        PulsePropagation pulsePropagation = new PulsePropagation();
        Long result = pulsePropagation.part1();
        log.info("part1: " + result);
    }


}


