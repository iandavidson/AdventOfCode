package dev.davidson.ian.advent.year2022.day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GrovePositioningSystem {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day20/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day20/input.txt";

    public static void main(String[] args) {
        GrovePositioningSystem grovePositioningSystem = new GrovePositioningSystem();
        System.out.println("Part1: " + grovePositioningSystem.execute());
    }

    public long execute(){
        List<Long> original = readFile();

        Decrypter decrypter = new Decrypter(original, 1);
        return decrypter.decrypt();
    }

    private List<Long> readFile(){
        List<Long> inputs = new ArrayList<>();

        ClassLoader cl = GrovePositioningSystem.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                inputs.add(Long.parseLong(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return inputs;
    }
}
