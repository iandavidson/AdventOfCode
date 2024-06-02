package dev.davidson.ian.advent.year2015.day13;

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
public class KnightsOfTheDinnerTable {

    private static final String SAMPLE_PATH = "adventOfCode/2015/day13/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2015/day13/input.txt";
    private static final String INPUT_PATH2 = "adventOfCode/2015/day13/input2.txt";

    public static void main(String[] args) {
        KnightsOfTheDinnerTable knightsOfTheDinnerTable = new KnightsOfTheDinnerTable();
        log.info("Part1: {}", knightsOfTheDinnerTable.execute(false));
        log.info("Part2: {}", knightsOfTheDinnerTable.execute(true));
    }

    public int execute(final Boolean part2) {
        Map<String, Person> peopleMap = readFile(part2);

        List<Person> all = new ArrayList<>(peopleMap.values());

        List<Person> initialSequence = new ArrayList<>();
        initialSequence.add(all.getFirst());
        List<List<Person>> sequences = new ArrayList<>();

        findSequences(all, initialSequence, sequences);

        int max = Integer.MIN_VALUE;
        for (List<Person> sequence : sequences) {
            max = Math.max(max, calculate(sequence, peopleMap));
        }
        return max;
    }

    private void findSequences(final List<Person> all, final List<Person> current, final List<List<Person>> sequences) {
        if (current.size() == all.size()) {
            sequences.add(current);
            return;
        }

        for (Person person : all) {
            if (!current.contains(person)) {
                List<Person> currentCopy = new ArrayList<>(current);
                currentCopy.add(person);
                findSequences(all, currentCopy, sequences);
            }
        }

    }


    private int calculate(List<Person> sequence, Map<String, Person> map) {
        int count = 0;
        int n = sequence.size();

        for (int i = 0; i < n; i++) {
            Person current = sequence.get(i);

            int leftIndex = (i - 1) < 0 ? n + i - 1 : i - 1;
            Person left = sequence.get(leftIndex);
            count += map.get(current.name()).neighborMap().get(left.name());

            Person right = sequence.get((i + 1) % n);
            count += map.get(current.name()).neighborMap().get(right.name());
        }

        return count;
    }

    private Map<String, Person> readFile(final boolean part2) {
        List<String> inputLines = new ArrayList<>();

        ClassLoader cl = KnightsOfTheDinnerTable.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(part2 ? INPUT_PATH2 : INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        Map<String, Person> people = new HashMap<>();
        for (String inputLine : inputLines) {
//            Carol would lose 62 happiness units by sitting next to Alice.

            String[] parts = inputLine.split("\\s+");

            String name = parts[0];
            int score = (parts[2].equals("lose") ? -1 : 1) * Integer.parseInt(parts[3]);
            String neighbor = parts[10].substring(0, parts[10].length() - 1);

            if (people.containsKey(name)) {
                // add to map
                people.get(name).neighborMap().put(neighbor, score);
            } else {
                Person person = new Person(name, new HashMap<>());
                person.neighborMap().put(neighbor, score);
                people.put(name, person);
            }

        }

        return people;
    }
}
