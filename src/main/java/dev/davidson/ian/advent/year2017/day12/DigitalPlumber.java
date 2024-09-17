package dev.davidson.ian.advent.year2017.day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DigitalPlumber {


    private static final String INPUT_PATH = "adventOfCode/2017/day12/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day12/sample.txt";

    public static void main(String[] args) {
        Map<Integer, Set<Integer>> adjList = readFile(INPUT_PATH);

        DigitalPlumber digitalPlumber = new DigitalPlumber();
        log.info("Part1: {}", digitalPlumber.part1(adjList));
        log.info("Part2: {}", digitalPlumber.part2(adjList));
    }

    private static Map<Integer, Set<Integer>> readFile(final String filePath) {
        Map<Integer, Set<Integer>> adjList = new HashMap<>();

        ClassLoader cl = DigitalPlumber.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String[] key_value = scanner.nextLine().split("<->");
                Integer key = Integer.parseInt(key_value[0].trim());
                List<Integer> values =
                        Arrays.stream(key_value[1].trim().replaceAll("\\s+", "").split(","))
                                .mapToInt(Integer::parseInt).boxed().toList();

                adjList.putIfAbsent(key, new HashSet<>());
                for (Integer value : values) {
                    adjList.putIfAbsent(value, new HashSet<>());
                    adjList.get(key).add(value);
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read at filepath provided");
        }

        return adjList;
    }

    public Integer part1(final Map<Integer, Set<Integer>> programs) {
        return BFS(programs, 0).size();
    }

    public Integer part2(final Map<Integer, Set<Integer>> programs) {
        Set<Integer> outerVisited = new HashSet<>();
        Integer groupCount = 0;
        for (int i = 0; i < programs.keySet().size(); i++) {
            if (!outerVisited.contains(i)) {
                outerVisited.addAll(BFS(programs, i));
                groupCount++;
            }
        }

        return groupCount;
    }

    private Set<Integer> BFS(final Map<Integer, Set<Integer>> programs, final Integer start) {
        Set<Integer> visited = new HashSet<>();
        visited.add(start);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Integer current = queue.remove();

                for (Integer neighbor : programs.get(current)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return visited;
    }
}