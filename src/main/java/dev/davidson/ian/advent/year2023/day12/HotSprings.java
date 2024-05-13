package dev.davidson.ian.advent.year2023.day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class HotSprings {

    private static final String INPUT_PATH = "adventOfCode/2023/day12/input.txt";
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day12/input-sample.txt";

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = HotSprings.class.getClassLoader();
            File file = new File(classLoader.getResource(INPUT_PATH).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    private static List<SpringRow> interpretInputs(List<String> inputs) {
        List<SpringRow> springRows = new ArrayList<>();

        for (String line : inputs) {
            List<String> lineElements = Arrays.stream(line.split("\\s+")).toList();
            List<Integer> sequence = Arrays.stream(lineElements.get(1).split(",")).map(Integer::parseInt).toList();
            SpringRow springRow = new SpringRow(sequence, lineElements.get(0));
            springRows.add(springRow);
        }

        return springRows;
    }


    private static Long totalMatches(SpringRow springRow) {
        long numQMarks = springRow.getRawRow().chars().filter(ch -> ch == '?').count();
        Map<Integer, Character> options = Map.of(0, '.', 1, '#');

        Matcher matcher = springRow.getRegEx().matcher("");


        char[] rowTemp;
        long matchesCount = 0L;
        long loopMax = (long) Math.pow(2, numQMarks);
        for (int i = 0; i < loopMax; i++) {

            rowTemp = springRow.getRawRow().toCharArray();
            int rowTempIdx = 0;
            int bitmask = i;
            for (int j = 0; j < numQMarks; j++) {
                while (rowTemp[rowTempIdx] != '?') {
                    rowTempIdx++;
                }
//                System.out.println("copy: " + copy + " ; copy >> 1: " + (copy >> 1)  + " ; copy >> 1 % 2: " + ((copy >> 1) % 2));

                char replacement = options.get(bitmask % 2);
                rowTemp[rowTempIdx] = replacement;
                bitmask /= 2;
            }
//            System.out.println("i: " + i + "; currentString: " + new String(rowTemp) + "\n");

            matcher.reset(new String(rowTemp));

            if (matcher.matches()) {
                matchesCount++;
            }

        }

        return matchesCount;
    }

    public static Long part1() {
        List<String> inputs = readFile();
        List<SpringRow> springRows = interpretInputs(inputs);

        Long sum = 0L;
        for (SpringRow springRow : springRows) {
            sum += totalMatches(springRow);
        }
        return sum;
    }

    private static List<SpringRow> interpretInputsPart2(List<String> inputs) {
        List<SpringRow> springRows = new ArrayList<>();

        for (String line : inputs) {
            List<String> lineElements = Arrays.stream(line.split("\\s+")).toList();
            List<Integer> sequence = new ArrayList<>();
            StringBuilder springRowBuilder = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                sequence.addAll(Arrays.stream(lineElements.get(1).split(",")).map(Integer::parseInt).toList());
                springRowBuilder.append(lineElements.get(0));

                if (i != 4) {
                    springRowBuilder.append("?");
                }
            }

            SpringRow springRow = new SpringRow(sequence, springRowBuilder.toString());
            springRows.add(springRow);
        }

        return springRows;
    }

    private static long countPeriods(String row) {
        long count = 0;
        for (Character ch : row.toCharArray()) {
            if (ch == '.') {
                count++;
            }
        }

        return count;
    }

    private static Long generateAndTraverseStateMachine(SpringRow springRow) {
        List<Integer> gears = springRow.getGearSequence();
        gears.add(0);
        springRow.setGearSequence(gears);


        StateKey init = new StateKey(0, 0);

        Map<StateKey, Long> states = new HashMap<>();
        states.put(init, 1L);
        Map<StateKey, Long> nextStates = new HashMap<>();

        Long brokenSpringsLeft = springRow.getRawRow().length() - countPeriods(springRow.getRawRow());

        List<Integer> minRequiredBrokenSpringsLeft = new ArrayList<>();
        for (int i = 0; i < springRow.getGearSequence().size(); i++) {
            minRequiredBrokenSpringsLeft.add(springRow.getGearSequence().stream().skip(i).mapToInt(Integer::intValue).sum());
        }

        for (Character spring : springRow.getRawRow().toCharArray()) {
            if (spring != '.') {
                brokenSpringsLeft--;
            }

            for (Map.Entry<StateKey, Long> entry : states.entrySet()) {
                if (spring == '#' || spring == '?') {
                    if (entry.getKey().getGroup() < springRow.getGearSequence().size() &&
                        entry.getKey().getAmount() < springRow.getGearSequence().get(entry.getKey().getGroup())) {
                        nextStates.put(new StateKey(entry.getKey().getGroup(), entry.getKey().getAmount() + 1), entry.getValue());
                    }
                }

                if (spring == '.' || spring == '?') {
                    if (entry.getKey().getAmount() == 0) {
                        StateKey stateKey = new StateKey(entry.getKey().getGroup(), 0);
                        Long currentWeight = nextStates.get(stateKey);
                        nextStates.put(stateKey, currentWeight == null ? entry.getValue() : entry.getValue() + currentWeight);
                    } else if (entry.getKey().getAmount().equals(springRow.getGearSequence().get(entry.getKey().getGroup()))) {
                        StateKey stateKey = new StateKey(entry.getKey().getGroup() + 1, 0);
                        Long currentWeight = nextStates.get(stateKey);
                        nextStates.put(stateKey, currentWeight == null ? entry.getValue() : entry.getValue() + currentWeight);
                    }
                }

            }


            List<StateKey> toBeRemoved = new ArrayList<>();
            for (StateKey stateKey : nextStates.keySet()) {
                if (brokenSpringsLeft + stateKey.getAmount() < minRequiredBrokenSpringsLeft.get(stateKey.getGroup())) {
                    toBeRemoved.add(stateKey);
                }
            }

            for (StateKey state : toBeRemoved) {
                nextStates.remove(state);
            }


            states.clear();
            states = nextStates;
            nextStates = new HashMap<>();
        }

        long sum = 0;
        for (Long i : states.values()) {
            sum += i;
        }
        return sum;
    }

    public static Long part2() {
        List<String> inputs = readFile();
        List<SpringRow> springRows = interpretInputsPart2(inputs);

        Long sum = 0L;
        for (SpringRow springRow : springRows) {
            sum += generateAndTraverseStateMachine(springRow);
        }

        return sum;

    }

    public static void main(String[] args) {
        HotSprings hotSprings = new HotSprings();
        Long number = part1();
        System.out.println("result: " + number);

        Long result = part2();
        System.out.println("result: " + result);
    }

    @Data
    static class SpringRow {
        //        private static final String ZERO_OR_MORE_DOT = "\\\\.*?";
//        private static final String ONE_OR_MORE_DOT = "\\\\.+?";
        private static final String ZERO_OR_MORE_DOT = "\\.*?";
        private static final String ONE_OR_MORE_DOT = "\\.+?";
        private static final String LEFT_GROUP_CHUNK = "#{";
        private static final String RIGHT_GROUP_CHUNK = "}";
        private List<Integer> gearSequence;
        private String rawRow;
        private Pattern regEx;


        public SpringRow(List<Integer> gearSequence, String rawRow) {
            //raw row should not contain the sequence of gears at the end, only characters consisting of ['.', '?', '#']
            this.gearSequence = gearSequence;
            this.rawRow = rawRow;
            this.regEx = regexFactory();
        }

        private Pattern regexFactory() {
            /*
                ?###???????? 3,2,1
                \\.*?#{3}\\.+#{2}\\.+#{1}\\.*?
             */

            StringBuilder rawRegex = new StringBuilder();
            rawRegex.append(ZERO_OR_MORE_DOT);
            for (int i = 0; i < gearSequence.size(); i++) {
                rawRegex.append(LEFT_GROUP_CHUNK);
                rawRegex.append(gearSequence.get(i));
                rawRegex.append(RIGHT_GROUP_CHUNK);
                if (i < gearSequence.size() - 1) {
                    rawRegex.append(ONE_OR_MORE_DOT);
                }
            }
            rawRegex.append(ZERO_OR_MORE_DOT);

            return Pattern.compile(rawRegex.toString());
        }
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class StateKey {

        private Integer group;
        private Integer amount;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StateKey stateKey = (StateKey) o;
            return Objects.equals(group, stateKey.group) && Objects.equals(amount, stateKey.amount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(group, amount);
        }
    }


}
