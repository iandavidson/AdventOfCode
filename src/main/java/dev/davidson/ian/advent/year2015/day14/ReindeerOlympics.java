package dev.davidson.ian.advent.year2015.day14;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ReindeerOlympics {

    private static final String INPUT_PATH = "adventOfCode/2015/day14/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day14/sample.txt";
    private static final Integer TOTAL_TIME = 2503;//1000;

    public static void main(String[] args) {
        ReindeerOlympics reindeerOlympics = new ReindeerOlympics();
        log.info("Part1: {}", reindeerOlympics.part1());
        log.info("Part2: {}", reindeerOlympics.part2());
    }

    public int part1() {
        List<Reindeer> reindeerList = readFile();
        int max = 0;

        for (Reindeer reindeer : reindeerList) {
            max = Math.max(max, calculateDistanceAtSeconds(reindeer, TOTAL_TIME));
        }
        return max;
    }

    public int part2() {
        List<Reindeer> reindeerList = readFile();

        int maxScore = 0;

        Map<Reindeer, Integer> map = new HashMap<>();
        //do it different this time
        for (int i = 1; i < TOTAL_TIME; i++) {
            int[] distances = new int[reindeerList.size()];
            int max = 0;
            List<Integer> leadIndex = new ArrayList<>();
            for (int j = 0; j < reindeerList.size(); j++) {
                distances[j] = calculateDistanceAtSeconds(reindeerList.get(j), i);
                if (distances[j] > max) {
                    leadIndex.clear();
                    leadIndex.add(j);
                    max = distances[j];
                } else if (distances[j] == max) {
                    leadIndex.add(j);
                }
            }

            for (Integer lead : leadIndex) {
                map.put(reindeerList.get(lead), map.getOrDefault(reindeerList.get(lead), 0) + 1);
            }
        }

        for (Integer value : map.values()) {
            maxScore = Math.max(maxScore, value);
        }

        return maxScore;

    }

    public int calculateDistanceAtSeconds(final Reindeer reindeer, final int seconds) {
        int period = reindeer.runTime() + reindeer.restTime();
        int cycles = seconds / period;
        int totalDistance = cycles * reindeer.runDistance() * reindeer.runTime();
        int remainder = seconds - cycles * period;
        if (remainder > reindeer.runTime()) {
            totalDistance += reindeer.runDistance() * reindeer.runTime();
        } else {
            totalDistance += reindeer.runDistance() * remainder;
        }

        return totalDistance;
    }

    private List<Reindeer> readFile() {
        List<Reindeer> reindeerList = new ArrayList<>();
        ClassLoader cl = ReindeerOlympics.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\s+");
                reindeerList.add(Reindeer.builder()
                        .name(parts[0].trim())
                        .runDistance(Integer.parseInt(parts[3]))
                        .runTime(Integer.parseInt(parts[6]))
                        .restTime(Integer.parseInt(parts[13])).build());
            }
//            Dancer can fly 37 km/s for 1 seconds, but then must rest for 36 seconds.
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        return reindeerList;
    }
}
