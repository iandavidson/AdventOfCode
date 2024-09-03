package dev.davidson.ian.advent.year2016.day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirewallRules {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day20/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day20/input.txt";

    public static void main(String[] args) {
        FirewallRules firewallRules = new FirewallRules();

        List<Range> input = readFile(INPUT_PATH);
        Collections.sort(input);

        List<Range> reduced = firewallRules.reduce(input);
        log.info("Part1: {}", reduced.getFirst().finish() + 1);
        log.info("Part2: {}", firewallRules.findUncoveredWidth(reduced));
    }

    private static List<Range> readFile(final String filePath) {
        List<Range> ranges = new ArrayList<>();

        ClassLoader cl = FirewallRules.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                ranges.add(Range.newRange(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return ranges;
    }

    public List<Range> reduce(final List<Range> ranges) {

        List<Range> results = new ArrayList<>();

        for (int i = 0; i < ranges.size(); i++) {
            Range current = ranges.get(i);
            while (i < ranges.size() - 1 && current.overlaps(ranges.get(i + 1))) {
                current = Range.combineRange(current, ranges.get(i + 1));
                i++;
            }
            results.add(current);
        }

        return results;
    }

    public Long findUncoveredWidth(final List<Range> ranges) {
        long currentIp = 0L;
        int index = 0;
        long count = 0L;

        while (currentIp < Math.pow(2, 32)) {
            if (index < ranges.size() && ranges.get(index).overlaps(currentIp)) {
                currentIp = ranges.get(index).finish() + 1;
                index++;
            } else if (index < ranges.size()) {
                count += ranges.get(index).start() - currentIp;
                currentIp = ranges.get(index).start();
            } else {
                count++;
                currentIp++;
            }
        }

        return count;
    }

}
