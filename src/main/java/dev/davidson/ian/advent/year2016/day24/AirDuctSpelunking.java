package dev.davidson.ian.advent.year2016.day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AirDuctSpelunking {
    private static final String SAMPLE_PATH = "adventOfCode/2016/day24/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day24/input.txt";
    private static final Character WALL = '#';

    private static final List<int[]> SHIFTS = List.of(
            new int[]{1, 0},
            new int[]{-1, 0},
            new int[]{0, 1},
            new int[]{0, -1}
    );

    public static void main(String[] args) {
        AirDuctSpelunking airDuctSpelunking = new AirDuctSpelunking();
        log.info("Part1: {}", airDuctSpelunking.execute(false));
        log.info("Part2: {}", airDuctSpelunking.execute(true));
    }


    public Integer execute(boolean part2) {
        int[][] points = new int[8][2]; //r,c
        List<List<Character>> grid = readFile();
        int rows = grid.size();
        int cols = grid.getFirst().size();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char ch = grid.get(i).get(j);
                if (Character.isDigit(ch)) {
                    points[ch - '0'][0] = i;
                    points[ch - '0'][1] = j;
                }
            }
        }

        int[][] distances = new int[8][8];
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                int distance = shortestPath(points[i], points[j], grid);
                distances[i][j] = distance;
                distances[j][i] = distance;
            }
        }
        List<List<Integer>> sequences = new ArrayList<>();
        List<Integer> initial = new ArrayList<>();
        initial.add(0);
        permutations(List.of(1, 2, 3, 4, 5, 6, 7), initial, sequences);

        Integer minDistance = Integer.MAX_VALUE;
        for (List<Integer> sequence : sequences) {
            int temp = 0;
            for (int i = 1; i < sequence.size(); i++) {
                temp += distances[sequence.get(i - 1)][sequence.get(i)];
            }

            if (part2) {
                temp += distances[sequence.get(sequence.size() - 1)][0];
            }

            minDistance = Math.min(minDistance, temp);
        }

        return minDistance;
    }

    private Integer shortestPath(final int[] start, final int[] finish, final List<List<Character>> grid) {
        Set<String> visited = new HashSet<>();
        visited.add(toKey(start));

        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);

        int distanceTraveled = 0;
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                int[] current = queue.remove();

                if (current[0] == finish[0] && current[1] == finish[1]) {
                    return distanceTraveled;
                }

                List<int[]> neighbors = findNeighbors(current, grid);

                for (int[] neighbor : neighbors) {
                    if (!visited.contains(toKey(neighbor))) {
                        queue.add(neighbor);
                        visited.add(toKey(neighbor));
                    }
                }
            }
            distanceTraveled++;
        }

        return -1;
    }

    private String toKey(final int[] rc) {
        return rc[0] + ":" + rc[1];
    }

    private void permutations(final List<Integer> remaining,
                              final List<Integer> currentSequence,
                              final List<List<Integer>> result) {
        if (remaining.isEmpty()) {
            result.add(currentSequence);
            return;
        }

        for (int i = 0; i < remaining.size(); i++) {
            List<Integer> nextSeq = new ArrayList<>(currentSequence);
            List<Integer> nextRemaining = new ArrayList<>(remaining);
            nextSeq.add(nextRemaining.get(i));
            nextRemaining.remove(i);
            permutations(nextRemaining, nextSeq, result);
        }
    }


    private List<int[]> findNeighbors(final int[] current, final List<List<Character>> grid) {
        List<int[]> neighbors = new ArrayList<>();

        for (int[] shift : SHIFTS) {
            int nextR = current[0] + shift[0];
            int nextC = current[1] + shift[1];

            if (nextC > 0 &&
                    nextC < grid.getFirst().size()
                    && nextR > 0
                    && nextR < grid.size()
                    && grid.get(nextR).get(nextC) != WALL) {
                neighbors.add(
                        new int[]{nextR, nextC}
                );
            }
        }

        return neighbors;
    }

    private List<List<Character>> readFile() {
        List<List<Character>> grid = new ArrayList<>();

        ClassLoader cl = AirDuctSpelunking.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                grid.add(
                        scanner.nextLine()
                                .chars()
                                .mapToObj(ch -> (char) ch)
                                .toList()
                );
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided file path");
        }

        return grid;
    }
}
