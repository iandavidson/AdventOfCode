package dev.davidson.ian.advent.year2017.day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HaltingProblem {

    private static final String INPUT_PATH = "adventOfCode/2017/day25/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day25/sample.txt";


    public static void main(String[] args) {
        HaltingProblem haltingProblem = new HaltingProblem();
        TuringMachine turingMachine = readFile(INPUT_PATH);
        log.info("Part1: {}", haltingProblem.part1(turingMachine));
    }

    private static TuringMachine readFile(final String filePath) {
        List<String> inputLines = new ArrayList<>();

        ClassLoader cl = HaltingProblem.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return TuringMachine.newTuringMachine(inputLines);
    }

    public Integer part1(final TuringMachine turingMachine) {
        Map<Integer, Integer> tape = new HashMap<>();

        Integer tapeIndex = 0;
        Character currentState = 'A';
        for (int i = 0; i < turingMachine.totalSteps(); i++) {
            Integer entry = tape.getOrDefault(tapeIndex, 0);
            MachineState machineState = turingMachine.machineStates().get(currentState);

            Transition transition = machineState.transitions().get(entry);
            currentState = transition.nextState();
            tape.put(tapeIndex, transition.writeValue());
            tapeIndex += transition.tapeMovement();
        }

        return Math.toIntExact(tape.values().stream().filter(value -> value == 1).count());
    }
}
