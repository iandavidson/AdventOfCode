package dev.davidson.ian.advent.year2017.day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FractalArt {

    private static final String INPUT_PATH = "adventOfCode/2017/day21/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day21/sample.txt";
    private static final Integer PART_1_ITERATIONS = 5;
    private static final Integer PART_2_ITERATIONS = 18;
    private static final Character FILLED = '#';
    private static final List<String> START = List.of(
            ".#.",
            "..#",
            "###"
    );

    public static void main(String[] args) {
        FractalArt fractalArt = new FractalArt();
        List<Rule> rules = readFile(INPUT_PATH);
        log.info("Part1: {}", fractalArt.execute(rules, PART_1_ITERATIONS));
        log.info("Part2: {}", fractalArt.execute(rules, PART_2_ITERATIONS));
    }

    private static List<Rule> readFile(final String filePath) {
        List<Rule> rules = new ArrayList<>();
        ClassLoader cl = FractalArt.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                rules.add(Rule.newRule(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return rules;
    }

    public Integer execute(final List<Rule> rules, final int iterations) {
        List<String> current = new ArrayList<>(START);

        for (int i = 0; i < iterations; i++) {
            current = applyTurn(current, rules);
        }

        int count = 0;
        for (String line : current) {
            for (char ch : line.toCharArray()) {
                if (ch == FILLED) {
                    count++;
                }
            }
        }

        return count;
    }

    private List<String> applyTurn(final List<String> current, final List<Rule> rules) {
        List<List<String>> flatResults = new ArrayList<>();
        List<String> resultGrid = new ArrayList<>();

        // # of sub grids are on one axis
        int elements = current.size() % 2 == 0 ? current.size() / 2 : current.size() / 3;

        // # width of subgrid before enhancing
        int elementWidth = current.size() / elements;

        for (int i = 0; i < elements; i++) {

            for (int j = 0; j < elements; j++) {
                List<String> tempSection = new ArrayList<>();
                for (int k = 0; k < elementWidth; k++) {
                    tempSection.add(current.get(i * elementWidth + k).substring(j * elementWidth,
                            (j + 1) * elementWidth));
                }

                for (Rule rule : rules) {
                    //check for match, convert, break
                    if (rule.isMatch(tempSection)) {
                        flatResults.add(rule.replacement());
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < elements; i++) {

            resultGrid.addAll(flatSublistToRow(flatResults.subList(i * elements, i * elements + elements),
                    elementWidth));
        }

        return resultGrid;
    }

    private List<String> flatSublistToRow(final List<List<String>> replacements, final Integer elementWidth) {

        List<StringBuilder> rows = new ArrayList<>();
        for (int k = 0; k < replacements.getFirst().size(); k++) {
            rows.add(new StringBuilder());
        }

        for (List<String> replacement : replacements) {
            for (int k = 0; k < replacement.size(); k++) {
                rows.get(k).append(replacement.get(k));
            }
        }

        return rows.stream().map(StringBuilder::toString).collect(Collectors.toList());
    }


}
