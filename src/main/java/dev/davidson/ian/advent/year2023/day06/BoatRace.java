package dev.davidson.ian.advent.year2023.day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

public class BoatRace {

    private static final String INPUT_PATH = "adventOfCode/2023/day06/input.txt";
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day06/input-sample.txt";

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = BoatRace.class.getClassLoader();
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

    private static List<RaceRecord> interpretRecordsPart1(List<String> inputs) {
        List<Long> times = Arrays.stream(inputs.get(0).substring(6).trim().split("\\s+")).map(Long::parseLong).toList();
        List<Long> distances = Arrays.stream(inputs.get(1).substring(11).trim().split("\\s+")).map(Long::parseLong).toList();

        List<RaceRecord> raceRecords = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            raceRecords.add(new RaceRecord(times.get(i), distances.get(i)));
        }

        return raceRecords;
    }

    private static RaceRecord interpretRecordsPart2(List<String> inputs) {
        List<String> times = Arrays.stream(inputs.get(0).substring(6).trim().split("\\s+")).toList();
        StringBuilder timeString = new StringBuilder();
        for (String temp : times) {
            timeString.append(temp);
        }
        long time = Long.parseLong(timeString.toString());

        List<String> distances = Arrays.stream(inputs.get(1).substring(11).trim().split("\\s+")).toList();
        StringBuilder distanceString = new StringBuilder();
        for (String temp : distances) {
            distanceString.append(temp);
        }
        long distance = Long.parseLong(distanceString.toString());

        return new RaceRecord(time, distance);
    }


    private static Long computeWinningRaces(RaceRecord raceRecord) {
        Long winningCombinations = 0L;
        for (int i = 0; i < Math.toIntExact(raceRecord.getTime()) + 1; i++) {
            Long timeRemaining = raceRecord.getTime() - i;
            if (applyDistanceFormula((long) i, timeRemaining) > raceRecord.getDistance()) {
                winningCombinations++;
            }
        }

        return winningCombinations;
    }

//    private static Long findMaxCharge(RaceRecord raceRecord){
//
//        Long right = raceRecord.getTime();
//        Long left = 0L;
//        while(left < right){
//            Long leftThirdIndex = left + (right - left) / 3;
//            Long leftThirdDistance = applyDistanceFormula(raceRecord, leftThirdIndex);
//            Long rightThirdIndex = right - (right - left) / 3;
//            Long rightThirdDistance = applyDistanceFormula(raceRecord, rightThirdIndex);
//
//            System.out.println("LI: " + leftThirdIndex + " LD: " + leftThirdDistance + "; MI:" + ((left + right) / 2) + " MD:" + applyDistanceFormula(raceRecord, (left + right) / 2)   +  "; RI: " + rightThirdIndex + " RD: " + rightThirdDistance);
//
//            if(leftThirdDistance < rightThirdDistance){
//                left = leftThirdIndex +  1;
//            } else {
//                right = rightThirdIndex  - 1;
//            }
//
//        }
//
//
//    return (left + right) / 2;
//    }

    private static Long binarySearch(RaceRecord raceRecord, Long low, Long high) {
        Long recordDistance = raceRecord.getDistance();
        long middle = 0L;
        while (low <= high) {
            middle = low + ((high - low) / 2);
            long tempDistance = applyDistanceFormula(raceRecord, middle);
            if (tempDistance < recordDistance) {
                low = middle + 1;
            } else if (tempDistance > recordDistance) {
                high = middle - 1;
            } else {
                break;
            }

        }
        return middle;
    }

    private static Long computeWinningRacesPart2(RaceRecord raceRecord) {
        Long timeMidPoint = raceRecord.getTime() / 2;

        //quick sanity:
        System.out.println("mid point distance: " + applyDistanceFormula(timeMidPoint, raceRecord.getTime() - timeMidPoint) + " distance to beat: " + raceRecord.getDistance());

        Long startIndex = binarySearch(raceRecord, 0L, raceRecord.getTime() / 2);
        Long endIndex = raceRecord.getTime() - startIndex + 1;
        return endIndex - startIndex;
    }

    private static Long applyDistanceFormula(Long chargeTime, Long timeRemaining) {
        return chargeTime * timeRemaining;
    }

    private static Long applyDistanceFormula(RaceRecord raceRecord, Long chargeTime) {
        return chargeTime * (raceRecord.getTime() - chargeTime);
    }

    public static void main(String[] args) {
        BoatRace boatRace = new BoatRace();
        System.out.println("answer: " + boatRace.part2());

    }

    public Long part1() {
        List<String> inputs = readFile();
        List<RaceRecord> raceRecords = interpretRecordsPart1(inputs);
        Long combinations = 0L;
        Long finiteProduct = 1L;
        for (RaceRecord raceRecord : raceRecords) {
            combinations = computeWinningRaces(raceRecord);
            finiteProduct *= combinations;
        }

        return finiteProduct;
    }

    public Long part2() {
        List<String> inputs = readFile();
        RaceRecord raceRecord = interpretRecordsPart2(inputs);
        System.out.println("Race Record: " + raceRecord);

        return computeWinningRacesPart2(raceRecord);
    }

    @AllArgsConstructor
    @Data
    @ToString
    public static class RaceRecord {
        private long time;
        private long distance;
    }

}
