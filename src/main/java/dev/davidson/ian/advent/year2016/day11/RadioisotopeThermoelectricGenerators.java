package dev.davidson.ian.advent.year2016.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RadioisotopeThermoelectricGenerators {

    private static final String INPUT_PATH = "adventOfCode/2016/day11/input.txt";
    private static final String INPUT_PART_2_PATH = "adventOfCode/2016/day11/input-part2.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2016/day11/sample.txt";

    public static void main(String[] args) {
        RadioisotopeThermoelectricGenerators rtg = new RadioisotopeThermoelectricGenerators();
        log.info("Part1: {}", rtg.part1());
    }

    public Integer part1() {
        Factory startFactory = readFile();

        Set<Factory> visited = new HashSet<>();
        Queue<Factory> queue = new LinkedList<>();
        queue.add(startFactory);
        int stepsTaken = 0;

        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Factory current = queue.remove();

                if (visited.contains(current)) {
                    continue;
                }

                if (current.isFinished()) {
                    return stepsTaken;
                }

                List<Factory> validMoves = findValidMoves(current);
                for(Factory validMove : validMoves){
                    if(!visited.contains(validMove)){
                        queue.add(validMove);
                    }
                }


                visited.add(current);
            }
            stepsTaken++;
        }

        return 0;
    }

    private List<Factory> findValidMoves(final Factory current) {
        List<Factory> validMoves = new ArrayList<>();
        validMoves.addAll(findValidMovesAtFloor(current, current.getCurrentFloor() + 1));
        validMoves.addAll(findValidMovesAtFloor(current, current.getCurrentFloor() - 1));
        return validMoves;
    }

    private List<Factory> findValidMovesAtFloor(final Factory current, final int proposedFloor) {
        if (proposedFloor == 0 || proposedFloor == 5) {
            return Collections.emptyList();
        }

        List<Factory> factories = new ArrayList<>();

        List<String> chipCandidates = current.availableChips();
        List<String> genCandidates = current.availableGenerators();
        for (String chipCandidate : chipCandidates) {
            factories.add(current.makeMove(proposedFloor, List.of(chipCandidate)));
        }

        for (String genCandidate : genCandidates) {
            factories.add(current.makeMove(proposedFloor, List.of(genCandidate)));
        }

        int n = current.getCurrentFloorItems().size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                factories.add(
                        current.makeMove(
                                proposedFloor,
                                List.of(current.getCurrentFloorItems().get(i), current.getCurrentFloorItems().get(j))));
            }
        }

        return factories.stream().filter(Factory::isValid).toList();
    }

    private Factory readFile() {
        List<String> inputLines = new ArrayList<>();

        ClassLoader cl = RadioisotopeThermoelectricGenerators.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PART_2_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return Factory.newFactory(inputLines);
    }

}
