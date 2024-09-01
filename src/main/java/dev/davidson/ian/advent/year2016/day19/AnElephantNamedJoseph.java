package dev.davidson.ian.advent.year2016.day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnElephantNamedJoseph {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day19/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day19/input.txt";

    public static void main(String[] args) {
        AnElephantNamedJoseph anElephantNamedJoseph = new AnElephantNamedJoseph();
        log.info("Part1: {}", anElephantNamedJoseph.part1());
        log.info("Part2: {}", anElephantNamedJoseph.part2());
    }

    public int part1() {
        int circleSize = readFile();
        Set<Integer> elvesLeft = new HashSet<>();
        for (int i = 0; i < circleSize; i++) {
            elvesLeft.add(i);
        }

        int i = 0;
        while (elvesLeft.size() > 1) {
            i = i % circleSize;
            if (elvesLeft.contains(i)) {
                for (int j = i + 1; j < Integer.MAX_VALUE; j++) {
                    j = j % circleSize;
                    if (j != i && elvesLeft.contains(j)) {
//                        log.info("i: {}, j: {}", i, j);
                        elvesLeft.remove(j);
                        break;
                    }
                }
            }

            i++;
        }

        return elvesLeft.stream().toList().getFirst() + 1;
    }

    public long part2() {
        int circleSize = readFile();
        List<Integer> elvesLeft = new ArrayList<>();
        for (int i = 0; i < circleSize; i++) {
            elvesLeft.add(i);
        }

        int i = 0;
        while (elvesLeft.size() > 1) {
            i = i % elvesLeft.size();
            int j = (i + elvesLeft.size()/2) % elvesLeft.size();

            elvesLeft.remove(j);
            i++;
        }

        return elvesLeft.getFirst() + 1;
    }

    private Integer readFile() {
        ClassLoader cl = AnElephantNamedJoseph.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return Integer.parseInt(scanner.nextLine());
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }
}
