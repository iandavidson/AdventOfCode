package dev.davidson.ian.advent.year2022.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MonkeyInTheMiddle {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day11/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day11/input.txt";

    public static void main(String[] args) {
        MonkeyInTheMiddle monkeyInTheMiddle = new MonkeyInTheMiddle();
        System.out.println("Part1: " + monkeyInTheMiddle.part1());
        System.out.println("Part2: " + monkeyInTheMiddle.part2());
    }

    public Long part1() {
        List<MonkeyCount> monkeyList = readFile();

        for (int i = 0; i < 20; i++) {
            for (int m = 0; m < monkeyList.size(); m++) {
                Monkey monkey = monkeyList.get(m).getMonkey();
                while (!monkey.items().isEmpty()) {
                    long itemScore = monkey.items().remove();
                    long afterOp = monkey.applyOperation(itemScore);
                    long afterInterestLost = monkey.loseInterest(afterOp);

                    monkeyList.get(monkey.throwToNewMonkey(afterInterestLost)).getMonkey().items().add(afterInterestLost);
                    monkeyList.get(m).incrementCount();
                }
            }
        }
        Collections.sort(monkeyList);
        return monkeyList.get(monkeyList.size() - 1).getCount() * monkeyList.get(monkeyList.size() - 2).getCount();
    }

    private long part2() {
        List<MonkeyCount> monkeyList = readFile();

        long reducer = 1L;
        for (MonkeyCount monkeyCount : monkeyList) {
            reducer *= monkeyCount.getMonkey().testDivisor();
        }

        for (int i = 0; i < 10000; i++) {
            for (int m = 0; m < monkeyList.size(); m++) {
                Monkey monkey = monkeyList.get(m).getMonkey();
                while (!monkey.items().isEmpty()) {
                    long itemScore = monkey.items().remove();
                    long afterOp = monkey.applyOperation(itemScore);
                    long reduced = afterOp % reducer;

                    monkeyList.get(monkey.throwToNewMonkey(reduced)).getMonkey().items().add(reduced);
                    monkeyList.get(m).incrementCount();
                }
            }
        }
        Collections.sort(monkeyList);
        return monkeyList.get(monkeyList.size() - 1).getCount() * monkeyList.get(monkeyList.size() - 2).getCount();
    }

    private List<MonkeyCount> readFile() {
        List<List<String>> monkeyBunches = new ArrayList<>();

        ClassLoader cl = MonkeyInTheMiddle.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            List<String> monkeyBunch = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    monkeyBunches.add(monkeyBunch);
                    monkeyBunch = new ArrayList<>();
                } else {
                    monkeyBunch.add(line);
                }
            }
            monkeyBunches.add(monkeyBunch);

        } catch (FileNotFoundException e) {
            System.err.println("File could not be found");
            throw new RuntimeException(e);
        }

        List<MonkeyCount> monkeyCounts = new ArrayList<>();
        for (List<String> bunch : monkeyBunches) {
            monkeyCounts.add(MonkeyCount.builder().monkey(Monkey.newMonkey(bunch)).build());
        }
        return monkeyCounts;
    }
}
