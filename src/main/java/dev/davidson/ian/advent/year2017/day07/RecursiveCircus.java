package dev.davidson.ian.advent.year2017.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RecursiveCircus {

    private static final String INPUT_PATH = "adventOfCode/2017/day07/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day07/sample.txt";

    public static void main(String[] args) {
        RecursiveCircus recursiveCircus = new RecursiveCircus();
        Map<String, Disk> disks = readFile(INPUT_PATH);
        Map<String, String> reverseMap = new HashMap<>();
        String rootLabel = recursiveCircus.part1(disks, reverseMap);
        log.info("Part1: {}", rootLabel);
        log.info("Part2: {}", recursiveCircus.part2(disks, reverseMap, rootLabel));
    }

    private static Map<String, Disk> readFile(final String filePath) {
        Map<String, Disk> disks = new HashMap<>();

        ClassLoader cl = RecursiveCircus.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                disks.put(line.split("\\s+")[0], Disk.newDisk(line));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return disks;
    }

    public String part1(final Map<String, Disk> disks, final Map<String, String> reverseMap) {
        for (Disk disk : disks.values()) {

            if (!reverseMap.containsKey(disk.label())) {
                reverseMap.put(disk.label(), "");
            }

            for (String heldDisk : disk.heldDisks()) {
                reverseMap.put(heldDisk, disk.label());
            }
        }

        List<String> start =
                reverseMap.keySet().stream().filter(key -> reverseMap.get(key).equals("")).toList();

        assert start.size() == 1;
        return start.getFirst();
    }

    public Long part2(final Map<String, Disk> disks, final Map<String, String> reverseMap,
                        final String rootLabel) {
        //compute rollup scores
        Map<String, Long> rollUpMap = new HashMap<>();
        DFS(disks, rollUpMap, rootLabel);

        //recursively find uneven child
        String uneven = findLeafUnevenLeaf(disks, rollUpMap, rootLabel);
//        log.info("uneven: {}", uneven);

        long unevenRollup = rollUpMap.get(uneven);
        String unevenParent = reverseMap.get(uneven);

        //find all siblings of uneven disk, narrow down to roll up weight that is not the uneven one
        List<Long> otherValues =
                disks.get(unevenParent).heldDisks().stream().mapToLong(rollUpMap::get).distinct().filter(weight -> weight != unevenRollup).boxed().toList();
        assert otherValues.size() == 1;

        //find difference in uneven roll up to siblings, find adjustment on uneven
        return disks.get(uneven).weight() + (otherValues.getFirst() - unevenRollup);
    }

    private Long DFS(final Map<String, Disk> disks, final Map<String, Long> rollUpMap, final String currentLabel) {
        long runningSum = disks.get(currentLabel).weight();

        for (String child : disks.get(currentLabel).heldDisks()) {
            runningSum += DFS(disks, rollUpMap, child);
        }

        rollUpMap.put(currentLabel, runningSum);
        return runningSum;
    }

    private String findLeafUnevenLeaf(final Map<String, Disk> disks, final Map<String, Long> rollUpMap,
                                      final String currentLabel) {
        if (disks.get(currentLabel).heldDisks().isEmpty()) {
            return currentLabel;
        }

        List<String> children = disks.get(currentLabel).heldDisks();


//        log.info("{} weight: {}", currentLabel, disks.get(currentLabel).weight());

        int count = 1;
        long weight = rollUpMap.get(children.getFirst());
//        log.info("{} roll up weight: {}", children.getFirst(), weight);

        for (int i = 1; i < children.size(); i++) {
            long tempWeight = rollUpMap.get(children.get(i));

//            log.info("{} roll up weight: {}", children.get(i), tempWeight);

            if (tempWeight != weight) {
                if (count == 0) {
                    count = 1;
                    weight = tempWeight;
                } else {
                    count--;
                }
            } else {
                count++;
            }
        }

        for (String child : children) {
            if (rollUpMap.get(child) != weight) {
                return findLeafUnevenLeaf(disks, rollUpMap, child);
            }
        }

        return currentLabel;
    }
}
