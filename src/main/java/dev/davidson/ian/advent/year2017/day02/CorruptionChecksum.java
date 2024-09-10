package dev.davidson.ian.advent.year2017.day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CorruptionChecksum {

    private static final String INPUT_PATH = "adventOfCode/2017/day02/input.txt";

    public static void main(String[] args) {
        CorruptionChecksum corruptionChecksum = new CorruptionChecksum();
        log.info("Part1: {}", corruptionChecksum.part1());
        log.info("Part2: {}", corruptionChecksum.part2());
    }

    public Integer part1() {
        List<List<Integer>> inputs = readFile();

        return inputs.stream().mapToInt(line ->
                        line.stream().max(Comparator.naturalOrder()).orElseThrow(IllegalStateException::new) -
                                line.stream().min(Comparator.naturalOrder()).orElseThrow(IllegalStateException::new))
                .sum();

    }

    public Integer part2() {
        List<List<Integer>> inputs = readFile();

        int checksum = 0;
        for (List<Integer> line : inputs) {

            for (int i = 0; i < line.size() - 1; i++) {
                for (int j = i + 1; j < line.size(); j++) {

                    int tempMax = Math.max(line.get(i), line.get(j));
                    int tempMin = Math.min(line.get(i), line.get(j));
                    if (tempMax % tempMin == 0) {
                        checksum += tempMax / tempMin;
                    }
                }
            }

        }

        return checksum;
    }

    private List<List<Integer>> readFile() {
        List<List<Integer>> inputs = new ArrayList<>();

        ClassLoader cl = CorruptionChecksum.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputs.add(Arrays.stream(scanner.nextLine().split("\\s+"))
                        .mapToInt(Integer::parseInt).boxed()
                        .toList());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return inputs;
    }
}
