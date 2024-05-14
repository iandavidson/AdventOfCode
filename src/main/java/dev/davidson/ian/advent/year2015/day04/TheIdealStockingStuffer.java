package dev.davidson.ian.advent.year2015.day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class TheIdealStockingStuffer {

    private static final String INPUT_PATH = "adventOfCode/2015/day04/input.txt";
    private static final String DESIRED_PREFIX_1 = "00000";
    private static final String DESIRED_PREFIX_2 = "000000";

    public static void main(String[] args) {
        TheIdealStockingStuffer theIdealStockingStuffer = new TheIdealStockingStuffer();
        System.out.println("part1: " + theIdealStockingStuffer.execute(DESIRED_PREFIX_1));
        System.out.println("part2: " + theIdealStockingStuffer.execute(DESIRED_PREFIX_2));
    }

    public String execute(String prefix) {
        final String input = readFile();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String temp = input + i;
                byte[] digest = md.digest(temp.getBytes(StandardCharsets.UTF_8));

                StringBuilder hexString = new StringBuilder();
                for (int j = 0; j < digest.length; j++) {
                    String hexChunk = Integer.toHexString(0xFF & digest[j]);
                    if (hexChunk.length() == 1) {
                        hexChunk = "0" + hexChunk;
                    }

                    hexString.append(hexChunk);
                }

                if (hexString.toString().startsWith(prefix)) {
                    return temp;
                }
            }


        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return "...";
    }

    private String readFile() {
        ClassLoader cl = TheIdealStockingStuffer.class.getClassLoader();
        File file = new File(cl.getResource(INPUT_PATH).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

}
