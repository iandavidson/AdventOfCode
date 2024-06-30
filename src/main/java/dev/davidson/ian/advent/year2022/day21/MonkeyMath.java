package dev.davidson.ian.advent.year2022.day21;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class MonkeyMath {

    private static final String INPUT_PATH = "adventOfCode/2022/day21/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2022/day21/sample.txt";
    private static final String FINAL_MONKEY = "root";
    private static final String HUMN = "humn";

    public static void main(String[] args) {
        MonkeyMath monkeyMath = new MonkeyMath();
        log.info("Part1: {}", monkeyMath.part1());
        log.info("Part2: {}", monkeyMath.part2());
    }

    public long part1() {
        List<Monkey> monkeys = readFile();
        int n = monkeys.size();

        Map<String, Long> monkeyMap = new HashMap<>();
        for (Monkey monkey : monkeys) {
            if (monkey.getDefaultValue() != null) {
                monkeyMap.put(monkey.getName(), monkey.getDefaultValue());
            }
        }

        long i = 0L;
        while (!monkeyMap.containsKey(FINAL_MONKEY)) {
            //if monkey has not been set
            Monkey current = monkeys.get((int) (i % n));
            if (!monkeyMap.containsKey(current.getName())) {

                if (monkeyMap.containsKey(current.getDep1()) && monkeyMap.containsKey(current.getDep2())) {
                    monkeyMap.put(
                            current.getName(),
                            OPERATION.executeOperation(current.getOperation(), monkeyMap.get(current.getDep1()), monkeyMap.get(current.getDep2()))
                    );
                }
            }

            i++;
        }

        return monkeyMap.get(FINAL_MONKEY);
    }


    public long part2() {
        List<Monkey> monkeys = readFile();
        //humn is no longer valid
        int n = monkeys.size();

//        Monkey root = null;
        Map<String, Long> monkeyValueMap = new HashMap<>();
        Map<String, Monkey> monkeyNameMap = new HashMap<>();
        for (Monkey monkey : monkeys) {
            monkeyNameMap.put(monkey.getName(), monkey);

            if (monkey.getDefaultValue() != null) {
                if (monkey.getName().equals(HUMN)) {
                    continue;
                }
                monkeyValueMap.put(monkey.getName(), monkey.getDefaultValue());
            }
        }

//        long i =0L;
        boolean stillWorking = true;
        while (stillWorking) {
            //if monkey has not been set
            stillWorking = false;
            for (int i = 0; i < n; i++) {
                Monkey current = monkeys.get(i);
                if (!monkeyValueMap.containsKey(current.getName())) {

                    if (monkeyValueMap.containsKey(current.getDep1()) && monkeyValueMap.containsKey(current.getDep2())) {
                        monkeyValueMap.put(
                                current.getName(),
                                OPERATION.executeOperation(current.getOperation(), monkeyValueMap.get(current.getDep1()), monkeyValueMap.get(current.getDep2()))
                        );
                        stillWorking = true;
                    }
                }

            }
        }

        //should have tree filled out execpt for "humn" and any equation that depends on humn
        // we know one of the paths from "root" is valid at this point.
        Monkey root = monkeyNameMap.get(FINAL_MONKEY);
        MonkeyValue monkeyValue = null;
        if (monkeyValueMap.containsKey(root.getDep1())) {
            monkeyValue = new MonkeyValue(root.getDep2(), monkeyValueMap.get(root.getDep1()));
        } else {
            monkeyValue = new MonkeyValue(root.getDep1(), monkeyValueMap.get(root.getDep2()));
        }

        while (true) {
            monkeyValue = evaluateLabel(monkeyValue, monkeyValueMap, monkeyNameMap);

            if (monkeyValue.name().equals(HUMN)) {
                break;
            }
        }


        return monkeyValueMap.get(HUMN);
    }

    private MonkeyValue evaluateLabel(final MonkeyValue monkeyValue, Map<String, Long> monkeyValueMap, Map<String, Monkey> monkeyMap) {
        // monkeyValue = b + c
        Monkey current = monkeyMap.get(monkeyValue.name());

        assert current != null;
        MonkeyValue next = null;
        if (monkeyValueMap.containsKey(current.getDep1())) {
            //b exists
            long tempB = monkeyValueMap.get(current.getDep1());
            String nextLabel = current.getDep2();

            switch (current.getOperation()) {
                case ADD -> {
                    //monkeyvalue = b + c;
                    //result: c = monkeyValue - b
                    next = new MonkeyValue(nextLabel, monkeyValue.value() - tempB);
                }
                case SUB -> {
                    //monkeyvalue = b - c
                    //result : c = b - monkeyValue
                    next = new MonkeyValue(nextLabel, tempB - monkeyValue.value());
                }
                case MULT -> {
                    //monkeyvalue = b * c
                    //result : c = monkeyValue / b
                    next = new MonkeyValue(nextLabel, monkeyValue.value() / tempB);
                }
                case DIV -> {
                    //monkeyvalue = b / c
                    //result : c = b / monkeyValue}
                    next = new MonkeyValue(nextLabel, tempB / monkeyValue.value());
                }
            }
        } else {
            //c exists
            long tempC = monkeyValueMap.get(current.getDep2());
            String nextLabel = current.getDep1();
            switch (current.getOperation()) {
                case ADD -> {
                    //monkeyvalue = b + c
                    //result : b = monkeyValue - c
                    next = new MonkeyValue(nextLabel, monkeyValue.value() - tempC);
                }
                case SUB -> {
                    //monkeyvalue = b - c
                    //result : b = monkeyValue + c
                    next = new MonkeyValue(nextLabel, monkeyValue.value() + tempC);
                }
                case MULT -> {
                    //monkeyvalue = b * c
                    //result : b = monkeyValue / c
                    next = new MonkeyValue(nextLabel, monkeyValue.value() / tempC);
                }
                case DIV -> {
                    //monkeyvalue = b / c
                    //result : b = monkeyValue * c
                    next = new MonkeyValue(nextLabel, monkeyValue.value() * tempC);
                }
            }
        }

        monkeyValueMap.put(next.name(), next.value());
        return next;
    }

    private List<Monkey> readFile() {
        List<Monkey> monkeys = new ArrayList<>();

        ClassLoader cl = MonkeyMath.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                monkeys.add(new Monkey(scanner.nextLine()));
            }

        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        return monkeys;
    }
}
