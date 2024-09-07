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
    private static final Character SPACE = '.';

    private static final List<int[]> SHIFTS = List.of(
            new int[]{1, 0},
            new int[]{-1, 0},
            new int[]{0, 1},
            new int[]{0, -1}
    );

    public static void main(String[] args) {
        AirDuctSpelunking airDuctSpelunking = new AirDuctSpelunking();
        log.info("Part1: {}", airDuctSpelunking.part1());
    }


    public Integer part1() {
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

        return 0;
    }

    private Integer shortestPath(final int[] start, final int[] finish, final List<List<Character>> grid) {
        Set<String> visited = new HashSet<>();
        visited.add(toKey(start));

        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);

        int distanceTraveled = 0;
        while (queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                int[] current = queue.remove();

                List<int[]> neighbors = findNeighbors(current, grid);
            }
        }

        return -1;
    }


    private String toKey(final int[] rc) {
        return rc[0] + ":" + rc[1];
    }

    /*
    Intuition:
     Conditions for exercising different options:
     ########
     #0#A..##
     #..1####
     ###B...#
     ########

     start at 0, arrive at 1, we know we already counted 0->1 tiles, so the options should be between A & B

     What conditions will influence our decision to pick?
        Finding all on A will be 3 moves, and making it back to 1 will be 6 moves
        Finding all on B will be 4 moves, and making it back to 1 will be 8 moves

        options:
        a.
            0 -> 1 = 3 moves
            1 -> A(all) -> 1 = 6
            1 -> B(all) = 4
            total: 13 moves
        b.
            0 -> 1 = 3 moves
            1 -> B(all) -> 1 = 8
            1 -> A(all) = 3
            total: 14 moves

        Can we calculate:
         - shortest path to find all from a node
         - shortest path to find all and return back to the same start node.

        then if we have 2 options (other than how we came in)
         - Prefer shortest round trip option of the two
         -> add all found nodes to visited<>
         - Then choose other option for find all nodes within

        if we have 3 options (other than how we came in)
         - prefer shortest roundtrip option of the three
         -> add all found nodes to visited<>
         - then choose 2nd shortest round trip option
         -> add all found noddes to visited<>
         - then choose remaining option and find all nodes within

         Though the caveat here is that to calculate the shortest path to discover all nodes
         We will need to build up our representation by calling recursively on a smaller subgraph
         (to find optimal path for that subgraph).
     */

    private List<List<Character>> readFile() {
        List<List<Character>> grid = new ArrayList<>();

        ClassLoader cl = AirDuctSpelunking.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
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
