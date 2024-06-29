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
    private static final String FINAL_MONKEY = "root";

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


    public long part2(){
        List<Monkey> monkeys = readFile();
        //humn is no longer valid
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
