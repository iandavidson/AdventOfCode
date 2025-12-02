package dev.davidson.ian.advent.year2024.day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiskFragmenter {

    private static final String SAMPLE_INPUT = "adventOfCode/2024/day09/sample.txt.txt";
    private static final String REAL_INPUT = "adventOfCode/2024/day09/real.txt";

    public static void main(String[] args) {
        String sampleInput = readFile(SAMPLE_INPUT);
        String realInput = readFile(REAL_INPUT);

        DiskFragmenter diskFragmenter = new DiskFragmenter();

        log.info("Part1: ");
        log.info("Sample: {}", diskFragmenter.part1(sampleInput));
        log.info("Real: {}", diskFragmenter.part1(realInput));
    }

    private static String readFile(final String filepath) {
        ClassLoader cl = DiskFragmenter.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filepath)).getFile());
        try (Scanner scanner = new Scanner(file)) {
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file");
        }
    }

    public long part1(final String diskMap) {
        List<Block> blocks = getBlocks(diskMap);

        return 0L;

    }

    private List<Block> getBlocks(final String diskMap) {
        List<Block> blocks = new ArrayList<>();

        for (int i = 0; i < diskMap.length(); i++) {
            if (i % 2 == 0) {
                blocks.add(new Block(i / 2, diskMap.charAt(i), BLOCKTYPE.FILE));
            } else {
                blocks.add(new Block(-1, diskMap.charAt(i), BLOCKTYPE.SPACE));
            }
        }

        return blocks;
    }
}
