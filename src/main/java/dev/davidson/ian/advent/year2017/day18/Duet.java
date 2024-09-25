package dev.davidson.ian.advent.year2017.day18;

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
public class Duet {

    private static final String INPUT_PATH = "adventOfCode/2017/day18/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day18/sample.txt";
    private static final String PART2_SAMPLE_PATH = "adventOfCode/2017/day18/part2sample.txt";

    public static void main(String[] args) {
        Duet duet = new Duet();
        List<Instruction> miniInstructions = readFile(PART2_SAMPLE_PATH);
        log.info("Using part2 sample input");
        log.info("Part1: {}", duet.part1(miniInstructions));
        log.info("Part2: {}", duet.part2(miniInstructions));

        List<Instruction> sampleInstructions = readFile(SAMPLE_PATH);
        log.info("Using sample input");
        log.info("Part1: {}", duet.part1(sampleInstructions));
        log.info("Part2: {}", duet.part2(sampleInstructions));

        List<Instruction> realInstructions = readFile(INPUT_PATH);
        log.info("Using real input");
        log.info("Part1: {}", duet.part1(realInstructions));
        log.info("Part2: {}", duet.part2(realInstructions));
    }

    private static List<Instruction> readFile(final String filePath) {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = Duet.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                instructions.add(Instruction.newInstruction(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return instructions;
    }

    public Long part1(final List<Instruction> instructions) {
        Map<Character, Long> registerMap = new HashMap<>();
        Long mostRecentlyPlayedSong = 0L;
        int index = 0;
        while (index < instructions.size()) {
            Instruction instruction = instructions.get(index);
            switch (instruction.instructionType()) {
                case snd -> {
                    mostRecentlyPlayedSong = toValue(registerMap, instruction.operands().getFirst());
                    index++;
                }
                case set -> {
                    Character label = instruction.operands().getFirst().charOp();
                    registerMap.putIfAbsent(label, 0L);
                    registerMap.put(label, toValue(
                            registerMap, instruction.operands().get(1)
                    ));
                    index++;
                }
                case add -> {
                    Character label = instruction.operands().getFirst().charOp();
                    registerMap.putIfAbsent(label, 0L);
                    registerMap.put(label,
                            registerMap.get(label) +
                                    toValue(registerMap, instruction.operands().get(1)));
                    index++;
                }
                case mul -> {
                    Character label = instruction.operands().getFirst().charOp();
                    registerMap.putIfAbsent(label, 0L);
                    registerMap.put(label,
                            registerMap.get(label) *
                                    toValue(registerMap, instruction.operands().get(1)));
                    index++;
                }
                case mod -> {
                    Character label = instruction.operands().getFirst().charOp();
                    registerMap.putIfAbsent(label, 0L);
                    registerMap.put(label,
                            registerMap.get(label) %
                                    toValue(registerMap, instruction.operands().get(1)));
                    index++;
                }
                case rcv -> {
                    return mostRecentlyPlayedSong;
                }
                case jgz -> {
                    if (toValue(registerMap, instruction.operands().getFirst()) > 0) {
                        index += toValue(registerMap, instruction.operands().get(1));
                    } else {
                        index++;
                    }
                }
            }
        }

        throw new IllegalStateException("Failed to terminate on valid rcv instruction");
    }


    public Integer part2(final List<Instruction> instructions) {
        ProgramState state0 = new ProgramState(0L);
        ProgramState state1 = new ProgramState(1L);

        while (state0.getIndex() < instructions.size() &&
                state1.getIndex() < instructions.size()) {

            Instruction instruction0 = instructions.get(state0.getIndex());
            Instruction instruction1 = instructions.get(state1.getIndex());

            //check for exit condition
            if (instruction0.instructionType() == InstructionType.rcv &&
                    state0.getQueue().isEmpty() &&
                    instruction1.instructionType() == InstructionType.rcv &&
                    state1.getQueue().isEmpty()) {
                return state1.getSendCount();
            }

            executeInstruction(state0, state1, instruction0);
            executeInstruction(state1, state0, instruction1);
        }

        throw new IllegalStateException("Shouldn't have exited instruction list");
    }

    private void executeInstruction(final ProgramState current, final ProgramState other,
                                    final Instruction instruction) {
        switch (instruction.instructionType()) {
            case snd -> {
                other.addToQueue(toValue(current.getMap(),
                        instruction.operands().getFirst()));
                current.bumpIndex();
                current.bumpSendCount();
            }
            case set -> {
                Character label = instruction.operands().getFirst().charOp();
                current.getMap().putIfAbsent(label, 0L);
                current.getMap().put(label, toValue(
                        current.getMap(), instruction.operands().get(1)
                ));
                current.bumpIndex();
            }
            case add -> {
                Character label = instruction.operands().getFirst().charOp();
                current.getMap().putIfAbsent(label, 0L);
                current.getMap().put(label,
                        current.getMap().get(label) +
                                toValue(current.getMap(), instruction.operands().get(1)));
                current.bumpIndex();
            }
            case mul -> {
                Character label = instruction.operands().getFirst().charOp();
                current.getMap().putIfAbsent(label, 0L);
                current.getMap().put(label,
                        current.getMap().get(label) *
                                toValue(current.getMap(), instruction.operands().get(1)));
                current.bumpIndex();
            }
            case mod -> {
                Character label = instruction.operands().getFirst().charOp();
                current.getMap().putIfAbsent(label, 0L);
                current.getMap().put(label,
                        current.getMap().get(label) %
                                toValue(current.getMap(), instruction.operands().get(1)));
                current.bumpIndex();
            }
            case rcv -> {
                if (!current.getQueue().isEmpty()) {
                    Character label = instruction.operands().getFirst().charOp();
                    current.getMap().put(label, current.getQueue().remove());
                    current.bumpIndex();
                }
            }
            case jgz -> {
                if (toValue(current.getMap(), instruction.operands().getFirst()) > 0) {
                    current.setIndex((int) (current.getIndex() +
                            toValue(current.getMap(), instruction.operands().get(1))));
                } else {
                    current.bumpIndex();
                }
            }
        }
    }

    private Long toValue(final Map<Character, Long> map, final Operand operand) {
        if (operand.numOp() != null) {
            return operand.numOp();
        } else if (!map.containsKey(operand.charOp())) {
            map.put(operand.charOp(), 0L);
        }

        return map.get(operand.charOp());
    }
}
