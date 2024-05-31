package dev.davidson.ian.advent.year2015.day13;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class KnightsOfTheDinnerTable {

    private static final String SAMPLE_PATH = "adventOfCode/2015/day13/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2015/day13/input.txt";

    public static void main(String[] args) {
        KnightsOfTheDinnerTable knightsOfTheDinnerTable = new KnightsOfTheDinnerTable();
        log.info("Part1: {}", knightsOfTheDinnerTable.part1());
    }

    public int part1() {
        Map<String, Person> peopleMap = readFile();



        return 0;
    }

    public int calculate(List<Person> sequence, Map<String, Person> map){

    }

    private Map<String, Person> readFile() {
        List<String> inputLines = new ArrayList<>();

        ClassLoader cl = KnightsOfTheDinnerTable.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
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
                people.get(name).neighborPerception().add(new Perception(neighbor, score));
            } else {
                Person person = new Person(name, new ArrayList<>());
                person.neighborPerception().add(new Perception(neighbor, score));
                people.put(name, person);
            }

        }

        for(Person person : people.values()){
            Collections.sort(person.neighborPerception());
        }
        return people;
    }
}
