package dev.davidson.ian.advent.year2015.day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class NaughtyOrNice {

    private static final String INPUT_PATH = "adventOfCode/2015/day05/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day05/sample.txt";

    private static final Set<Character> VOWELS = Set.of('a', 'e', 'i', 'o', 'u');
    private static final Set<String> SUBSTRINGS = Set.of("ab", "cd", "pq", "xy");

    public static void main(String[] args) {
        NaughtyOrNice naughtyOrNice = new NaughtyOrNice();
        System.out.println(naughtyOrNice.part1());
        System.out.println(naughtyOrNice.part2());
    }


    /*
  It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
  It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
  It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
   */
    public Integer part1() {
        List<String> inputs = readFile();

        int count = 0;
        for (String input : inputs) {
            if (!contains3Vowels(input)) {
                continue;
            }


            if (!containsTwiceInRow(input)) {
                continue;
            }

            if (containsSubstring(input)) {
                continue;
            }

            count++;
        }

        return count;
    }

    public Integer part2() {
        List<String> inputs = readFile();

        /*
        It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
        It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
         */
        Integer count = 0;
        for (String input : inputs) {
            if (!contains2Pairs(input)) {
                continue;
            }

            if (!containsLetterRepeatingWithGap(input)) {
                continue;
            }

            count++;
        }

        return count;
    }

    private boolean contains2Pairs(String input) {
        for (int i = 1; i < input.length(); i++) {
            String sub = input.substring(i - 1, i + 1);
            if (input.substring(i + 1).contains(sub)) {
                return true;
            }
            ///abcabc
        }

        return false;

    }

    private boolean containsLetterRepeatingWithGap(String input) {
        for (int i = 0; i < input.length() - 2; i++) {
            String remaining = input.substring(i + 2);
            if (remaining.charAt(0) == input.charAt(i)) {
                return true;
            }
        }

        return false;
    }


    private boolean contains3Vowels(final String input) {
        int count = 0;
        for (char ch : input.toCharArray()) {
            if (VOWELS.contains(ch)) {
                count++;
            }
        }

        return count >= 3;
    }

    private boolean containsTwiceInRow(final String input) {
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i - 1) == input.charAt(i)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsSubstring(final String input) {
        for (String substring : SUBSTRINGS) {
            if (input.contains(substring)) {
                return true;
            }
        }

        return false;
    }

    private List<String> readFile() {
        List<String> inputs = new ArrayList<>();

        ClassLoader cl = NaughtyOrNice.class.getClassLoader();
        File file = new File(cl.getResource(INPUT_PATH).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputs.add(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return inputs;
    }

}
