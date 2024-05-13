package dev.davidson.ian.advent.year2023.day18;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class LavaductLagoon {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day18/input-sample.txt";

    private static final String MINI_SAMPLE_INPUT_PATH = "adventOfCode/2023/day18/input-sample-mini.txt";
    private static final String INPUT_PATH = "adventOfCode/2023/day18/input.txt";

    private static Edge parseInput(String inputLine, boolean part1) {
        String[] parts = inputLine.split("\\s+");

        if (part1) {
            return Edge.builder()
                    .direction(Direction.valueOf(parts[0]))
                    .distance(Integer.parseInt(parts[1]))
                    .rgbValue(parts[2].substring(1, parts[2].length() - 1))
                    .build();
        } else {
            int newDistance = Integer.parseInt(parts[2].substring(1, parts[2].length() - 1).substring(1, 6), 16);
            Direction directionOrdinal = Direction.toDirection(Integer.parseInt(parts[2].substring(1, parts[2].length() - 1).substring(6)));

            return Edge.builder()
                    .direction(directionOrdinal)
                    .distance(newDistance)
                    .rgbValue(parts[2].substring(1, parts[2].length() - 1))
                    .build();
        }
    }

    private static List<Edge> readFile(boolean part1) {
        List<Edge> input = new ArrayList<>();
        try {
            ClassLoader classLoader = LavaductLagoon.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(parseInput(myReader.nextLine(), part1));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    private static List<Node> processEdges(List<Edge> edges) {
        List<Node> nodes = new ArrayList<>();
        int r = 0;
        int c = 0;
        for (Edge edge : edges) {
            nodes.add(Node.builder().row(r).col(c).rgbValue(edge.getRgbValue()).build());
            switch (edge.getDirection()) {
                case U -> r -= edge.getDistance();
                case D -> r += edge.getDistance();
                case R -> c += edge.getDistance();
                case L -> c -= edge.getDistance();
            }

        }

        return nodes;
    }

    private static long countInsideGrid(List<Node> gridWithPath, long perimeter) {
        long area = 0;
        int j = gridWithPath.size() - 1;

        for (int i = 0; i < gridWithPath.size(); i++) {
            area += (long) (gridWithPath.get(j).getRow() + gridWithPath.get(i).getRow()) *
                    (gridWithPath.get(i).getCol() - gridWithPath.get(j).getCol());

            j = i;
        }

        area = Math.abs(area / 2);

        //pick's theorem:
        return area - (perimeter / 2) + 1;
    }

    public long part1() {
        List<Edge> edges = readFile(true);
        List<Node> nodes = processEdges(edges);
        long perimeter = edges.stream().mapToLong(Edge::getDistance).sum();
        long area = countInsideGrid(nodes, perimeter);
        return area + perimeter;
    }

    public long part2() {
        List<Edge> edges = readFile(false);
        List<Node> nodes = processEdges(edges);
        long perimeter = edges.stream().mapToLong(Edge::getDistance).sum();
        long area = countInsideGrid(nodes, perimeter);
        return area + perimeter;
    }

    public static void main(String[] args) {
        LavaductLagoon lavaductLagoon = new LavaductLagoon();
        long result1 = lavaductLagoon.part1();
        System.out.println("part1 result: " + result1);

        long result2 = lavaductLagoon.part2();
        System.out.println("part2 result: " + result2);
    }

    public enum Direction {
        R, D, L, U;

        public static Direction toDirection(int ordinal) {
            switch (ordinal) {
                case 0 -> {
                    return R;
                }
                case 1 -> {
                    return D;
                }
                case 2 -> {
                    return L;
                }
                case 3 -> {
                    return U;
                }
            }
            return null;
        }
    }


    @Data
    @Builder
    @AllArgsConstructor
    public static class Edge {
        private final Direction direction;
        private final int distance;
        private final String rgbValue;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Node {
        private final int row;
        private final int col;
        private final String rgbValue;
    }

}
