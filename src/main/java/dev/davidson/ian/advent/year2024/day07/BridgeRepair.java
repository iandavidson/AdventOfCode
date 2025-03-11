package dev.davidson.ian.advent.year2024.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BridgeRepair {

    private static final String SAMPLE_INPUT = "adventOfCode/2024/day07/sample.txt";
    private static final String REAL_INPUT = "adventOfCode/2024/day07/real.txt";

    public static void main(String[] args) {
        Map<Long, List<Long>> sampleMap = readFile(SAMPLE_INPUT);
        Map<Long, List<Long>> realMap = readFile(REAL_INPUT);

        BridgeRepair bridgeRepair = new BridgeRepair();
        log.info("Part1 sample input: {}", bridgeRepair.part1(sampleMap));
        log.info("Part1 real input: {}", bridgeRepair.part1(realMap));

        log.info("Part2 sample input: {}", bridgeRepair.part2(sampleMap));
        log.info("Part2 real input: {}", bridgeRepair.part2(realMap));
    }

    private static Map<Long, List<Long>> readFile(final String filePath) {
        Map<Long, List<Long>> inputMap = new HashMap<>();

        ClassLoader cl = BridgeRepair.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                Long key = Long.parseLong(parts[0]);
                List<Long> values = Arrays.stream(parts[1].trim().split("\\s+")).map(Long::parseLong).toList();
                inputMap.put(key, values);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read input");
        }

        return inputMap;
    }

    public Long part1(final Map<Long, List<Long>> inputMap) {
        Long runningSum = 0L;

        for (Long key : inputMap.keySet()) {

            List<Long> values = inputMap.get(key);
            Set<Long> runningResults = Set.of(values.getFirst());

            for (int i = 1; i < values.size(); i++) {
                Set<Long> nextRound = new HashSet<>();

                for (Long runningResult : runningResults) {
                    //add
                    nextRound.add(runningResult + values.get(i));

                    //mult
                    nextRound.add(runningResult * values.get(i));
                }

                runningResults = nextRound;
            }

            if (runningResults.contains(key)) {
                runningSum += key;
            }
        }

        return runningSum;
    }

    public Long part2(final Map<Long, List<Long>> inputMap) {
        Long runningSum = 0L;

        for (Long key : inputMap.keySet()) {

            List<Long> values = inputMap.get(key);
            Set<Long> runningResults = Set.of(values.getFirst());

            for (int i = 1; i < values.size(); i++) {
                Set<Long> nextRound = new HashSet<>();

                for (Long runningResult : runningResults) {
                    //add
                    nextRound.add(runningResult + values.get(i));

                    //mult
                    nextRound.add(runningResult * values.get(i));

                    //concat
                    nextRound.add(Long.parseLong(runningResult + values.get(i).toString()));
                }

                runningResults = nextRound;
            }

            if (runningResults.contains(key)) {
                runningSum += key;
            }
        }

        return runningSum;
    }
}
