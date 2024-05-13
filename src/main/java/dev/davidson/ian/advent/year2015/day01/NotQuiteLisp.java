package dev.davidson.ian.advent.year2015.day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class NotQuiteLisp {

    private static final String SAMPLE_PATH = "adventOfCode/2015/day01/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2015/day01/input.txt";


    public static void main(String[] args) {
        NotQuiteLisp notQuiteLisp = new NotQuiteLisp();
        System.out.println(notQuiteLisp.part1());
        System.out.println(notQuiteLisp.part2());
    }

    public Integer part1() {
        String line = readFile();
        int currentFloor = 0;
        for (Character ch : line.toCharArray()) {
            if (ch == '(') {
                currentFloor++;
            } else {
                currentFloor--;
            }
        }

        return currentFloor;
    }

    public Integer part2() {
        String line = readFile();
        int currentFloor = 0;
        for (int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);
            if (c == '(') {
                currentFloor++;
            } else {
                currentFloor--;
            }

            if (currentFloor == -1) {
                return i + 1;
            }
        }

        throw new IllegalStateException();
    }

    private String readFile() {
        ClassLoader cl = NotQuiteLisp.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
