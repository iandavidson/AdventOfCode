package dev.davidson.ian.advent.year2015.day16;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static dev.davidson.ian.advent.year2015.day16.Trait.akitas;
import static dev.davidson.ian.advent.year2015.day16.Trait.cars;
import static dev.davidson.ian.advent.year2015.day16.Trait.cats;
import static dev.davidson.ian.advent.year2015.day16.Trait.children;
import static dev.davidson.ian.advent.year2015.day16.Trait.goldfish;
import static dev.davidson.ian.advent.year2015.day16.Trait.perfumes;
import static dev.davidson.ian.advent.year2015.day16.Trait.pomeranians;
import static dev.davidson.ian.advent.year2015.day16.Trait.samoyeds;
import static dev.davidson.ian.advent.year2015.day16.Trait.trees;
import static dev.davidson.ian.advent.year2015.day16.Trait.vizslas;

@Slf4j
public class AuntSue {
    private static final String INPUT_PATH = "adventOfCode/2015/day16/input.txt";
    public static final EnumMap<Trait, Integer> EXPECTATION_MAP = new EnumMap<>(Trait.class);

    static {
        {
            EXPECTATION_MAP.put(children, 3);
            EXPECTATION_MAP.put(cats, 7);
            EXPECTATION_MAP.put(samoyeds, 2);
            EXPECTATION_MAP.put(pomeranians, 3);
            EXPECTATION_MAP.put(akitas, 0);
            EXPECTATION_MAP.put(vizslas, 0);
            EXPECTATION_MAP.put(goldfish, 5);
            EXPECTATION_MAP.put(trees, 3);
            EXPECTATION_MAP.put(cars, 2);
            EXPECTATION_MAP.put(perfumes, 1);
        }
    }


    public static void main(String[] args) {
        AuntSue auntSue = new AuntSue();
        log.info("Part1: {}", auntSue.part1());
        log.info("Part2: {}", auntSue.part2());
    }

    public int part1() {
        List<AuntComposition> auntCompositions = readFile();

        for (AuntComposition auntComposition : auntCompositions) {
            boolean found = true;
            for (Map.Entry<Trait, Integer> entry : auntComposition.composition().entrySet()) {
                if (!EXPECTATION_MAP.get(entry.getKey()).equals(entry.getValue())) {
                    found = false;
                    break;
                }
            }

            if (found) {
                return auntComposition.id();
            }
        }

        return -1;
    }

    public int part2() {
        List<AuntComposition> auntCompositions = readFile();

        for (AuntComposition auntComposition : auntCompositions) {
            boolean found = true;
            for (Map.Entry<Trait, Integer> entry : auntComposition.composition().entrySet()) {

                switch (entry.getKey()) {
                    case cats, trees -> {
                        if (EXPECTATION_MAP.get(entry.getKey()) >= entry.getValue()) {
                            found = false;
                        }
                    }
                    case pomeranians, goldfish -> {
                        if (EXPECTATION_MAP.get(entry.getKey()) <= entry.getValue()) {
                            found = false;
                        }
                    }
                    default -> {
                        if (!Objects.equals(EXPECTATION_MAP.get(entry.getKey()), entry.getValue())) {
                            found = false;
                        }
                    }
                }

                if (!found) {
                    break;
                }
            }

            if (found) {
                return auntComposition.id();
            }
        }

        return -1;
    }

    private List<AuntComposition> readFile() {
        List<AuntComposition> aunts = new ArrayList<>();

        ClassLoader cl = AuntSue.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                aunts.add(AuntComposition.newAuntComposition(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        return aunts;
    }

}
