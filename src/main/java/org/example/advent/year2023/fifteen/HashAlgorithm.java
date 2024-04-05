package org.example.advent.year2023.fifteen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.LongAdder;

public class HashAlgorithm {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day15/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2023/day15/input.txt";

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = HashAlgorithm.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return Arrays.stream(input.get(0).split(",")).toList();
    }

    private static Long calculateHash(String word) {
        long score = 0;

        /*
Determine the ASCII code for the current character of the string.
Increase the current value by the ASCII code you just determined.
Set the current value to itself multiplied by 17.
Set the current value to the remainder of dividing itself by 256.
         */

        for (Character ch : word.toCharArray()) {
            score = score + (int) ch;
            score *= 17;
            score %= 256;
        }

        return score;
    }

    private static List<Lens> processInput(List<String> inputs) {
        List<Lens> lenses = new ArrayList<>();
        for (String input : inputs) {

            if (input.contains("-")) {
                lenses.add(Lens.builder().remove(true).label(input.substring(0, input.indexOf('-'))).build());
            } else {
                String[] temp = input.split("=");
                lenses.add(Lens.builder().remove(false).label(temp[0]).strength(Integer.parseInt(temp[1])).build());
            }
        }

        return lenses;
    }

    public static Long part2() {
        List<String> inputs = readFile();
        List<Lens> lenses = processInput(inputs);
        Map<Integer, LinkedList<Lens>> boxes = new HashMap<>();

        for (Lens instruction : lenses) {
            int hash = Math.toIntExact(calculateHash(instruction.getLabel()));

            if (instruction.getRemove()) {
                if (boxes.containsKey(hash)) {
                    boxes.get(hash).removeFirstOccurrence(instruction);
                } else {
                    //do nothing, entry doesn't exist
                }
            } else {
                if (boxes.containsKey(hash)) {
                    LinkedList<Lens> lensLinkedList = boxes.get(hash);
                    if (!lensLinkedList.contains(instruction)) {
                        lensLinkedList.add(instruction);
                    } else {
                        lensLinkedList.set(lensLinkedList.indexOf(instruction), instruction);
                    }
                } else {
                    LinkedList<Lens> lensLinkedList = new LinkedList<>();
                    lensLinkedList.add(instruction);
                    boxes.put(hash, lensLinkedList);
                }
            }
        }

        LongAdder sum = new LongAdder();
        boxes.keySet().parallelStream().forEach(hash -> {
            for (int i = 0; i < boxes.get(hash).size(); i++) {
                sum.add((long) (hash + 1) * (i + 1) * boxes.get(hash).get(i).getStrength());
            }
        });

        return sum.sum();
    }

    public static Long part1() {
        List<String> inputs = readFile();
        Long sum = 0L;
        for (String input : inputs) {
            sum += calculateHash(input);
        }

        return sum;
    }

    public static void main(String[] input) {
        long part1 = part1();
        System.out.println("part1: " + part1);

        long part2 = part2();
        System.out.println("part2: " + part2);
    }

    @AllArgsConstructor
    @Builder
    @Data
    public static class Lens {
        private final String label;
        private final Integer strength;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Lens lens = (Lens) o;
            return Objects.equals(label, lens.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }

        private final Boolean remove;
    }
}
