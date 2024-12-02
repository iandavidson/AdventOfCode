package dev.davidson.ian.advent.year2024.day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HistorianHysteria {


    private static final String REAL_INPUT_PATH = "adventOfCode/2024/day01/real.txt";

    public static void main(String [] args){
        HistorianHysteria historianHysteria = new HistorianHysteria();
        log.info("Part1: {}", historianHysteria.part1(readInput(REAL_INPUT_PATH)));
        log.info("Part2: {}", historianHysteria.part2(readInput(REAL_INPUT_PATH)));
    }

    public long part1(final List<List<Long>> input){
        List<Long> left = input.getFirst();
        List<Long> right = input.getLast();

        Collections.sort(left);
        Collections.sort(right);

        Long sum = 0L;
        for(int i = 0; i < left.size(); i++){
            sum += Math.abs(left.get(i) - right.get(i));
        }

        return sum;
    }

    public long part2(final List<List<Long>> input){
        Map<Long, Integer> leftMap = new HashMap<>();

        Set<Long> left = new HashSet<>(input.getFirst());
        List<Long> right = input.getLast();

        for(Long val : right){
            if(left.contains(val)){
                leftMap.put(val, leftMap.getOrDefault(val, 0) + 1);
            }
        }

        long tally = 0L;
        for(Map.Entry<Long, Integer> entry : leftMap.entrySet()){
            tally += entry.getKey() * entry.getValue();
        }

        return tally;
    }

    private static List<List<Long>> readInput(final String filePath){
        List<Long> left = new ArrayList<>();
        List<Long> right = new ArrayList<>();

        ClassLoader cl = HistorianHysteria.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] parts = scanner.nextLine().split("\\s+");
                left.add(Long.parseLong(parts[0]));
                right.add(Long.parseLong(parts[1]));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Failed to read input file");
        }

        return List.of(left, right);
    }

}
