package dev.davidson.ian.advent.year2024.day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintQueue {

    private static final String SAMPLE_PATH = "adventOfCode/2024/day05/sample.txt";
    private static final String REAL_PATH = "adventOfCode/2024/day05/real.txt";

    public static void main(String[] args) {

        Map<Integer, Set<Integer>> orderMap = new HashMap<>();
        List<List<Integer>> sequences = readFile(orderMap, REAL_PATH);

        PrintQueue printQueue = new PrintQueue();
        log.info("Part1: {}", printQueue.part1(orderMap, sequences));
        log.info("Part1: {}", printQueue.part2(orderMap, sequences));
    }

    private static List<List<Integer>> readFile(final Map<Integer, Set<Integer>> orderMap,
                                                final String fileName) {
        List<List<Integer>> sequences = new ArrayList<>();

        ClassLoader cl = PrintQueue.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(fileName)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            while (!line.isBlank()) {
                String[] splits = line.split("\\|");
                Integer from = Integer.parseInt(splits[0]);
                Integer to = Integer.parseInt(splits[1]);
                Set<Integer> toSet = orderMap.getOrDefault(from, new HashSet<>());
                toSet.add(to);
                orderMap.put(from, toSet);

                line = scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                sequences.add(Arrays.stream(nextLine.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
            }

        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read input");
        }

        return sequences;
    }

    public Integer part1(final Map<Integer, Set<Integer>> orderMap, final List<List<Integer>> sequences) {
        Integer validCount = 0;

        for (List<Integer> sequence : sequences) {
            if (valid(orderMap, sequence)) {
                validCount += sequence.get(sequence.size() / 2);
            }
        }

        return validCount;
    }

    public Integer part2(final Map<Integer, Set<Integer>> orderMap, final List<List<Integer>> sequences) {
        Integer invalidCount = 0;

        for (List<Integer> sequence : sequences) {
            if (!valid(orderMap, sequence)) {
                sequence.sort((a, b) -> {
                    if (orderMap.getOrDefault(a, new HashSet<>()).contains(b)) {
                        return -1;
                    } else if (orderMap.getOrDefault(b, new HashSet<>()).contains(a)) {
                        return 1;
                    } else {
                        return 0;
                    }
                });

                invalidCount += sequence.get(sequence.size() / 2);
            }
        }

        return invalidCount;
    }

    private boolean valid(final Map<Integer, Set<Integer>> orderMap, final List<Integer> sequence) {
        for (int i = 0; i < sequence.size() - 1; i++) {
            if (!orderMap.getOrDefault(sequence.get(i), new HashSet<>()).contains(sequence.get(i + 1))) {
                return false;
            }
        }

        return true;
    }
}
