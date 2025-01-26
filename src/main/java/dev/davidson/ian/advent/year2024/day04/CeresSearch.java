package dev.davidson.ian.advent.year2024.day04;

import dev.davidson.ian.advent.year2024.day03.MullItOver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CeresSearch {

    private static final String SAMPLE_PATH = "adventOfCode/2024/day04/sample.txt";
    private static final String REAL_PATH = "adventOfCode/2024/day04/real.txt";
    private static final List<Character> MAS = List.of('M', 'A', 'S');

    private static final List<List<Relative>> X_MAS = List.of(
//    private static final List<Map<Character, int[]>> X_MAS = List.of(
//            M.M
//            .A.
//            S.S
            List.of(
                    new Relative('M', new int[]{-1, -1}),
                    new Relative('M', new int[]{-1, 1}),
                    new Relative('S', new int[]{1, -1}),
                    new Relative('S', new int[]{1, 1})
            ),

//            M.S
//            .A.
//            M.S
            List.of(
                    new Relative('M', new int[]{-1, -1}),
                    new Relative('S', new int[]{-1, 1}),
                    new Relative('M', new int[]{1, -1}),
                    new Relative('S', new int[]{1, 1})
            ),

//            S.S
//            .A.
//            M.M
            List.of(
                    new Relative('S', new int[]{-1, -1}),
                    new Relative('S', new int[]{-1, 1}),
                    new Relative('M', new int[]{1, -1}),
                    new Relative('M', new int[]{1, 1})
            ),

//            S.M
//            .A.
//            S.M
            List.of(
                    new Relative('S', new int[]{-1, -1}),
                    new Relative('M', new int[]{-1, 1}),
                    new Relative('S', new int[]{1, -1}),
                    new Relative('M', new int[]{1, 1})
            )
    );

    public static void main(String[] args) {
        List<String> sample = readFile(SAMPLE_PATH);
        List<String> real = readFile(REAL_PATH);

        CeresSearch ceresSearch = new CeresSearch();
        log.info("Part1 sample: {}", ceresSearch.part1(sample));
        log.info("Part1 real: {}", ceresSearch.part1(real));

        log.info("Part2 sample: {}", ceresSearch.part2(sample));
        log.info("Part2 real: {}", ceresSearch.part2(real));
    }

    private static List<String> readFile(final String filePath) {
        List<String> inputLines = new ArrayList<>();

        ClassLoader cl = MullItOver.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return inputLines;
    }

    public Long part1(final List<String> input) {
        long count = 0L;
        int rMax = input.size();
        int cMax = input.getFirst().length();

        for (int r = 0; r < rMax; r++) {
            for (int c = 0; c < cMax; c++) {
                Character current = input.get(r).charAt(c);
                if (!current.equals('X')) {
                    continue;
                }

                for (DIRECTION dir : DIRECTION.values()) {
                    if (matchInDirection(dir, r, c, rMax, cMax, input)) {
                        count++;
                    }
                }

            }
        }

        return count;
    }

    private boolean matchInDirection(final DIRECTION dir, int r, int c, final int rMax, final int cMax,
                                     final List<String> input) {

        for (Character match : MAS) {

            r += DIRECTION.SHIFTS.get(dir)[0];
            c += DIRECTION.SHIFTS.get(dir)[1];

            if (!inbounds(r, c, rMax, cMax)) {
                return false;
            }

            Character current = input.get(r).charAt(c);

            if (!current.equals(match)) {
                return false;
            }
        }

        return true;
    }

    public Long part2(final List<String> input) {
        long count = 0L;
        int rMax = input.size();
        int cMax = input.getFirst().length();

        for (int r = 0; r < rMax; r++) {
            for (int c = 0; c < cMax; c++) {
                Character current = input.get(r).charAt(c);

                if (!current.equals('A')) {
                    continue;
                }

                for (List<Relative> relativeList : X_MAS) {
                    if (matchWithRelatives(relativeList, r, c, rMax, cMax, input)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private boolean matchWithRelatives(final List<Relative> relatives, final int r, final int c, final int rMax,
                                       final int cMax, final List<String> input) {

        for (Relative relative : relatives) {

            int nr = r + relative.shift()[0];
            int nc = c + relative.shift()[1];

            if (!inbounds(nr, nc, rMax, cMax)) {
                return false;
            }

            Character current = input.get(nr).charAt(nc);

            if (!current.equals(relative.ch())) {
                return false;
            }
        }

        return true;
    }

    private boolean inbounds(int r, int c, int rMax, int cMax) {
        return r >= 0 && c >= 0 && r < rMax && c < cMax;
    }
}
