package dev.davidson.ian.advent.year2017.day16;

import dev.davidson.ian.advent.year2017.day16.dancer.Dancer;
import dev.davidson.ian.advent.year2017.day16.dancer.ExchangeStep;
import dev.davidson.ian.advent.year2017.day16.dancer.PartnerStep;
import dev.davidson.ian.advent.year2017.day16.dancer.SpinStep;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PermutationPromenade {

    private static final String INPUT_PATH = "adventOfCode/2017/day16/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day16/sample.txt";
    private static final Integer PROGRAMS = 16;
    private static final Integer ONE_BILLION = 1_000_000_000;

    public static void main(String[] args) {
        PermutationPromenade permutationPromenade = new PermutationPromenade();
        List<Dancer> danceSteps = readFile(INPUT_PATH);

        log.info("Part1: {}", permutationPromenade.part1(new ArrayList<>(danceSteps)));
        log.info("Part2: {}", permutationPromenade.part2(new ArrayList<>(danceSteps)));
    }

    private static List<Dancer> readFile(final String filePath) {
        List<Dancer> danceList = new ArrayList<>();

        ClassLoader cl = PermutationPromenade.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            String[] rawDanceMoves = scanner.nextLine().split(",");
            for (String line : rawDanceMoves) {
                char[] chars = line.toCharArray();
                switch (chars[0]) {
                    case 'p' -> {
                        danceList.add(
                                new PartnerStep(
                                        chars[1],
                                        chars[3],
                                        line)
                        );
                    }
                    case 'x' -> {
                        String[] operands = line.split("/");
                        danceList.add(
                                new ExchangeStep(
                                        Integer.parseInt(operands[0].substring(1)),
                                        Integer.parseInt(operands[1]),
                                        line
                                )
                        );
                    }
                    case 's' -> {
                        danceList.add(
                                new SpinStep(Integer.parseInt(line.substring(1)), line)
                        );
                    }
                    default -> {
                        throw new IllegalStateException("Issue interpreting input");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return danceList;
    }

    public String part1(final List<Dancer> danceSteps) {
        List<Character> programs = new ArrayList<>();
        for (int i = 0; i < PROGRAMS; i++) {
            programs.add((char) ('a' + i));
        }

        for (Dancer danceStep : danceSteps) {
            programs = danceStep.applyMove(programs);
        }

        return printSequence(programs);
    }

    public String part2(final List<Dancer> danceSteps) {

        List<Character> programs = new ArrayList<>();
        for (int i = 0; i < PROGRAMS; i++) {
            programs.add((char) ('a' + i));
        }

        //detect cycle
        Map<String, List<Integer>> seenMap = new HashMap<>();
        for (int i = 0; i < 200; i++) {

            for (Dancer danceStep : danceSteps) {
                programs = danceStep.applyMove(programs);
            }

            String currentSequence = printSequence(programs);
            seenMap.putIfAbsent(currentSequence, new ArrayList<>());
            seenMap.get(currentSequence).add(i);
            if (seenMap.get(currentSequence).size() > 1) {
                log.info("I: {} Sequence: {} has been seen last at: {}", i, currentSequence,
                        seenMap.get(currentSequence).get(seenMap.get(currentSequence).size() - 2));
            }
        }

        Integer cycleLength = seenMap.size();

        programs.clear();
        for (int i = 0; i < PROGRAMS; i++) {
            programs.add((char) ('a' + i));
        }

        for (int i = 0; i < ONE_BILLION % cycleLength; i++) {
            for (Dancer danceStep : danceSteps) {
                programs = danceStep.applyMove(programs);
            }
        }

        return printSequence(programs);
    }

    private String printSequence(final List<Character> sequence) {
        StringBuilder sb = new StringBuilder();
        for (Character ch : sequence) {
            sb.append(ch);
        }

        return sb.toString();
    }
}
