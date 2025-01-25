package dev.davidson.ian.advent.year2024.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MullItOver {

    private static final String SAMPLE_PATH = "adventOfCode/2024/day03/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2024/day03/real.txt";

    private static final String PART2_REPLACE = "don't\\(\\).*?(do\\(\\)|$)";

    private static final Pattern PATTERN = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    public static void main(String[] args) {
        MullItOver mullItOver = new MullItOver();
        List<String> sampleLines = readFile(SAMPLE_PATH);
        log.info("Sample Part1: {}", mullItOver.part1(sampleLines));
        log.info("Sample Part2: {}", mullItOver.part2(sampleLines));

        List<String> inputLines = readFile(INPUT_PATH);
        log.info("Real Part1: {}", mullItOver.part1(inputLines));
        log.info("Real Part2: {}", mullItOver.part2(inputLines));
    }

    private static List<String> readFile(final String filePath) {
        List<String> inputLines = new ArrayList<>();

        ClassLoader cl = MullItOver.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return inputLines;
    }

    public long part1(final List<String> corruptedLines) {
        long runningSum = 0L;

        for (String line : corruptedLines) {
            runningSum += part1(line);
        }

        return runningSum;
    }

    private long part1(final String line) {
        return PATTERN.matcher(line)
                .results()
                .mapToLong(match -> Long.parseLong(match.group(1)) * Long.parseLong(match.group(2)))
                .sum();
    }

    public long part2(final List<String> corruptedLines) {
        return part1(String.join("", corruptedLines).replaceAll(PART2_REPLACE, "#"));
    }
}
