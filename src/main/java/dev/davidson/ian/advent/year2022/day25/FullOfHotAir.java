package dev.davidson.ian.advent.year2022.day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FullOfHotAir {

    private static final String INPUT_PATH = "adventOfCode/2022/day25/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2022/day25/sample.txt";
    private static final String MINI_PATH = "adventOfCode/2022/day25/mini.txt";

    private static final Map<Character, Long> SNAFU_TO_VALUE = Map.of(
            '2', 2L,
            '1', 1L,
            '0', 0L,
            '-', -1L,
            '=', -2L
    );

    private static final Map<Integer, Character> VALUE_TO_SNAFU = Map.of(
            2, '2',
            1, '1',
            0, '0',
            -1, '-',
            -2, '='
    );

    public static void main(String[] args) {
        FullOfHotAir fullOfHotAir = new FullOfHotAir();
        log.info("Part1: {}", fullOfHotAir.part1());
    }

    public String part1() {
        List<String> numbers = readFile();
        Long sum = 0L;

        for (String number : numbers) {
            sum += SNAFUtoLong(number);
        }

        return convertToSNAFU(sum);
    }

    private String convertToSNAFU(Long value) {
        //convert to base 5
        log.info("decimal: {}", value);
        StringBuilder base5 = new StringBuilder();
        while (value > 0) {
            base5.append(value % 5);
            value /= 5;
        }

        String base = base5.toString();

        //convert base5 -> SNAFU
        StringBuilder snafu = new StringBuilder();
        boolean carry = false;
        for (int i = 0; i < base.length(); i++) {
            int current = Character.getNumericValue(base.charAt(i));
            if (carry) {
                current += 1;
                carry = false;
            }

            if (current > -1 && current < 3) {
                snafu.append(current);
            } else if (current == 3) {
                snafu.append('=');
                carry = true;
            } else if (current == 4) {
                snafu.append('-');
                carry = true;
            } else {
                snafu.append('0');
                carry = true;
            }
        }

        if (carry) {
            snafu.append('1');
        }

        return snafu.reverse().toString();
    }

    private Long SNAFUtoLong(final String value) {
        long tempValue = 0L;
        char[] num = new StringBuilder(value).reverse().toString().toCharArray();
        int n = num.length;
        long coefficient = 1L;
        for (int i = 0; i < n; i++) {
            tempValue += SNAFU_TO_VALUE.get(num[i]) * coefficient;
            coefficient *= 5L;
        }

        return tempValue;
    }

    private List<String> readFile() {
        List<String> numbers = new ArrayList<>();

        ClassLoader cl = FullOfHotAir.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                numbers.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }
        return numbers;
    }

}
