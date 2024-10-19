package dev.davidson.ian.advent.year2018.day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryManagementSystem {

    private static final String INPUT_PATH = "adventOfCode/2018/day02/input.txt";

    public static void main(String[] args) {
        List<Identifier> inputs = readfile(INPUT_PATH);
        InventoryManagementSystem ims = new InventoryManagementSystem();

        log.info("Part1: {}", ims.part1(inputs));
        log.info("Part2: {}", ims.part2(inputs));
    }

    private static List<Identifier> readfile(final String filePath) {
        List<Identifier> inputs = new ArrayList<>();

        ClassLoader cl = InventoryManagementSystem.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputs.add(Identifier.newIdentifier(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return inputs;
    }

    public Long part1(final List<Identifier> identifiers) {
        long hasDoubles = 0L;
        long hasTriples = 0L;

        for (Identifier identifier : identifiers) {
            if (identifier.twoOfAKind()) {
                hasDoubles++;
            }

            if (identifier.threeOfAKind()) {
                hasTriples++;
            }
        }

        return hasDoubles * hasTriples;
    }

    public String part2(final List<Identifier> identifiers) {
        int n = identifiers.size();
        String box1 = "";
        String box2 = "";

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Identifier.has1Diff(identifiers.get(i), identifiers.get(j))) {
                    box1 = identifiers.get(i).getRawId();
                    box2 = identifiers.get(j).getRawId();
                }
            }
        }


        if (box1.isEmpty()) {
            throw new IllegalStateException("FUCK!!!!!! 🖕🏻");
        }


        StringBuilder overlap = new StringBuilder();
        for (int i = 0; i < box1.length(); i++) {
            if (box1.charAt(i) == box2.charAt(i)) {
                overlap.append(box1.charAt(i));
            }
        }

        return overlap.toString();
    }
}
