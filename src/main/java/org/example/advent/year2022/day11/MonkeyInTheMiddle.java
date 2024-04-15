package org.example.advent.year2022.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MonkeyInTheMiddle {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day11/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day11/input.txt";

    public static void main(String[] args) {
        MonkeyInTheMiddle monkeyInTheMiddle = new MonkeyInTheMiddle();
        System.out.println("Part1: " + monkeyInTheMiddle.part1());
    }

    public Long part1() {
        List<Monkey> monkeyList = readFile();

        return 0L;
    }

    private List<Monkey> readFile() {
        List<List<String>> monkeyBunches = new ArrayList<>();

        ClassLoader cl = MonkeyInTheMiddle.class.getClassLoader();
        File file = new File(Objects.requireNonNull(Objects.requireNonNull(cl.getResource(SAMPLE_INPUT_PATH)).getFile()));
        try {
            Scanner scanner = new Scanner(file);
            List<String> monkeyBunch = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.isBlank()){
                    monkeyBunches.add(monkeyBunch);
                } else {
                    monkeyBunch.add(line);
                }
            }
            monkeyBunches.add(monkeyBunch);

        } catch (FileNotFoundException e) {
            System.err.println("File could not be found");
            throw new RuntimeException(e);
        }

        return monkeyBunches.stream().map(Monkey::newMonkey).toList();
    }
}
