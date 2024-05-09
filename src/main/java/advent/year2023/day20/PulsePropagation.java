package advent.year2023.day20;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

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

import static org.example.advent.year2023.day20.Broadcaster.BROADCASTER;

@Log
public class PulsePropagation {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day20/input-sample.txt";
    private static final String SAMPLE_INPUT_2_PATH = "adventOfCode/2023/day20/input-sample2.txt";
    private static final String INPUT_PATH = "adventOfCode/2023/day20/input.txt";
    private static final String FINAL_STATE = "sq";

    private List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = PulsePropagation.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
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

    private static Map<String, SignalReceiver> processInputs(List<String> inputLines, Map<String, List<String>> inputMap) {
        Map<String, SignalReceiver> labelMap = new HashMap<>();
        for (String inputLine : inputLines) {

            List<String> outputs = Arrays.stream(inputLine.substring(inputLine.indexOf("->") + 2).split(","))
                    .map(String::trim).toList();

            String label = inputLine.charAt(0) != 'b' ? inputLine.substring(1, inputLine.indexOf(" ")).trim() : BROADCASTER;

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

    private static void propagatePart1(Map<String, SignalReceiver> circuit, ChargeCount chargeCount) {
        Queue<PulseVector> queue = new LinkedList<>();
        queue.add(PulseVector.builder().signalReceiver(circuit.get(BROADCASTER)).pulse(PULSE.LOW).senderLabel("button").build());

        long highCharges = 0;
        long lowCharges = 0;

        while (!queue.isEmpty()) {
            PulseVector pulseVector = queue.remove();
            SignalReceiver signalReceiver = pulseVector.getSignalReceiver();
            PULSE pulse = pulseVector.getPulse();

            //count
            if (pulse.equals(PULSE.LOW)) {
                lowCharges++;
            } else {
                highCharges++;
            }

            //apply pulse
            if (signalReceiver.sendsSignal(pulse)) {
                PULSE next = signalReceiver.receiveSignal(pulseVector.getSenderLabel(), pulse);

                for (String label : signalReceiver.getOutputs()) {
                    if (circuit.get(label) == null) {
                        if (next.equals(PULSE.LOW)) {
                            lowCharges++;
                        } else {
                            highCharges++;
                        }
                    } else {
                        queue.add(PulseVector.builder()
                                .signalReceiver(circuit.get(label))
                                .pulse(next)
                                .senderLabel(signalReceiver.getLabel())
                                .build());
                    }
                }
            }
        }
//        log.info("h:" + highCharges + " l:" + lowCharges);
        chargeCount.add(lowCharges, highCharges);
    }

    private static void propagatePart2(Map<String, SignalReceiver> circuit, Map<String, List<Long>> finalInputsMap, int iteration) {
        Queue<PulseVector> queue = new LinkedList<>();
        queue.add(PulseVector.builder().signalReceiver(circuit.get(BROADCASTER)).pulse(PULSE.LOW).senderLabel("button").build());

        while (!queue.isEmpty()) {
            PulseVector pulseVector = queue.remove();
            SignalReceiver signalReceiver = pulseVector.getSignalReceiver();
            PULSE pulse = pulseVector.getPulse();

            //apply pulse
            if (signalReceiver.sendsSignal(pulse)) {
                PULSE next = signalReceiver.receiveSignal(pulseVector.getSenderLabel(), pulse);

                if (finalInputsMap.containsKey(signalReceiver.getLabel()) && next.equals(PULSE.HIGH)) {
                    if (finalInputsMap.get(signalReceiver.getLabel()).isEmpty()) {
                        finalInputsMap.get(signalReceiver.getLabel()).add((long) iteration);
                    }

                    for (String label : finalInputsMap.keySet()) {
                        if (finalInputsMap.get(label).isEmpty()) {
                            break;
                        } else {
                            return;
                        }

                    }
                }

                for (String label : signalReceiver.getOutputs()) {
                    if (circuit.get(label) != null) {
                        queue.add(PulseVector.builder()
                                .signalReceiver(circuit.get(label))
                                .pulse(next)
                                .senderLabel(signalReceiver.getLabel())
                                .build());
                    }
                }
            }
        }
    }


    public long part1() {
        List<String> inputs = readFile();
        Map<String, List<String>> inputMap = new HashMap<>();
        Map<String, SignalReceiver> receivers = processInputs(inputs, inputMap);
        ChargeCount chargeCount = ChargeCount.builder().build();
        for (int i = 0; i < 1000; i++) {
            propagatePart1(receivers, chargeCount);
        }

        return chargeCount.result();
    }


    public long part2() {
        List<String> inputs = readFile();
        Map<String, List<String>> inputMap = new HashMap<>();
        Map<String, SignalReceiver> receivers = processInputs(inputs, inputMap);

        List<String> sqInputs = inputMap.get(FINAL_STATE);
        Map<String, List<Long>> finalInputMap = new HashMap<>();
        sqInputs.forEach(input -> finalInputMap.put(input, new ArrayList<>()));

        for (int i = 1; i < 10000; i++) {
            propagatePart2(receivers, finalInputMap, i);
        }

        Long sum = 1L;

        for (String label : finalInputMap.keySet()) {
            System.out.println("For label: " + label + "; " + finalInputMap.get(label).get(0));
            sum *= finalInputMap.get(label).get(0);
        }


        return sum;
    }


    public static void main(String[] args) {
        PulsePropagation pulsePropagation = new PulsePropagation();
        Long result = pulsePropagation.part1();
        log.info("part1: " + result);

        result = pulsePropagation.part2();
        log.info("part2: " + result);
    }


    @AllArgsConstructor
    @Builder
    @Data
    public static class ChargeCount {
        @Builder.Default
        private long highCharges = 0L;

        @Builder.Default
        private long lowCharges = 0L;

        public void add(long low, long high) {
            this.lowCharges += low;
            this.highCharges += high;
        }

        public long result() {
            return lowCharges * highCharges;
        }
    }

}


