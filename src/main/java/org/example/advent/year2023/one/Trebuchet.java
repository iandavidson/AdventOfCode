package org.example.advent.year2023.one;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Trebuchet {
    private static final String INPUT_PATH = "adventOfCode/year2023/day1/input.txt";
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/year2023/day1/input2.txt";
    private static final String ZERO = "zero";
    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";
    private static final String FOUR = "four";
    private static final String FIVE = "five";
    private static final String SIX = "six";
    private static final String SEVEN = "seven";
    private static final String EIGHT = "eight";
    private static final String NINE = "nine";
    private static final Map<String, String> TEXT_TO_NUMBER = Map.of(
        ZERO, "0ero",
        ONE, "1ne",
        TWO, "2wo",
        THREE, "3hree",
        FOUR, "4our",
        FIVE, "5ive",
        SIX, "6ix",
        SEVEN, "7even",
        EIGHT, "8ight",
        NINE, "9ine"
    );

    public static void main(String[] args) {
        Trebuchet trebuchet = new Trebuchet();

        trebuchet.calibratePart1();
        trebuchet.calibratePart2();
    }

    public int calibratePart1() {
        List<Integer> digitsRead = new ArrayList<>();

        int runningSum = 0;
        try {
            ClassLoader classLoader = Trebuchet.class.getClassLoader();
            File file = new File(classLoader.getResource(INPUT_PATH).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                digitsRead.clear();
                for (char c : data.toCharArray()) {
                    if (Character.isDigit(c)) {
                        digitsRead.add(Integer.parseInt(String.valueOf(c)));
                    }
                }

                if (!digitsRead.isEmpty()) {
                    runningSum = runningSum + (digitsRead.get(0) * 10) + digitsRead.get(digitsRead.size() - 1);
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("Total: " + runningSum);
        return runningSum;
        //should be == 53921 given input.txt
    }

    public int calibratePart2() {
        List<Integer> digitsRead = new ArrayList<>();

        int runningSum = 0;
        try {
            ClassLoader classLoader = Trebuchet.class.getClassLoader();
            File file = new File(classLoader.getResource(INPUT_PATH).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                digitsRead.clear();

                String treatedData = treatLinePart2(data);

                for (char c : treatedData.toCharArray()) {
                    if (Character.isDigit(c)) {
                        digitsRead.add(Integer.parseInt(String.valueOf(c)));
                    }
                }

                if (!digitsRead.isEmpty()) {
                    runningSum = runningSum + (digitsRead.get(0) * 10) + digitsRead.get(digitsRead.size() - 1);
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("Total: " + runningSum);
        return runningSum;
    }

    private String treatLinePart2(String line) {
        //replace first letter of spelled out number with respective number
        //eg: xtwone3four -> x2w1ne34our

        String og = line;
        //doing this inefficient as fuck bare with me

        for (int i = 0; i < line.length(); i++) {
            for (String spelledNumber : TEXT_TO_NUMBER.keySet()) {
                if (line.indexOf(spelledNumber) == i) {
                    // if there are 2 repeating instances of a number spelled out we could run into an issue of interpretation
                    line = line.replace(spelledNumber, TEXT_TO_NUMBER.get(spelledNumber));
                }
            }
        }

        System.out.println("Original: " + og + "  ; Treated: " + line);
        return line;
    }
}
