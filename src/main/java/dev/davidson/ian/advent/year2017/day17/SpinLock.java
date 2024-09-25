package dev.davidson.ian.advent.year2017.day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpinLock {
    private static final String INPUT_PATH = "adventOfCode/2017/day17/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day17/sample.txt";
    private static final Integer PART1_FINISH = 2017;
    private static final Integer PART2_FINISH = 50_000_000;


    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        Integer skipDistance = readFile(INPUT_PATH);
        log.info("Part1: {}", spinLock.part1(skipDistance));
        log.info("Part2: {}", spinLock.part2(skipDistance));
    }

    private static Integer readFile(final String path) {
        ClassLoader cl = SpinLock.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(path)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return Integer.parseInt(scanner.nextLine());
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }

    public Integer part1(final Integer skipDistance) {
        List<Integer> state = new ArrayList<>(60000);
        state.add(0);
        int currentIndex = 0;
        for (int i = 1; i <= PART1_FINISH; i++) {
            currentIndex = (currentIndex + skipDistance) % state.size();
            state.add(currentIndex + 1, i);
            currentIndex++;
        }

        return state.get((state.indexOf(PART1_FINISH) + 1) % state.size());
    }

    public Integer part2(final Integer skipDistance) {
        int currentIndex = 0;
        int size = 1;
        int res = 0;
        for (int i = 1; i <= PART2_FINISH; i++) {
            currentIndex = (currentIndex + skipDistance) % size;
            if (currentIndex == 0) {
                res = i;
            }

            size++;
            currentIndex++;
        }

        return res;
    }
}
