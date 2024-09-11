package dev.davidson.ian.advent.year2017.day06;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryReallocation {

    private static final String INPUT_PATH = "adventOfCode/2017/day06/input.txt";

    public static void main(String[] args) {
        MemoryReallocation memoryReallocation = new MemoryReallocation();
        List<Integer> inputs = readFile(INPUT_PATH);
        log.info("Part1: {}", memoryReallocation.part1(inputs));
        log.info("Part1: {}", memoryReallocation.part2(inputs));
    }

    private static List<Integer> readFile(final String filePath) {
        ClassLoader cl = MemoryReallocation.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return new ArrayList<>(Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .toList());

        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }

    public long part1(final List<Integer> input) {
        List<Integer> registers = new ArrayList<>(input);
        Set<String> seenConfigurations = new HashSet<>();
        seenConfigurations.add(toConfigEncoding(registers));
        int count = 0;

        while (count < Integer.MAX_VALUE) {
            count++;
            registers = redistribute(registers);
            String encoding = toConfigEncoding(registers);
            if (seenConfigurations.contains(encoding)) {
                break;
            }

            seenConfigurations.add(encoding);
        }

        return count;
    }

    public long part2(final List<Integer> input) {
        List<Integer> registers = new ArrayList<>(input);
        Map<String, Long> seenConfigurations = new HashMap<>();
        long count = 0L;
        seenConfigurations.put(toConfigEncoding(registers), count);

        while (count < Integer.MAX_VALUE) {
            count++;
            registers = redistribute(registers);
            String encoding = toConfigEncoding(registers);
            if (seenConfigurations.containsKey(encoding)) {
                return count - seenConfigurations.get(encoding);
            }

            seenConfigurations.put(encoding, count);
        }

        return count;
    }

    private List<Integer> redistribute(final List<Integer> registers) {
        int maxIndex = -1;
        int maxValue = -1;
        List<Integer> copy = new ArrayList<>(registers);
        for (int i = 0; i < copy.size(); i++) {
            if (copy.get(i) > maxValue) {
                maxIndex = i;
                maxValue = copy.get(i);
            }
        }

        copy.set(maxIndex, 0);
        for (int i = 0; i < maxValue; i++) {
            int index = (maxIndex + i + 1) % 16;
            copy.set(index, copy.get(index) + 1);
        }

        return copy;
    }

    private String toConfigEncoding(final List<Integer> registers) {
        return String.join(":", registers.stream().map(Object::toString).toList());
    }
}
