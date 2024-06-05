package dev.davidson.ian.advent.year2015.day17;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class NoSuchThingAsTooMuch {

    private static final String INPUT_PATH = "adventOfCode/2015/day17/input.txt";

    public static void main(String[] args) {
        NoSuchThingAsTooMuch noSuchThingAsTooMuch = new NoSuchThingAsTooMuch();
        log.info("Part1: {}", noSuchThingAsTooMuch.part1());
        log.info("Part2: {}", noSuchThingAsTooMuch.part2());
    }

    public int part1() {
        List<Integer> input = readFile();
        Collections.sort(input);

        List<Integer> sums = new ArrayList<>();
        sums.add(0);

        int count = 0;
        for (Integer value : input) {
            List<Integer> nexts = new ArrayList<>();
            for (Integer currentSum : sums) {
                if (value + currentSum < 150) {
                    nexts.add(value + currentSum);
                } else if (value + currentSum == 150) {
                    count++;
                }
            }
            sums.addAll(nexts);
        }

        return count;
    }

    public int part2() {
        List<Integer> input = readFile();
        Collections.sort(input);

        List<EggnogComposition> eggnogCompositions = new ArrayList<>();
        eggnogCompositions.add(EggnogComposition.newEggnogComposition(new ArrayList<>()));

        List<EggnogComposition> resultList = new ArrayList<>();
        int minSize = Integer.MAX_VALUE;

        for (Integer value : input) {
            List<EggnogComposition> tempList = new ArrayList<>();
            for (EggnogComposition current : eggnogCompositions) {
                List<Integer> valueList = new ArrayList<>(current.containers());
                valueList.add(value);
                EggnogComposition eggnogComposition = EggnogComposition.newEggnogComposition(valueList);
                if (eggnogComposition.total() < 150) {
                    tempList.add(eggnogComposition);
                } else if (eggnogComposition.total() == 150) {
                    minSize = Math.min(eggnogComposition.size(), minSize);
                    resultList.add(eggnogComposition);
                }
            }

            eggnogCompositions.addAll(tempList);

        }

        int count = 0;
        for (EggnogComposition eggnogComposition : resultList) {
            if (eggnogComposition.size() == minSize) {
                count++;
            }
        }

        return count;
    }


    private List<Integer> readFile() {
        List<Integer> inputs = new ArrayList<>();

        ClassLoader cl = NoSuchThingAsTooMuch.class.getClassLoader();
        File file = new File(cl.getResource(INPUT_PATH).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputs.add(Integer.parseInt(scanner.nextLine().trim()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        return inputs;
    }


}
