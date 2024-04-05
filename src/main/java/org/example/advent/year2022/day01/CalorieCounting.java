package org.example.advent.year2022.day01;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

@Data
@AllArgsConstructor
@Builder
public class CalorieCounting {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day01/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day01/input.txt";


    public static void main(String[] args) {
        CalorieCounting calorieCounting = new CalorieCounting();
        System.out.println("Part1: " + calorieCounting.part1());
        System.out.println("Part2: " + calorieCounting.part2());
    }

    public long part1() {
        Queue<Long> elfBags = processInput();
        return elfBags.remove();
    }

    public long part2() {
        Queue<Long> elfBags = processInput();
        return elfBags.remove() + elfBags.remove() + elfBags.remove();
    }

    private static Queue<Long> processInput() {
        Queue<Long> elfBags = new PriorityQueue<>(Collections.reverseOrder());

        try {
            ClassLoader classLoader = CalorieCounting.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            Long temp = 0L;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.isEmpty()) {
                    elfBags.add(temp);
                    temp = 0L;
                } else {
                    temp += Long.parseLong(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return elfBags;
    }
}
