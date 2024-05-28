package dev.davidson.ian.advent.year2015.day11;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class SantasNewPassword {

    private static final String INPUT_PATH = "adventOfCode/2015/day11/input.txt";
    private static final Set<Character> NOT_ALLOWED = Set.of('i', 'o', 'l');

    public static void main(String[] args) {
        SantasNewPassword santasNewPassword = new SantasNewPassword();
        log.info("Part1: {}", santasNewPassword.part1());
    }

    public String part1() {
        String initial = readFile();
        char[] password = initial.toCharArray();
        while (true) {

            if(isValidPart1(password)){
                return new String(password);
            }

            int index = password.length-1;

            nextChar(password, index);
            boolean carry = password[index] == 'z';
            while(carry){
                if(password[index] == 'z'){
                    password[index] = 'a';
                    index--;
                }

                nextChar(password, index);

                carry = password[index] == 'z';
            }


            if(isValidPart1(password)){
                return new String(password);
            }

        }
/*

    Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd doesn't count.
    Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
    Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
 */

    }

    private void nextChar(char[] password, int index) {
        password[index]++;
        while (NOT_ALLOWED.contains(password[index])) {
            password[index]++;
        }
    }


    private boolean isValidPart1(final char[] password) {
        return twoCopies(password) && threeIncrementing(password);
    }

    private boolean twoCopies(final char[] password) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < password.length - 1; i++) {
            if (password[i] == password[i + 1]) {
                set.add(password[i]);
            }
        }

        return set.size() > 1;
    }

    private boolean threeIncrementing(final char[] password) {
        for (int i = 0; i < password.length - 2; i++) {
            int first = password[i] - 'a';
            int second = password[i + 1] - 'a' - 1;
            int third = password[i + 2] - 'a' - 2;
            if (first == second && second == third) {
                return true;
            }
        }

        return false;
    }

    private String readFile() {
        ClassLoader cl = SantasNewPassword.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }
    }
}
