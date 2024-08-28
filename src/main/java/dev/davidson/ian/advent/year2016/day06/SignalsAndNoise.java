package dev.davidson.ian.advent.year2016.day06;

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
public class SignalsAndNoise {

    private static final String INPUT_PATH = "adventOfCode/2016/day06/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2016/day06/sample.txt";

    public static void main(String[] args) {
        SignalsAndNoise signalsAndNoise = new SignalsAndNoise();
        signalsAndNoise.execute();
    }

    public void execute() {
        List<String> words = readFile();
        List<Map<Character, Integer>> positionCounts = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            positionCounts.add(new HashMap<>());
        }

        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                positionCounts.get(i).put(word.charAt(i), positionCounts.get(i).getOrDefault(word.charAt(i), 0) + 1);
            }
        }

        StringBuilder result1 = new StringBuilder();
        StringBuilder result2 = new StringBuilder();
        for (Map<Character, Integer> countMap : positionCounts) {
            int count1 = Integer.MIN_VALUE;
            Character found1 = '~';

            int count2 = Integer.MAX_VALUE;
            Character found2 = '~';

            for (Character key : countMap.keySet()) {
                if (countMap.get(key) > count1) {
                    count1 = countMap.get(key);
                    found1 = key;
                }

                if (countMap.get(key) < count2) {
                    count2 = countMap.get(key);
                    found2 = key;
                }
            }

            result1.append(found1);
            result2.append(found2);
        }

        log.info("Part1: {}", result1);
        log.info("Part2: {}", result2);
    }

    private List<String> readFile() {
        List<String> words = new ArrayList<>();

        ClassLoader cl = SignalsAndNoise.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file");
        }

        return words;
    }
}
