package dev.davidson.ian.advent.year2015.day09;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class SingleNight {

    private static final String INPUT_PATH = "adventOfCode/2015/day09/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day09/sample.txt";

    public static void main(String[] args) {
        SingleNight singleNight = new SingleNight();
        log.info("part1: {}", singleNight.part1());
        log.info("part2: {}", singleNight.part2());
    }

    public int part1() {
        Map<String, Map<String, Integer>> adjList = readFile();
        int min = Integer.MAX_VALUE;
        for (String location : adjList.keySet()) {
            Set<String> visited = new HashSet<>();
            visited.add(location);
            min = Math.min(min, findShortestPath(location, adjList, visited, 0));
        }

        return min;
    }

    public int part2() {
        Map<String, Map<String, Integer>> adjList = readFile();
        int max = 0;
        for (String location : adjList.keySet()) {
            Set<String> visited = new HashSet<>();
            visited.add(location);
            max = Math.max(max, findLongestPath(location, adjList, visited, 0));
        }

        return max;
    }

    private int findShortestPath(final String current, final Map<String, Map<String, Integer>> adjList, final Set<String> visited, final int currentDistance) {
        if (visited.size() == adjList.size()) {
            return currentDistance;
        }

        int foundDistance = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : adjList.get(current).entrySet()) {

            if (visited.contains(entry.getKey())) {
                continue;
            }

            Set<String> visitedCopy = new HashSet<>(visited);
            visitedCopy.add(entry.getKey());

            foundDistance = Math.min(foundDistance,
                    findShortestPath(entry.getKey(), adjList, visitedCopy, currentDistance + entry.getValue()));
        }

        return foundDistance;
    }

    private int findLongestPath(final String current, final Map<String, Map<String, Integer>> adjList, final Set<String> visited, final int currentDistance) {
        if (visited.size() == adjList.size()) {
            return currentDistance;
        }

        int foundDistance = 0;

        for (Map.Entry<String, Integer> entry : adjList.get(current).entrySet()) {

            if (visited.contains(entry.getKey())) {
                continue;
            }

            Set<String> visitedCopy = new HashSet<>(visited);
            visitedCopy.add(entry.getKey());

            foundDistance = Math.max(foundDistance,
                    findLongestPath(entry.getKey(), adjList, visitedCopy, currentDistance + entry.getValue()));
        }

        return foundDistance;
    }


    private Map<String, Map<String, Integer>> readFile() {
        List<String> lines = new ArrayList<>();
        ClassLoader cl = SingleNight.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        Map<String, Map<String, Integer>> map = new HashMap<>();

        for (String line : lines) {
            //Dublin to Belfast = 141
            String[] chunks = line.split("\\s+");

            String dest1 = chunks[0];
            String dest2 = chunks[2];
            Integer distance = Integer.parseInt(chunks[4]);

            if (map.containsKey(dest1)) {
                map.get(dest1).put(dest2, distance);
            } else {
                //no entry for dest1
                Map<String, Integer> tempMap = new HashMap<>();
                tempMap.put(dest2, distance);
                map.put(dest1, tempMap);
            }

            if (map.containsKey(dest2)) {
                map.get(dest2).put(dest1, distance);
            } else {
                //no entry for dest2
                Map<String, Integer> tempMap = new HashMap<>();
                tempMap.put(dest1, distance);
                map.put(dest2, tempMap);
            }
        }

        return map;
    }
}
