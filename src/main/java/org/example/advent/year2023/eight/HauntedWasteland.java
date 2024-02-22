package org.example.advent.year2023.eight;

import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class HauntedWasteland {

    private static final String INPUT_PATH = "/Users/Ian/Documents/PersonalProjects/interviewprep/src/main/java/org/example/advent/year2023/eight/input.txt";
//    private static final String INPUT_PATH = "/Users/Ian/Documents/PersonalProjects/interviewprep/src/main/java/org/example/advent/year2023/eight/input-sample.txt";

    private String start_key = "AAA";
    private String final_key = "ZZZ";


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

    private static List<Direction> inputToDirections(String line) {
        List<Direction> directions = new ArrayList<>();

        for (Character ch : line.toCharArray()) {
            Direction direction = Direction.valueOf(String.valueOf(ch));
            directions.add(direction);
        }
        return directions;
    }

    private static Map<String, Map<Direction, String>> inputToPuzzleMap(List<String> inputLines) {
        Map<String, Map<Direction, String>> map = new HashMap<>();
        for (int i = 2; i < inputLines.size(); i++) {
            String state = inputLines.get(i).substring(0, 3);
            String left = inputLines.get(i).substring(7, 10);
            String right = inputLines.get(i).substring(12, 15);
            Map<Direction, String> tempMap = new HashMap<>();
            tempMap.put(Direction.L, left);
            tempMap.put(Direction.R, right);
            map.put(state, tempMap);
//            puzzleCoordinates.add(new PuzzleCoordinate(state, left, right));
        }

        return map;
    }

//    private static List<PuzzleCoordinate> inputToPuzzleCoordinates(List<String> inputLines) {
//        //AAA = (BBB, CCC)
//        List<PuzzleCoordinate> puzzleCoordinates = new ArrayList<>();
//        String state, left, right;
//        for (int i = 2; i < inputLines.size(); i++) {
//            state = inputLines.get(i).substring(0, 3);
//            left = inputLines.get(i).substring(7, 10);
//            right = inputLines.get(i).substring(12, 15);
//            puzzleCoordinates.add(new PuzzleCoordinate(state, left, right));
//        }
//
//        return puzzleCoordinates;
//    }

    public Long part1() {
        List<String> inputs = readFile();
        List<Direction> directions = inputToDirections(inputs.get(0).trim());
//        List<PuzzleCoordinate> puzzleCoordinates = inputToPuzzleCoordinates(inputs);
        Map<String, Map<Direction, String>> coordinateMap = inputToPuzzleMap(inputs);

        Long countSteps = 0L;
        String currentState = start_key;
        while (!currentState.equals(final_key)) {
            for (Direction direction : directions) {
                currentState = coordinateMap.get(currentState).get(direction);
                countSteps++;
                if (currentState.equals(final_key)) {
                    break;
                }
            }
        }


//        String finalState = puzzleCoordinates.get(puzzleCoordinates.size() - 1).getState();
//        String currentState = puzzleCoordinates.get(0).getState();
//        Long countSteps = 0L;
//        while (!Objects.equals(currentState, finalState)) {
//            for (Direction direction : directions) {
//                currentState = puzzleMap.get(currentState).getDirections().get(direction);
//                countSteps++;
//                if (currentState.equals(finalState)) {
//                    break;
//                }
//            }
//        }

        /*
AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)
         */
        return countSteps;
    }

    private Long processState(String start, Map<String, Map<Direction, String>> coordinateMap, List<Direction> directions) {
        long countSteps = 0;
        String currentState = start;
        while (!currentState.endsWith("Z")) {
            for (Direction direction : directions) {
                currentState = coordinateMap.get(currentState).get(direction);
                countSteps++;
                if (currentState.endsWith("Z")) {
                    break;
                }
            }
        }

        return countSteps;
    }

    private Long processCounts(List<Long> counts){
        long gcd = 1;
        for(int i  = 1; i < counts.size(); i++){
            if(i == 1){
                gcd = gcd(counts.get(0), counts.get(1));
            } else{
                gcd  = gcd(gcd, counts.get(i));
            }
        }

        //LCM(a, b) = (a x b) / GCD(a,b) or GCD(a,b) = (a x b) / LCM(a, b)
        long lcm = counts.get(0);
        for(int j = 1; j < counts.size(); j++){
            lcm *= counts.get(j);
            lcm /= gcd;
        }

        return lcm;
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long t = a;
            a = b;
            b = t % b;
        }
        return a;
    }


    public Long part2() {
        List<String> inputs = readFile();
        List<Direction> directions = inputToDirections(inputs.get(0).trim());
//        List<PuzzleCoordinate> puzzleCoordinates = inputToPuzzleCoordinates(inputs);
        Map<String, Map<Direction, String>> coordinateMap = inputToPuzzleMap(inputs);

        List<Long> counts = new ArrayList<>();
        List<String> startStates = new ArrayList<>();
        coordinateMap.keySet().forEach(key -> {
            if (key.endsWith("A")) {
                startStates.add(key);
            }
        });

        for (String startState : startStates) {
            counts.add(processState(startState, coordinateMap, directions));
        }

        //compute gcd of all members of counts
        return processCounts(counts);
    }

    public static void main(String[] args) {
        HauntedWasteland hauntedWasteland = new HauntedWasteland();
//        Long count = hauntedWasteland.part1();
//        System.out.println("Count: " + count);

        Long count = hauntedWasteland.part2();
        System.out.println("Count: " + count);
    }

    @Data
    public static class PuzzleCoordinate {

        private final String state;
        private final Map<Direction, String> directions;

        public PuzzleCoordinate(String state, String left, String right) {
            this.state = state;
            this.directions = Map.of(Direction.L, left, Direction.R, right);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PuzzleCoordinate that = (PuzzleCoordinate) o;
            return Objects.equals(state, that.state);
        }

        @Override
        public int hashCode() {
            return Objects.hash(state);
        }
    }

    public enum Direction {
        L, R;
    }
}
