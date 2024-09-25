package dev.davidson.ian.advent.year2017.day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Duet {

    private static final String INPUT_PATH = "adventOfCode/2017/day18/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day18/sample.txt";
    private static final String MINI_PATH = "adventOfCode/2017/day18/mini.txt";

    public static void main(String[] args) {
        Duet duet = new Duet();
        List<Instruction> miniInstructions = readFile(MINI_PATH);
        log.info("Using mini input");
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
//            log.info("index:  {}", index);
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


    public Long part2(final List<Instruction> instructions) {
        ProgramState programState0 = new ProgramState(
                new HashMap<>(),
                0,
                new LinkedList<>());
        programState0.getRegisterMap().put('p', 0L);

        ProgramState programState1 = new ProgramState(
                new HashMap<>(),
                0,
                new LinkedList<>()
        );
        programState1.getRegisterMap().put('p', 1L);

        Map<Integer, ProgramState> programStates = Map.of(
                0, programState0,
                1, programState1);

        long program1SentCount = 0L;
        while (programState0.getIndex() < instructions.size() &&
                programState1.getIndex() < instructions.size()) {

            Instruction program0Instruction = instructions.get(programState0.getIndex());
            Instruction program1Instruction = instructions.get(programState1.getIndex());

            if (program0Instruction.instructionType() == InstructionType.rcv &&
                    programState0.getQueue().isEmpty() &&
                    program1Instruction.instructionType() == InstructionType.rcv &&
                    programState1.getQueue().isEmpty()) {
                return program1SentCount;
            }

            switch (program0Instruction.instructionType()) {
                case snd -> {
                    programState1.addToQueue(toValue(programState0.getRegisterMap(),
                            program0Instruction.operands().getFirst()));
                    programState0.bumpIndex();
                }
                case set -> {
                    Character label = program0Instruction.operands().getFirst().charOp();
                    programState0.getRegisterMap().putIfAbsent(label, 0L);
                    programState0.getRegisterMap().put(label, toValue(
                            programState0.getRegisterMap(), program0Instruction.operands().get(1)
                    ));
                    programState0.bumpIndex();
                }
                case add -> {
                    Character label = program0Instruction.operands().getFirst().charOp();
                    programState0.getRegisterMap().putIfAbsent(label, 0L);
                    programState0.getRegisterMap().put(label,
                            programState0.getRegisterMap().get(label) +
                                    toValue(programState0.getRegisterMap(), program0Instruction.operands().get(1)));
                    programState0.bumpIndex();
                }
                case mul -> {
                    Character label = program0Instruction.operands().getFirst().charOp();
                    programState0.getRegisterMap().putIfAbsent(label, 0L);
                    programState0.getRegisterMap().put(label,
                            programState0.getRegisterMap().get(label) *
                                    toValue(programState0.getRegisterMap(), program0Instruction.operands().get(1)));
                    programState0.bumpIndex();
                }
                case mod -> {
                    Character label = program0Instruction.operands().getFirst().charOp();
                    programState0.getRegisterMap().putIfAbsent(label, 0L);
                    programState0.getRegisterMap().put(label,
                            programState0.getRegisterMap().get(label) %
                                    toValue(programState0.getRegisterMap(), program0Instruction.operands().get(1)));
                    programState0.bumpIndex();
                }
                case rcv -> {
                    if (!programState0.getQueue().isEmpty()) {
                        Character label = program0Instruction.operands().getFirst().charOp();
                        programState0.getRegisterMap().put(label, programState0.getQueue().remove());
                        programState0.bumpIndex();
                    }
                }
                case jgz -> {
                    if (toValue(programState0.getRegisterMap(), program0Instruction.operands().getFirst()) > 0) {
                        programState0.setIndex((int) (programState0.getIndex() +
                                toValue(programState0.getRegisterMap(), program0Instruction.operands().get(1))));
                    } else {
                        programState0.bumpIndex();
                    }
                }
            }

            switch (program1Instruction.instructionType()) {
                case snd -> {
                    programState0.addToQueue(
                            toValue(programState1.getRegisterMap(), program1Instruction.operands().getFirst()));
                    programState1.bumpIndex();
                    program1SentCount++;
                }
                case set -> {
                    Character label = program1Instruction.operands().getFirst().charOp();
                    programState1.getRegisterMap().putIfAbsent(label, 0L);
                    programState1.getRegisterMap().put(label, toValue(
                            programState1.getRegisterMap(), program1Instruction.operands().get(1)
                    ));
                    programState1.bumpIndex();
                }
                case add -> {
                    Character label = program1Instruction.operands().getFirst().charOp();
                    programState1.getRegisterMap().putIfAbsent(label, 0L);
                    programState1.getRegisterMap().put(label,
                            programState1.getRegisterMap().get(label) +
                                    toValue(programState1.getRegisterMap(), program1Instruction.operands().get(1)));
                    programState1.bumpIndex();
                }
                case mul -> {
                    Character label = program1Instruction.operands().getFirst().charOp();
                    programState1.getRegisterMap().putIfAbsent(label, 0L);
                    programState1.getRegisterMap().put(label,
                            programState1.getRegisterMap().get(label) *
                                    toValue(programState1.getRegisterMap(), program1Instruction.operands().get(1)));
                    programState1.bumpIndex();
                }
                case mod -> {
                    Character label = program1Instruction.operands().getFirst().charOp();
                    programState1.getRegisterMap().putIfAbsent(label, 0L);
                    programState1.getRegisterMap().put(label,
                            programState1.getRegisterMap().get(label) %
                                    toValue(programState1.getRegisterMap(), program1Instruction.operands().get(1)));
                    programState1.bumpIndex();
                }
                case rcv -> {
                    if (!programState1.getQueue().isEmpty()) {
                        Character label = program1Instruction.operands().getFirst().charOp();
                        programState1.getRegisterMap().put(label, programState1.getQueue().remove());
                        programState1.bumpIndex();
                    }

                }
                case jgz -> {
                    if (toValue(programState1.getRegisterMap(), program1Instruction.operands().getFirst()) > 0) {
                        /*
                         programState0.setIndex((int) (programState0.getIndex() +
                                toValue(programState0.getRegisterMap(), program0Instruction.operands().get(1))));
                         */
                        programState1.setIndex((int) (programState1.getIndex() +
                                toValue(programState1.getRegisterMap(), program1Instruction.operands().get(1))));
                    } else {
                        programState1.bumpIndex();
                    }
                }
            }

        }

        throw new IllegalStateException("Shouldn't have exited instruction");
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
