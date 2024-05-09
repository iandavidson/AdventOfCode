package advent.year2022.day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class TuningTrouble {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day06/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day06/input.txt";

    public static void main(String[] args) {
        TuningTrouble tuningTrouble = new TuningTrouble();
        System.out.println("Part1: " + tuningTrouble.part1());
        System.out.println("Part2: " + tuningTrouble.part2());
    }


    public Integer part1() {
        String input = readFile();

        for (int i = 3; i < input.length(); i++) {
            if (input.substring(i - 3, i + 1).chars().distinct().count() == 4) {
                return i + 1;
            }
        }

        throw new IllegalStateException("Didn't find it");
    }

    public Integer part2() {
        String input = readFile();

        for (int i = 13; i < input.length(); i++) {
            if (input.substring(i - 13, i + 1).chars().distinct().count() == 14) {
                return i + 1;
            }
        }

        throw new IllegalStateException("Didn't find it");
    }

    private String readFile() {
        try {
            ClassLoader loader = TuningTrouble.class.getClassLoader();
            File file = new File(Objects.requireNonNull(loader.getResource(INPUT_PATH)).getFile());
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }
    }
}


