package dev.davidson.ian.advent.year2017.day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PacketScanners {

    private static final String INPUT_PATH = "adventOfCode/2017/day13/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day13/sample.txt";

    public static void main(String[] args) {
        PacketScanners packetScanners = new PacketScanners();
        Map<Integer, Integer> layers = readFile(INPUT_PATH);
        log.info("Part1: {}", packetScanners.part1(layers));
        log.info("Part2: {}", packetScanners.part2(layers));
    }

    private static Map<Integer, Integer> readFile(final String filePath) {
        Map<Integer, Integer> layers = new HashMap<>();

        ClassLoader cl = PacketScanners.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(":");
                layers.put(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return layers;
    }

    private Long part1(final Map<Integer, Integer> layers) {
        Long result = 0L;
        int end =
                layers.keySet().stream().max(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException(
                        "Couldn't find key value, issue reading input"));

        for (int i = 0; i < end + 1; i++) {
            if (layers.containsKey(i)) {
                int cycleLength = 2 * (layers.get(i) - 1);
                if (i % cycleLength == 0) {
                    result += (long) i * layers.get(i);
                }
            }
        }

        return result;
    }

    private Integer part2(final Map<Integer, Integer> layers) {

        int startTime = 0;
        int endIndex =
                layers.keySet().stream().max(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException(
                        "Couldn't find key value, issue reading input"));
        long result;
        while (true) {
            result = 0L;

            for (int i = 0; i < endIndex + 1; i++) {
                if (layers.containsKey(i)) {
                    int cycleLength = 2 * (layers.get(i) - 1);
                    if ((startTime + i) % cycleLength == 0) {
                        result += (long) i * layers.get(i);
                    }
                }
            }

            if (result == 0L) {
                break;
            }
            startTime++;
        }

        return startTime;
    }
}
