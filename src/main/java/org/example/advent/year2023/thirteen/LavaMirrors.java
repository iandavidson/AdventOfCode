package org.example.advent.year2023.thirteen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class LavaMirrors {

    private static final String INPUT_PATH = "adventOfCode/day13/input.txt";
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day13/input-sample.txt";

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = LavaMirrors.class.getClassLoader();
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

    private static List<List<String>> processInput(List<String> inputs) {
        List<List<String>> grids = new ArrayList<>();
        List<String> grid = new ArrayList<>();
        for (String row : inputs) {
            if (row.isBlank()) {
                grids.add(grid);
                grid = new ArrayList<>();
            } else {
                grid.add(row);
            }
        }

        grids.add(grid);

        return grids;
    }

    private static int findMirror(List<String> gridRaw) {
        List<List<Character>> grid = new ArrayList<>();
        for (String s : gridRaw) {
            List<Character> gridRow = new ArrayList<>();
            for (int j = 0; j < s.length(); j++) {
                gridRow.add(s.charAt(j));
            }
            grid.add(gridRow);
        }

        int score = 0;

        //horizontal check - search each column
        Set<Integer> horzAllFound = new HashSet<>();
        List<Set<Integer>> horzFoundByIndex = new ArrayList<>();

        for (int i = 0; i < grid.size(); i++) {
            Set<Integer> foundInRow = new HashSet<>();

            List<Character> row = grid.get(i);
            for (int j = 0; j < row.size() - 1; j++) {
                if (row.get(j) == row.get(j + 1)) {
                    //expand search until left or right hits boundary
                    int left = j;
                    int right = j + 1;
                    boolean validTilBoundary = true;
                    while (left > -1 && right < row.size()) {
                        if (row.get(left) != row.get(right)) {
                            validTilBoundary = false;
                            break;
                        }

                        left--;
                        right++;
                    }

                    if (validTilBoundary) {
                        horzAllFound.add(j);
                        foundInRow.add(j);
                    }
                }
            }

            horzFoundByIndex.add(foundInRow);
        }

        for (Integer i : horzAllFound) {
            boolean foundEverywhere = true;
            for (Set<Integer> set : horzFoundByIndex) {
                if (!set.contains(i)) {
                    foundEverywhere = false;
                    break;
                }
            }

            if (foundEverywhere) {
                score = i + 1;
            }
        }

        //vertical check - search each row
        Set<Integer> vertAllFound = new HashSet<>();
        List<Set<Integer>> vertFoundByIndex = new ArrayList<>();
        for (int i = 0; i < grid.get(0).size(); i++) {
            Set<Integer> foundInCol = new HashSet<>();
            for (int j = 0; j < grid.size() - 1; j++) {
                //grid.get(j).get(i)
                if (grid.get(j).get(i) == grid.get(j + 1).get(i)) {
                    //expand search up and down until hits boundary
                    int above = j;
                    int below = j + 1;
                    boolean validTilBoundary = true;

                    while (above > -1 && below < grid.size()) {
                        if (grid.get(above).get(i) != grid.get(below).get(i)) {
                            validTilBoundary = false;
                            break;
                        }

                        above--;
                        below++;
                    }

                    if (validTilBoundary) {
                        vertAllFound.add(j);
                        foundInCol.add(j);
                    }
                }
            }
            vertFoundByIndex.add(foundInCol);
        }

        for (Integer i : vertAllFound) {
            boolean foundEverywhere = true;
            for (Set<Integer> set : vertFoundByIndex) {
                if (!set.contains(i)) {
                    foundEverywhere = false;
                    break;
                }
            }

            if (foundEverywhere) {
//                System.out.println("Found split rowIndex: " + i + " was consistent for all cols");
                score = (i + 1) * 100;
            }
        }


        return score;
    }

    private static int findMirrorPart2(List<String> gridRaw) {
        List<List<Character>> grid = new ArrayList<>();
        for (String s : gridRaw) {
            List<Character> gridRow = new ArrayList<>();
            for (int j = 0; j < s.length(); j++) {
                gridRow.add(s.charAt(j));
            }
            grid.add(gridRow);
        }

        int score = 0;

        //first check for perfect:
        Set<Integer> horzAllFound = new HashSet<>();
        List<Set<Integer>> horzFoundByIndex = new ArrayList<>();
        for (int i = 0; i < grid.size(); i++) {
            Set<Integer> foundInRow = new HashSet<>();

            List<Character> row = grid.get(i);
            for (int j = 0; j < row.size() - 1; j++) {
                if (row.get(j) == row.get(j + 1)) {
                    //expand search until left or right hits boundary
                    int left = j;
                    int right = j + 1;
                    boolean validTilBoundary = true;
                    while (left > -1 && right < row.size()) {
                        if (row.get(left) != row.get(right)) {
                            validTilBoundary = false;
                            break;
                        }

                        left--;
                        right++;
                    }

                    if (validTilBoundary) {
                        horzAllFound.add(j);
                        foundInRow.add(j);
                    }
                }
            }

            horzFoundByIndex.add(foundInRow);
        }

        //horizontal check - search each column for imperfect symmetry
        List<Set<Integer>> horzFoundWithDifference = new ArrayList<>();
        for (int i = 0; i < grid.size(); i++) {
            Set<Integer> foundInRowWithDifference = new HashSet<>();

            List<Character> row = grid.get(i);
            boolean chanceLeft = true;
            for (int j = 0; j < row.size() - 1; j++) {
                if (row.get(j) == row.get(j + 1) || chanceLeft) {

                    //expand search until left or right hits boundary
                    int left = j;
                    int right = j + 1;
                    boolean validTilBoundary = true;
                    while (left > -1 && right < row.size()) {

                        if (row.get(left) != row.get(right)) {
                            if (chanceLeft) {
                                chanceLeft = false;
                            } else {
                                validTilBoundary = false;
                                break;
                            }
                        }

                        left--;
                        right++;
                    }

                    if (validTilBoundary && !chanceLeft) {
                        horzAllFound.add(j);
                        foundInRowWithDifference.add(j);
                    }
                }
            }

            horzFoundWithDifference.add(foundInRowWithDifference);
        }

        for (Integer i : horzAllFound) {
            int count = 0;
            int countOffBy1 = 0;
            for (int j = 0; j < horzFoundByIndex.size(); j++) {
                if (horzFoundByIndex.get(j).contains(i)) {
                    count++;
                }

                if (horzFoundWithDifference.get(j).contains(i)) {
                    countOffBy1++;
                }
            }

            if (count == horzFoundByIndex.size() - 1 && countOffBy1 > 0) {
                score = i + 1;
            }
        }


        //vertical check - search each row
        Set<Integer> vertAllFound = new HashSet<>();
        List<Set<Integer>> vertFoundByIndex = new ArrayList<>();
        for (int i = 0; i < grid.get(0).size(); i++) {
            Set<Integer> foundInCol = new HashSet<>();
            for (int j = 0; j < grid.size() - 1; j++) {
                //grid.get(j).get(i)
                if (grid.get(j).get(i) == grid.get(j + 1).get(i)) {
                    //expand search up and down until hits boundary
                    int above = j;
                    int below = j + 1;
                    boolean validTilBoundary = true;

                    while (above > -1 && below < grid.size()) {
                        if (grid.get(above).get(i) != grid.get(below).get(i)) {
                            validTilBoundary = false;
                            break;
                        }

                        above--;
                        below++;
                    }

                    if (validTilBoundary) {
                        vertAllFound.add(j);
                        foundInCol.add(j);
                    }
                }
            }
            vertFoundByIndex.add(foundInCol);
        }


        //vertical imperfect
        List<Set<Integer>> vertFoundWithDifference = new ArrayList<>();
        for (int i = 0; i < grid.get(0).size(); i++) {
            Set<Integer> foundInCol = new HashSet<>();
            boolean chanceLeft = true;
            for (int j = 0; j < grid.size() - 1; j++) {
                //grid.get(j).get(i)
                char top = grid.get(j).get(i);
                char bottom = grid.get(j + 1).get(i);
                if (grid.get(j).get(i) == grid.get(j + 1).get(i) || chanceLeft) {

                    //expand search up and down until hits boundary
                    int above = j;
                    int below = j + 1;
                    boolean validTilBoundary = true;

                    while (above > -1 && below < grid.size()) {
                        if (grid.get(above).get(i) != grid.get(below).get(i)) {
                            if (chanceLeft) {
                                chanceLeft = false;
                            } else {
                                validTilBoundary = false;
                                break;
                            }
                        }

                        above--;
                        below++;
                    }

                    if (validTilBoundary && !chanceLeft) {
                        vertAllFound.add(j);
                        foundInCol.add(j);
                    }
                }
            }
            vertFoundWithDifference.add(foundInCol);
        }

        //vertical imperfect search
        for (Integer i : vertAllFound) {

            int count = 0;
            int countOffBy1 = 0;
            for (int j = 0; j < vertFoundByIndex.size(); j++) {
                if (vertFoundByIndex.get(j).contains(i)) {
                    count++;
                }

                if (vertFoundWithDifference.get(j).contains(i)) {
                    countOffBy1++;
                }
            }

            if (count == vertFoundByIndex.size() - 1 && countOffBy1 > 0) {
                score = (i + 1) * 100;
            }
        }

        System.out.println("score found: " + score);

        return score;
    }

    public static Long part1() {
        List<String> inputs = readFile();
        List<List<String>> grids = processInput(inputs);
        Long sum = 0L;
        for (List<String> grid : grids) {
            sum += findMirror(grid);
        }

        return sum;
    }

    public static Long part2() {
        List<String> inputs = readFile();
        List<List<String>> grids = processInput(inputs);
        Long sum = 0L;
        for (List<String> grid : grids) {
            sum += findMirrorPart2(grid);
        }

        return sum;
    }


    public static void main(String[] args) {
        Long result = part1();
        System.out.println("result: " + result);

        Long result2 = part2();
        System.out.println("result2: " + result2);
    }
}
