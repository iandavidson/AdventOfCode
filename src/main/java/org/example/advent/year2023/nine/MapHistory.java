package org.example.advent.year2023.nine;

import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MapHistory {

    private static final String INPUT_PATH = "/Users/Ian/Documents/PersonalProjects/interviewprep/src/main/java/org/example/advent/year2023/nine/input.txt";
//    private static final String INPUT_PATH = "/Users/Ian/Documents/PersonalProjects/interviewprep/src/main/java/org/example/advent/year2023/nine/input-sample.txt";

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            File file = new File(INPUT_PATH);
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

    private static List<History> processInput(List<String> inputLines) {
        List<History> histories = new ArrayList<>();
        for (String inputLine : inputLines) {
            List<Integer> history = new ArrayList<>();
            Arrays.stream(inputLine.split("\\s+")).forEach(val -> history.add(Integer.parseInt(val)));
            histories.add(new History(history));
        }

        return histories;
    }
/*
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
 */

    public static Long part1() {
        List<String> inputLines = readFile();
        List<History> histories = processInput(inputLines);

        long sum = 0L;

        for (History history : histories) {
            sum += history.getHiddenRightSide();
        }

        return sum;
    }

    public static Long part2() {
        List<String> inputLines = readFile();
        List<History> histories = processInput(inputLines);

        long sum = 0L;

        for (History history : histories) {
            sum += history.getHiddenLeftSide();
        }

        return sum;
    }


    public static void main(String[] args) {
        MapHistory mapHistory = new MapHistory();
        Long result = mapHistory.part1();
        System.out.println("result: " + result);

        result = mapHistory.part2();
        System.out.println("result: " + result);
    }

    @Data
    private static class History {
        List<Integer> initialList;
        List<List<Integer>> fullHistory; // includes initial list as first element

        //just going to hack this one to bits by leveraging a hashmap that goes from f(row) = newly added left most hidden value
        Map<Integer, Integer> leftSideValuesMap;

        public History(List<Integer> initialList) {
            this.initialList = initialList;
            createHistory();
            createLeftSideValuesMap();
        }


        private void createHistory() {
            List<List<Integer>> newFullHistory = new ArrayList<>();
            newFullHistory.add(initialList);

            boolean containsNonZeros = true;
            int historyIndex = 0;

            while (containsNonZeros) {
                containsNonZeros = false;
                List<Integer> temp = new ArrayList<>();
                for (int i = 0; i < newFullHistory.get(historyIndex).size() - 1; i++) {
                    int difference = newFullHistory.get(historyIndex).get(i + 1) - newFullHistory.get(historyIndex).get(i);
                    if (difference != 0) {
                        containsNonZeros = true;
                    }
                    temp.add(difference);
                }
                newFullHistory.add(temp);
                historyIndex++;
            }

            //don't forget to add the extra zero on final row
            newFullHistory.get(newFullHistory.size() - 1).add(0);

//          add right side hidden values:
            for (int i = newFullHistory.size() - 1; i > 0; i--) {
                newFullHistory.get(i - 1).add(newFullHistory.get(i).get(newFullHistory.get(i).size() - 1) + newFullHistory.get(i - 1).get(newFullHistory.get(i - 1).size() - 1));
            }
            this.fullHistory = newFullHistory;
        }

        private void createLeftSideValuesMap(){
            Map<Integer, Integer> map  = new HashMap<>();
            //add zero to bottom most row
            map.put(this.fullHistory.size() - 1, 0);

            for (int i = this.fullHistory.size() - 1; i > 0; i--) {
                int temp = this.fullHistory.get(i - 1).get(0) -  map.get(i);
                map.put(i-1, temp);
            }

            this.leftSideValuesMap = map;
        }

        public int getHiddenRightSide() {
            return this.fullHistory.get(0).get(fullHistory.get(0).size()-1);
        }

        public int getHiddenLeftSide(){
            return leftSideValuesMap.get(0);
        }
    }
}
