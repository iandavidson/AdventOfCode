package dev.davidson.ian.advent.year2022.day20;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class GrovePositioningSystem {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day20/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day20/input.txt";
    private static final Long KEY = 811589153L;

    public static void main(String[] args) {
        GrovePositioningSystem grovePositioningSystem = new GrovePositioningSystem();
        grovePositioningSystem.execute();
    }

    public void execute(){
        List<Long> original = readFile();

        Decrypter decrypter = new Decrypter(original, 1);
        log.info("Part1: {}", decrypter.decrypt());

        decrypter = new Decrypter(original.stream().map(val -> val * KEY).toList(), 10);
        log.info("Part2: {}", decrypter.decrypt());
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
