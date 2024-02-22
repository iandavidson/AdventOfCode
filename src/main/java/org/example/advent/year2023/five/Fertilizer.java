package org.example.advent.year2023.five;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Fertilizer {

    private static final String INPUT_PATH = "/Users/Ian/Documents/PersonalProjects/interviewprep/src/main/java/org/example/advent/year2023/five/input.txt";
//        private static final String INPUT_PATH = "/Users/Ian/Documents/PersonalProjects/interviewprep/src/main/java/org/example/advent/year2023/five/input-sample.txt";
    private Long currentIndex = 0L;

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

    private Almanac processInput(List<String> inputLines) {

        //first line
        String seedsRaw = inputLines.get(Math.toIntExact(currentIndex));

        List<Long> seedsInitial = Arrays.stream(seedsRaw.substring(6).trim().split("\\s+")).map(Long::parseLong).toList();

        currentIndex++;


        while (inputLines.get(Math.toIntExact(currentIndex)).isBlank()) {
            currentIndex++;
        }

        List<Range> seedToSoil = processMap(inputLines);
        List<Range> soilToFertilizer = processMap(inputLines);
        List<Range> fertilizerToWater = processMap(inputLines);
        List<Range> waterToLight = processMap(inputLines);
        List<Range> lightToTemperature = processMap(inputLines);
        List<Range> temperatureToHumidity = processMap(inputLines);
        List<Range> humidityToLocation = processMap(inputLines);
        return new Almanac(seedsInitial, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation);
    }

    private List<Range> processMap(List<String> inputLines) {
        List<Range> rangeList = new ArrayList<>();
        while (currentIndex < inputLines.size() && !inputLines.get(Math.toIntExact(currentIndex)).isBlank()) {
            if (Character.isDigit(inputLines.get(Math.toIntExact(currentIndex)).charAt(0))) {
                List<Long> seedToSoilRange = Arrays.stream(inputLines.get(Math.toIntExact(currentIndex)).trim().split("\\s+"))
                        .map(Long::parseLong).toList();

                rangeList.add(new Range(seedToSoilRange.get(0), seedToSoilRange.get(1), seedToSoilRange.get(2)));

            }
            currentIndex++;
        }
        currentIndex++;
        return rangeList;
    }

    private static long findConversion(Long source, List<Range> ranges) {
        for (Range range : ranges) {
            if (range.sourceValueSupported(source)) {
                return range.sourceToDestination(source);
            }
        }
        return source;
    }

    public long part2() {
        List<String> inputs = readFile();
        currentIndex = 0L;
        Almanac almanac = processInput(inputs);

        Long temp = 0l;
        Long min = Long.MAX_VALUE;
        for (int i = 0; i < almanac.getSeeds().size(); i += 2) {

            long seedIndex = almanac.getSeeds().get(i);

            while(seedIndex < almanac.getSeeds().get(i) + almanac.getSeeds().get(i+1)){
                long seed = seedIndex;
//                System.out.print("seed: " + seed);
                temp = findConversion(seed, almanac.getSeedToSoil());
//                System.out.print(" ; soil: " + temp);
                temp = findConversion(temp, almanac.getSoilToFertilizer());
//                System.out.print(" ; fert: " + temp);
                temp = findConversion(temp, almanac.getFertilizerToWater());
//                System.out.print(" ; water: " + temp);
                temp = findConversion(temp, almanac.getWaterToLight());
//                System.out.print(" ; light: " + temp);
                temp = findConversion(temp, almanac.getLightToTemperature());
//                System.out.print(" ; temp: " + temp);
                temp = findConversion(temp, almanac.getTemperatureToHumidity());
//                System.out.print(" ; humid: " + temp);
                temp = findConversion(temp, almanac.getHumidityToLocation());
//                System.out.println(" ; location: " + temp);
                if (temp < min) {
                    min = temp;
                }

                seedIndex++;
            }

        }

        return min;
    }

    public long part1() {
        List<String> inputs = readFile();
        Almanac almanac = processInput(inputs);

        Long temp = 0l;
        Long min = Long.MAX_VALUE;
        for (Long seed : almanac.getSeeds()) {
            System.out.print("seed: " + seed);
            temp = findConversion(seed, almanac.getSeedToSoil());
            System.out.print(" ; soil: " + temp);
            temp = findConversion(temp, almanac.getSoilToFertilizer());
            System.out.print(" ; fert: " + temp);
            temp = findConversion(temp, almanac.getFertilizerToWater());
            System.out.print(" ; water: " + temp);
            temp = findConversion(temp, almanac.getWaterToLight());
            System.out.print(" ; light: " + temp);
            temp = findConversion(temp, almanac.getLightToTemperature());
            System.out.print(" ; temp: " + temp);
            temp = findConversion(temp, almanac.getTemperatureToHumidity());
            System.out.print(" ; humid: " + temp);
            temp = findConversion(temp, almanac.getHumidityToLocation());
            System.out.println(" ; location: " + temp);

            if (temp < min) {
                min = temp;
            }
        }

        return min;
    }

    public static void main(String[] args) {
        Fertilizer fertilizer = new Fertilizer();
//        System.out.println("Min location value: " + fertilizer.part1());
        System.out.println("Min location value part 2: " + fertilizer.part2());
    }

    @AllArgsConstructor
    @Data
    public static class Almanac {
        private List<Long> seeds;

        private List<Range> seedToSoil;
        private List<Range> soilToFertilizer;
        private List<Range> fertilizerToWater;
        private List<Range> waterToLight;
        private List<Range> lightToTemperature;
        private List<Range> temperatureToHumidity;
        private List<Range> humidityToLocation;
    }

    @AllArgsConstructor
    @Data
    public static class Range {
        private Long destinationRangeStart;
        private Long sourceRangeStart;
        private Long sourceRangeLength;


        public boolean sourceValueSupported(Long source) {
            return sourceRangeStart <= source && source < sourceRangeStart + sourceRangeLength;
        }

        public Long sourceToDestination(Long source) {
            if (sourceValueSupported(source)) {
                return source - sourceRangeStart + destinationRangeStart;
            } else {
                return source;
            }
        }
    }
}
