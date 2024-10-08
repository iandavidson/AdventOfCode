package dev.davidson.ian.advent.year2016.day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OneTimePad {

    private static final Pattern THREE_IN_A_ROW = Pattern.compile("(\\w)\\1{2}");
    private static final String SAMPLE_PATH = "adventOfCode/2016/day14/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day14/input.txt";

    public static void main(String[] args) {
        OneTimePad oneTimePad = new OneTimePad();
        log.info("Part1: {}", oneTimePad.part1());
        log.info("Part2: {}", oneTimePad.part2());
    }

    public Integer part1() {
        String key = readFile();

        int keyCount = 0;
        int indexAtMostRecent = -1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String tempKey = key + i;
            String hex = Hasher.encodeToHex(tempKey, 1);

            Matcher matcher = THREE_IN_A_ROW.matcher(hex);
            if (matcher.find()) {
                String repeated = matcher.group();
                String fiveMatch = repeated + repeated.substring(1);

                for (int j = i + 1; j < i + 1001; j++) {
                    String complement = Hasher.encodeToHex(key + j, 1);
                    if (complement.contains(fiveMatch)) {
                        indexAtMostRecent = i;
                        keyCount++;
                        break;
                    }
                }

            }
            if (keyCount == 64) {
                break;
            }
        }

        return indexAtMostRecent;
    }

    public Integer part2() {
        //SHOULD BE HASHING 1 time then 2016 more times, update to 2017
        String key = readFile();

        Map<Integer, String> cache = new HashMap<>();

        int keyCount = 0;
        int indexAtMostRecent = -1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String tempKey = key + i;
            String hex;

            if (cache.containsKey(i)) {
                hex = cache.get(i);
            } else {
                hex = Hasher.encodeToHex(tempKey, 2017);
                cache.put(i, hex);
            }

            Matcher matcher = THREE_IN_A_ROW.matcher(hex);
            if (matcher.find()) {
                String repeated = matcher.group();
                String fiveMatch = repeated + repeated.substring(1);

                for (int j = i + 1; j < i + 1001; j++) {
                    String complement;
                    if (cache.containsKey(j)) {
                        complement = cache.get(j);
                    } else {
                        complement = Hasher.encodeToHex(key + j, 2017);
                        cache.put(j, complement);
                    }

                    if (complement.contains(fiveMatch)) {
                        indexAtMostRecent = i;
                        keyCount++;
                        log.info("keys found: {}", keyCount);
                        break;
                    }
                }

            }
            if (keyCount == 64) {
                break;
            }
        }

        return indexAtMostRecent;
    }

    private String readFile() {
        ClassLoader cl = OneTimePad.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine().trim();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path.");
        }
    }
}
