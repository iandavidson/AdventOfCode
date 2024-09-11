package dev.davidson.ian.advent.year2017.day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HighEntropyPassphrases {

    private static final String INPUT_PATH = "adventOfCode/2017/day04/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day04/sample.txt";

    public static void main(String[] args) {
        HighEntropyPassphrases highEntropyPassphrases = new HighEntropyPassphrases();
        List<String> inputs = readFile(INPUT_PATH);
        log.info("Part1: {}", highEntropyPassphrases.execute(inputs, true));
        log.info("Part2: {}", highEntropyPassphrases.execute(inputs, false));
    }

    private static List<String> readFile(final String filePath) {
        List<String> inputs = new ArrayList<>();

        ClassLoader cl = HighEntropyPassphrases.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputs.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return inputs;
    }

    public Integer execute(final List<String> inputs, final boolean part1) {
        int count = 0;

        for (String line : inputs) {

            List<String> subsequences = part1
                    ? Arrays.stream(line.split("\\s+")).toList()
                    : Arrays.stream(line.split("\\s+")).map(this::convertToCountEncoding).toList();

            Set<String> unique = new HashSet<>(subsequences);
            if (unique.size() == subsequences.size()) {
                count++;
            }
        }

        return count;
    }

    private String convertToCountEncoding(final String word) {
        char[] counts = new char[26];
        for (char ch : word.toCharArray()) {
            counts[ch - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            sb.append(counts[i]);
        }

        return sb.toString();
    }
}
