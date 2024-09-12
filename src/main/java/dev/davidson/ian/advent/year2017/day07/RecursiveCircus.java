package dev.davidson.ian.advent.year2017.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        String rootLabel = recursiveCircus.part1(disks);
        log.info("Part1: {}", rootLabel);
        log.info("Part2: {}", recursiveCircus.part2(disks, rootLabel));
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

    public String part1(final Map<String, Disk> disks) {
        Map<String, List<String>> reverseMap = new HashMap<>();
        for (Disk disk : disks.values()) {

            if (!reverseMap.containsKey(disk.label())) {
                reverseMap.put(disk.label(), new ArrayList<>());
            }

            for (String heldDisk : disk.heldDisks()) {
                if (!reverseMap.containsKey(heldDisk)) {
                    reverseMap.put(heldDisk, new ArrayList<>());
                }

                reverseMap.get(heldDisk).add(disk.label());
            }
        }

        List<String> start =
                reverseMap.keySet().stream().filter(key -> reverseMap.get(key).isEmpty()).toList();

        assert start.size() == 1;
        return start.getFirst();
    }

    public String part2(final Map<String, Disk> disks, final String rootLabel){
        //compute rollup scores
        Map<String, Long> rollUpMap = new HashMap<>();
        DFS(disks, rollUpMap, rootLabel);


        List<String> firstLayer = disks.get(rootLabel).heldDisks();
        log.info("{}; weight:{} rollup:{}", rootLabel, disks.get(rootLabel).weight(), rollUpMap.get(rootLabel));
        for(String child: firstLayer){
            log.info("{}: weight:{} rollup:{}", child, disks.get(child).weight(), rollUpMap.get(child));
        }

        //recursively find child



        return "";

    }

    private Long DFS(final Map<String, Disk> disks, final Map<String, Long> rollUpMap, final String currentLabel){
        long runningSum = disks.get(currentLabel).weight();

        for(String child : disks.get(currentLabel).heldDisks()){
            runningSum += DFS(disks, rollUpMap, child);
        }

        rollUpMap.put(currentLabel, runningSum);
        return runningSum;
    }

    private String findLeafUnevenLeaf(final Map<String, Disk> disks, final Map<String, Long> rollUpMap,
                                      final String currentLabel){
        if(disks.get(currentLabel).heldDisks().isEmpty()){
            return currentLabel;
        }

        List<String> children = disks.get(currentLabel).heldDisks();

        int count = 1;
        long weight = rollUpMap.get(children.getFirst());
        for(int i = 1; i < children.size(); i++){
            long tempWeight = rollUpMap.get(children.get(i));

            if(tempWeight != weight){
                if(count == 0){
                    count = 1;
                    weight = tempWeight;
                }else{
                    count--;
                }
            }else{
                count++;
            }
        }


    }
}
