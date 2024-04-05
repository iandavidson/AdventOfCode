package org.example.advent.year2022.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RucksackReorganization {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day03/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day03/input.txt";

    public static void main(String[] args) {
        RucksackReorganization reorganization = new RucksackReorganization();
        System.out.println("part1: " + reorganization.part1());
        System.out.println("part1: " + reorganization.part2());
    }

    public Long part1() {
        List<String> lines = readInput();
        List<Rucksack> rucksacks = processInputPart1(lines);
        Long count = 0L;

        for (Rucksack rucksack : rucksacks) {
            count += getCommonItemScore(rucksack.getCommonItem());
        }

        return count;
    }

    public Long part2() {
        List<String> lines = readInput();
        List<ElfCrew> elfCrews = processInputPart2(lines);
        Long count = 0L;

        for (ElfCrew elfCrew : elfCrews) {
            count += getCommonItemScore(elfCrew.getBadgeCharacter());
        }

        return count;
    }

    public List<String> readInput() {
        List<String> lines = new ArrayList<>();
        try {
            ClassLoader classLoader = RucksackReorganization.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return lines;
    }

    public List<Rucksack> processInputPart1(List<String> lines) {
        List<Rucksack> rucksacks = new ArrayList<>();
        for (String line : lines) {
            rucksacks.add(Rucksack.newRucksack(line));
        }

        return rucksacks;
    }

    public List<ElfCrew> processInputPart2(List<String> lines) {
        List<ElfCrew> elfCrews = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 3) {
            List<Rucksack> temp = List.of(Rucksack.newRucksack(lines.get(i)), Rucksack.newRucksack(lines.get(i + 1)), Rucksack.newRucksack(lines.get(i + 2)));
            elfCrews.add(ElfCrew.newElfCrew(temp));
        }

        return elfCrews;
    }

    public Long getCommonItemScore(Character commonItem) {
        if (Character.isUpperCase(commonItem)) {
            return (long) (commonItem - 'A' + 27);
        } else {
            return (long) (commonItem - 'a' + 1);
        }
    }
}
