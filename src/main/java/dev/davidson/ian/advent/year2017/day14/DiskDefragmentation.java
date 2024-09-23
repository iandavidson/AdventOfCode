package dev.davidson.ian.advent.year2017.day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
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
public class DiskDefragmentation {

    private static final String INPUT_PATH = "adventOfCode/2017/day14/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day14/sample.txt";
    private static final Character EMPTY = '0';
    private static final Character FILLED = '1';

    public static void main(String[] args) {
        DiskDefragmentation diskDefragmentation = new DiskDefragmentation();
        String input = readFile(SAMPLE_PATH);
        log.info("Part1: {}", diskDefragmentation.part1(input));
        log.info("Part2: {}", diskDefragmentation.part2(input));

    }

    private static String readFile(final String filePath) {
        ClassLoader cl = DiskDefragmentation.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }

    private Integer part1(final String key) {
        int count = 0;
        for (int i = 0; i < 128; i++) {
            String updatedKey = key + "-" + i;
            KnotHash knotHash = new KnotHash(updatedKey);
            String rowKey = knotHash.apply();
            count += new BigInteger(rowKey, 16).bitCount();
        }

        return count;
    }

    private Integer part2(final String key) {
        List<List<Character>> grid = new ArrayList<>();
        Set<Coordinate> filledLocations = new HashSet<>();
        for (int i = 0; i < 128; i++) {
            String updatedKey = key + "-" + i;
            KnotHash knotHash = new KnotHash(updatedKey);
            String rowKey = knotHash.apply();
            String binaryString = new BigInteger(rowKey, 16).toString(2);
            List<Character> tempList = new ArrayList<>();
            for (int j = 0; j < 128; j++) {
                if(j < binaryString.length()){
                    char ch = binaryString.charAt(j);
                    tempList.add(ch);
                    if (ch == '1') {
                        filledLocations.add(new Coordinate(i, j));
                    }
                }else{
                    tempList.add(EMPTY);
                }
            }
            grid.add(tempList);
        }

        int foundGroups = 0;
        Set<Coordinate> visited = new HashSet<>();

        for (Coordinate coordinate : filledLocations) {
            if (!visited.contains(coordinate)) {
                BFS(grid, visited, coordinate);
                foundGroups++;
            }
        }

        return foundGroups;
    }

    private void BFS(final List<List<Character>> grid, final Set<Coordinate> visited, final Coordinate start) {
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Coordinate current = queue.remove();

                for (int[] shift : List.of(new int[]{0, 1}, new int[]{0, -1}, new int[]{1, 0}, new int[]{-1, 0})) {
                    Coordinate potentialNeighbor =
                            new Coordinate(current.r() + shift[0], current.c() + shift[1]);

                    if (potentialNeighbor.c() >= 0 && potentialNeighbor.c() <= 127
                            && potentialNeighbor.r() >= 0 && potentialNeighbor.r() <= 127
                            && grid.get(potentialNeighbor.r()).get(potentialNeighbor.c()) == FILLED
                            && !visited.contains(potentialNeighbor)
                    ) {
                        queue.add(potentialNeighbor);
                        visited.add(potentialNeighbor);
                    }
                }
            }
        }
    }

}
