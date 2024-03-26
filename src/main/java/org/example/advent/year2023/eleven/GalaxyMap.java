package org.example.advent.year2023.eleven;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class GalaxyMap {

    private static final String INPUT_PATH = "adventOfCode/day11/input.txt";
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day11/input-sample.txt";

    private static final Long PART_1_ADDITIONAL_EXPANSION_WEIGHT = 1L;
    private static final Long PART_2_ADDITIONAL_EXPANSION_WEIGHT = 999999L;

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = GalaxyMap.class.getClassLoader();
            File file = new File(classLoader.getResource(INPUT_PATH).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    private static List<List<Character>> interpretAndExpand(List<String> inputs) {
        Set<Integer> emptyRows = new HashSet<>();
        Set<Integer> emptyCols = new HashSet<>();

        for (int i = 0; i < inputs.size(); i++) {
            boolean noGalaxy = true;
            for (int j = 0; j < inputs.get(0).length(); j++) {
                if (inputs.get(i).charAt(j) != '.') {
                    noGalaxy = false;
                    break;
                }
            }
            if (noGalaxy) {
                emptyRows.add(i);
            }
        }

        for (int k = 0; k < inputs.get(0).length(); k++) {
            boolean noGalaxy = true;
            for (int l = 0; l < inputs.size(); l++) {
                if (inputs.get(l).charAt(k) != '.') {
                    noGalaxy = false;
                    break;
                }
            }

            if (noGalaxy) {
                emptyCols.add(k);
            }
        }

        List<List<Character>> map = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < inputs.get(0).length(); j++) {
                row.add(inputs.get(i).charAt(j));

                if (emptyCols.contains(j)) {
                    row.add(inputs.get(i).charAt(j));
                }
            }

            map.add(row);
            if (emptyRows.contains(i)) {
                map.add(row);
            }
        }

        return map;
    }

    private static List<Galaxy> processInput(List<List<Character>> map) {
        List<Galaxy> galaxies = new ArrayList<>();

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {
                if (map.get(i).get(j) == '#') {
                    galaxies.add(Galaxy.builder().row(i).col(j).build());
                }
            }
        }

        return galaxies;
    }

    private static Long computeDistances(List<Galaxy> galaxies) {
        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                sum += computeIndividualDistance(galaxies.get(i), galaxies.get(j));
            }
        }

//        return computeDistancesHelper(paths);
        return sum;
    }

    private static long computeIndividualDistance(Galaxy one, Galaxy two) {
//        System.out.println("computing diff of galaxy1: " + one + " and galaxy2: " + two);
        return Math.abs(one.getCol() - two.getCol()) +
            Math.abs(one.getRow() - two.getRow());
    }

    public static long part1() {
        List<String> rawInputs = readFile();
        List<List<Character>> map = interpretAndExpand(rawInputs);
        List<Galaxy> galaxies = processInput(map);
        return computeDistances(galaxies);
    }

    private static List<Set<Integer>> interpretAndExpandPart2(List<String> inputs) {
        Set<Integer> emptyRows = new HashSet<>();
        Set<Integer> emptyCols = new HashSet<>();
        for (int i = 0; i < inputs.size(); i++) {
            boolean noGalaxy = true;
            for (int j = 0; j < inputs.get(0).length(); j++) {
                if (inputs.get(i).charAt(j) != '.') {
                    noGalaxy = false;
                    break;
                }
            }
            if (noGalaxy) {
                emptyRows.add(i);
            }
        }

        for (int k = 0; k < inputs.get(0).length(); k++) {
            boolean noGalaxy = true;
            for (int l = 0; l < inputs.size(); l++) {
                if (inputs.get(l).charAt(k) != '.') {
                    noGalaxy = false;
                    break;
                }
            }

            if (noGalaxy) {
                emptyCols.add(k);
            }
        }

        return List.of(emptyRows, emptyCols);
    }

    private static List<Galaxy> processInputPart2(List<String> input, List<Set<Integer>> emptyRowsThenCols) {
        List<Galaxy> galaxies = new ArrayList<>();

        int emptyRowsPassedNum = 0;
        for (int i = 0; i < input.size(); i++) {
            if (emptyRowsThenCols.get(0).contains(i)) {
                emptyRowsPassedNum++;
            }
            int emptyColsPassedNum = 0;
            for (int j = 0; j < input.get(0).length(); j++) {
                if (emptyRowsThenCols.get(1).contains(j)) {
                    emptyColsPassedNum++;
                }

                if (input.get(i).charAt(j) == '#') {
                    galaxies.add(Galaxy.builder().row((emptyRowsPassedNum * 999999) + i).col((emptyColsPassedNum * 999999) + j).build());
                }
            }
        }

        return galaxies;
    }


    public static long part2() {
        List<String> rawInputs = readFile();
        List<Set<Integer>> emptyRowsThenColumns = interpretAndExpandPart2(rawInputs);
        List<Galaxy> galaxies = processInputPart2(rawInputs, emptyRowsThenColumns);
        return computeDistances(galaxies);
    }

    public static void main(String[] ins) {
//        Long dist = part1();
//        System.out.println("distance sum: " + dist);

        Long dist2 = part2();
        System.out.println("distance for part2: " + dist2);
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class GalaxyPath {

        private List<Galaxy> galaxies;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GalaxyPath that = (GalaxyPath) o;
            return Objects.equals(galaxies, that.galaxies);
        }

        @Override
        public int hashCode() {
            return Objects.hash(galaxies);
        }
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Galaxy {
        private int row;
        private int col;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Galaxy galaxy = (Galaxy) o;
            return row == galaxy.row && col == galaxy.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
}
