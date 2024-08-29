package dev.davidson.ian.advent.year2016.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        log.info("Part1: {}", rtg.execute(INPUT_PATH));
        log.info("Part2: {}", rtg.execute(INPUT_PART_2_PATH));
    }

    public Integer execute(String filePath) {
        Factory startFactory = readFile(filePath);

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

                List<Factory> validMoves = current.findValidMoves(current);
                for (Factory validMove : validMoves) {
                    if (!visited.contains(validMove)) {
                        queue.add(validMove);
                    }
                }

                visited.add(current);
            }
            stepsTaken++;
        }

        return 0;
    }

    private Factory readFile(final String filePath) {
        List<String> inputLines = new ArrayList<>();

        ClassLoader cl = RadioisotopeThermoelectricGenerators.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
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
