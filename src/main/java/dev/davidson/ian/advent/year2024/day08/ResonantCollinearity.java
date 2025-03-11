package dev.davidson.ian.advent.year2024.day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResonantCollinearity {

    private static final String SAMPLE_INPUT = "adventOfCode/2024/day08/sample.txt";
    private static final String PART_2_SAMPLE_INPUT = "adventOfCode/2024/day08/part2Sample.txt";
    private static final String REAL_INPUT = "adventOfCode/2024/day08/real.txt";

    public static void main(String[] args) {
        Grid sampleGrid = readFile(SAMPLE_INPUT);
        Grid part2SampleGrid = readFile(PART_2_SAMPLE_INPUT);
        Grid realGrid = readFile(REAL_INPUT);

        ResonantCollinearity resonantCollinearity = new ResonantCollinearity();
        log.info("Part1:");
        log.info("sample input: {}", resonantCollinearity.part1(sampleGrid));
        log.info("real input: {}", resonantCollinearity.part1(realGrid));
        log.info("Part2:");
        log.info("sample input: {}", resonantCollinearity.part2(sampleGrid));
        log.info("second sample input: {}", resonantCollinearity.part2(part2SampleGrid));
        log.info("real input: {}", resonantCollinearity.part2(realGrid));
    }

    private static Grid readFile(final String filePath) {
        List<String> rows = new ArrayList<>();
        ClassLoader cl = ResonantCollinearity.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                rows.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read input file");
        }

        return Grid.newGrid(rows);
    }

    public long part1(final Grid grid) {
        Set<Coordinate> antinodes = new HashSet<>();

        for (char channel : grid.frequencies().keySet()) {
            antinodes.addAll(findAntinodes(channel, grid));
        }

        return antinodes.size();
    }

    public long part2(final Grid grid) {
        Set<Coordinate> antinodes = new HashSet<>();

        for (char channel : grid.frequencies().keySet()) {
            antinodes.addAll(findAntinodesPart2(channel, grid));
        }

        grid.frequencies().values().forEach(freqList ->
                antinodes.addAll(freqList.stream().map(Frequency::coordinate).collect(Collectors.toSet())));

        return antinodes.size();
    }

    private Set<Coordinate> findAntinodes(final char channel, final Grid grid) {
        Set<Coordinate> antinodes = new HashSet<>();
        List<Frequency> frequencies = grid.frequencies().get(channel);

        for (int i = 0; i < frequencies.size() - 1; i++) {
            for (int j = i + 1; j < frequencies.size(); j++) {
                Coordinate first = frequencies.get(i).coordinate();
                Coordinate second = frequencies.get(j).coordinate();

                Coordinate leftAnt = new Coordinate(first.r() + first.r() - second.r(),
                        first.c() + first.c() - second.c());
                if (grid.isInBounds(leftAnt)) {
//                    log.info("Original: {}, added: {}", first, leftAnt);
                    antinodes.add(leftAnt);
                }

                Coordinate rightAnt = new Coordinate(second.r() + second.r() - first.r(),
                        second.c() + second.c() - first.c());
                if (grid.isInBounds(rightAnt)) {
//                    log.info("Original: {}, added: {}", second, rightAnt);
                    antinodes.add(rightAnt);
                }
            }
        }
        return antinodes;
    }

    private Set<Coordinate> findAntinodesPart2(final char channel, final Grid grid) {
        Set<Coordinate> antinodes = new HashSet<>();
        List<Frequency> frequencies = grid.frequencies().get(channel);

        for (int i = 0; i < frequencies.size() - 1; i++) {
            for (int j = i + 1; j < frequencies.size(); j++) {
                Coordinate first = frequencies.get(i).coordinate();
                Coordinate second = frequencies.get(j).coordinate();

                int rowDiff = first.r() - second.r();
                int colDiff = first.c() - second.c();
                Coordinate leftAnt = new Coordinate(first.r() + rowDiff,
                        first.c() + colDiff);
                while (grid.isInBounds(leftAnt)) {
//                    log.info("Adding leftNode: {}", leftAnt);
                    antinodes.add(leftAnt);
                    leftAnt = new Coordinate(leftAnt.r() + rowDiff,
                            leftAnt.c() + colDiff);
                }

                rowDiff = second.r() - first.r();
                colDiff = second.c() - first.c();
                Coordinate rightAnt = new Coordinate(second.r() + rowDiff,
                        second.c() + colDiff);
                while (grid.isInBounds(rightAnt)) {
//                    log.info("Adding rightNode: {}", rightAnt);
                    antinodes.add(rightAnt);
                    rightAnt = new Coordinate(rightAnt.r() + rowDiff,
                            rightAnt.c() + colDiff);
                }
            }
        }
        return antinodes;
    }

}
