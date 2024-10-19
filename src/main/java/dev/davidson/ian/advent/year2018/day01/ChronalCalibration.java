package dev.davidson.ian.advent.year2018.day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChronalCalibration {

    private static final String INPUT_PATH = "adventOfCode/2018/day01/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2018/day01/sample.txt";

    public static void main(String[] args) {
        ChronalCalibration chronalCalibration = new
                ChronalCalibration();

        List<Integer> inputs = readFile(INPUT_PATH);

        log.info("Part1: {}", inputs.stream().mapToInt(Integer::intValue).sum());
        log.info("Part2: {}", chronalCalibration.part2(inputs));
    }

    private static List<Integer> readFile(final String filePath) {
        List<Integer> inputs = new ArrayList<>();

        ClassLoader cl = ChronalCalibration.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputs.add(Integer.parseInt(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return inputs;
    }

    public Integer part2(final List<Integer> inputs) {
        Integer current = 0;
        Set<Integer> seen = new HashSet<>();
        seen.add(current);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            current += inputs.get(i % inputs.size());
            if (seen.contains(current)) {
                return current;
            }

            seen.add(current);
        }

        return -1;
    }
}
