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
                queue.addAll(validMoves);

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
        for (String chipCandidate : chipCandidates) {
            //no generators exist at proposedFloor
            //1 or more exist at proposedFloor and one matches name of chipCandidate
            if (current.getGenMap().get(proposedFloor).isEmpty() || current.getGenMap().get(proposedFloor).contains(chipCandidate)) {
                factories.add(current.makeMove(proposedFloor, List.of(chipCandidate), Collections.emptyList()));
            }
        }

        List<String> genCandidates = current.availableGenerators();
        for (String genCandidate : genCandidates) {
            if(current.getChipMap().get(proposedFloor).isEmpty() || current.getChipMap().get(proposedFloor).contains(genCandidate)) {
                factories.add(current.makeMove(proposedFloor, Collections.emptyList(), List.of(genCandidate)));
            }
        }

        //all combinations of 2

        /*
        List<String> chip: a, b
        List<String> generators : c, d

        combos [a,b],[a,c],[a,d],[b,c],[b,d],[c,d]

         */



    }

    private Factory readFile() {
        List<String> inputLines = new ArrayList<>();

        ClassLoader cl = RadioisotopeThermoelectricGenerators.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
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
