package dev.davidson.ian.advent.year2017.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuelingGenerators {

    private static final String INPUT_PATH = "adventOfCode/2017/day15/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day15/sample.txt";
    private static final Map<String, Long> MULT_MAP = Map.of(
            "A", 16807L,
            "B", 48271L
    );

    private static final Map<String, Long> FACTOR_MAP = Map.of(
            "A", 4L,
            "B", 8L
    );

    private static final Integer BIT_MASK_16 = 65535;

    private static final Integer PART_1_LENGTH = 40_000_000;
    private static final Integer PART_2_LENGTH = 5_000_000;


    public static void main(String[] args) {
        DuelingGenerators duelingGenerators = new DuelingGenerators();
        Map<String, Long> startMap = readFile(INPUT_PATH);
        log.info("Part1: {}", duelingGenerators.part1(startMap));
        log.info("Part2: {}", duelingGenerators.part2(startMap));
    }

    private static Map<String, Long> readFile(final String filePath) {
        Map<String, Long> inputs = new HashMap<>();

        ClassLoader cl = DuelingGenerators.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");
                inputs.put(parts[1].trim(), Long.parseLong(parts[4].trim()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return inputs;
    }

    public Long part1(final Map<String, Long> startMap) {
        Long generatorA = startMap.get("A");
        Long generatorB = startMap.get("B");
        Long matchCount = 0L;

        for (int i = 0; i < PART_1_LENGTH; i++) {
            generatorA = (generatorA * MULT_MAP.get("A")) % Integer.MAX_VALUE;
            generatorB = (generatorB * MULT_MAP.get("B")) % Integer.MAX_VALUE;

            Long tempA = generatorA & BIT_MASK_16;
            Long tempB = generatorB & BIT_MASK_16;
//            log.info("A`: {}, B`: {}; A%: {}, B%: {}", tempA, tempB, generatorA % 65534, generatorB % 65534);

            if (tempA.equals(tempB)) {
                matchCount++;
            }
        }

        return matchCount;
    }

    public Long part2(final Map<String, Long> startMap) {
        Long generatorA = startMap.get("A");
        Long generatorB = startMap.get("B");
        Long matchCount = 0L;

        for (int i = 0; i < PART_2_LENGTH; i++) {
            generatorA = findNext(generatorA, MULT_MAP.get("A"), FACTOR_MAP.get("A"));
            generatorB = findNext(generatorB, MULT_MAP.get("B"), FACTOR_MAP.get("B"));

            if ((generatorA & BIT_MASK_16) == (generatorB & BIT_MASK_16)) {
                matchCount++;
            }
        }

        return matchCount;
    }

    private long findNext(Long current, final Long mult, final Long factor) {
        while (true) {
            current *= mult;
            current %= Integer.MAX_VALUE;
            if (current % factor == 0) {
                return current;
            }
        }
    }
}
