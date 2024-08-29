package dev.davidson.ian.advent.year2016.day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NiceGameOfChess {

    private static final String INPUT_PATH = "adventOfCode/2016/day05/input.txt";
    private static final String PREFIX_MATCH = "00000";

    public static void main(String[] args) {
        NiceGameOfChess niceGameOfChess = new NiceGameOfChess();
        log.info("Part1: {}", niceGameOfChess.part1());
        log.info("Part2: {}", niceGameOfChess.part2());
    }

    public String part1() {
        String doorId = readFile();
        StringBuilder result = new StringBuilder();
        int counter = 0;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String temp = doorId + i;
                byte[] digest = md.digest(temp.getBytes(StandardCharsets.UTF_8));

                StringBuilder hexString = new StringBuilder();

                for (int j = 0; j < digest.length; j++) {
                    String hexChunk = Integer.toHexString(0xFF & digest[j]);
                    if (hexChunk.length() == 1) {
                        hexChunk = "0" + hexChunk;
                    }

                    hexString.append(hexChunk);
                }


                if (hexString.toString().startsWith(PREFIX_MATCH)) {
                    result.append(hexString.charAt(5));
                    counter++;
                }

                if (counter == 8) {
                    break;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }

        return result.toString();
    }

    public String part2() {
        String doorId = readFile();
        char[] result = new char[8];
        Set<Integer> indexSet = new HashSet<>();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String temp = doorId + i;
                byte[] digest = md.digest(temp.getBytes(StandardCharsets.UTF_8));

                StringBuilder hexString = new StringBuilder();

                for (int j = 0; j < digest.length; j++) {
                    String hexChunk = Integer.toHexString(0xFF & digest[j]);
                    if (hexChunk.length() == 1) {
                        hexChunk = "0" + hexChunk;
                    }

                    hexString.append(hexChunk);
                }

                String hex = hexString.toString();

                if (hex.matches("^00000[0-7].+?$")) {

                    if (!indexSet.contains(hex.charAt(5) - '0')) {
                        result[hex.charAt(5) - '0'] = hex.charAt(6);
                        indexSet.add(hex.charAt(5) - '0');
                    }
                }

                if (indexSet.size() == 8) {
                    break;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }

        return new String(result);
    }


    private String readFile() {
        ClassLoader cl = NiceGameOfChess.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
