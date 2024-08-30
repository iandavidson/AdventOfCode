package dev.davidson.ian.advent.year2016.day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DragonChecksum {

    private static final String INPUT_PATH = "adventOfCode/2016/day16/input.txt";
    private static final Integer INPUT_DISK_SPACE_PART_1 = 272;
    private static final Integer INPUT_DISK_SPACE_PART_2 = 35651584;
    private static final String SAMPLE_PATH = "adventOfCode/2016/day16/sample.txt";
    private static final Integer SAMPLE_DISK_SPACE = 20;


    public static void main(String[] args) {
        DragonChecksum dragonChecksum = new DragonChecksum();
        log.info("Part1: {}", dragonChecksum.execute(INPUT_DISK_SPACE_PART_1));
        log.info("Part2: {}", dragonChecksum.execute(INPUT_DISK_SPACE_PART_2));
    }

    public String execute(final Integer diskSpace) {
        String result = applyRule(readFile(), diskSpace);
        return generateCheckSum(result, diskSpace);
    }

    private String applyRule(final String initial, final Integer diskSpace) {
        String result = initial;
        while (result.length() < diskSpace) {
            String a = result;
            String aPrime = new StringBuilder(a).reverse().toString();
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < aPrime.length(); i++) {
                if (aPrime.charAt(i) == '1') {
                    b.append('0');
                } else {
                    b.append('1');
                }
            }

            result = a + '0' + b;
        }

        return result;
    }

    private String generateCheckSum(final String value, final Integer diskSpace) {
        StringBuilder checkSum = new StringBuilder(value.substring(0, diskSpace));
        while (checkSum.length() % 2 == 0) {
            StringBuilder next = new StringBuilder();
            for (int i = 0; i < checkSum.length(); i += 2) {
                if (checkSum.charAt(i) == checkSum.charAt(i + 1)) {
                    next.append('1');
                } else {
                    next.append('0');
                }
            }

            checkSum = next;
        }

        return checkSum.toString();
    }

    public String readFile() {
        ClassLoader cl = DragonChecksum.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }
}
