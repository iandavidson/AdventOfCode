package dev.davidson.ian.advent.year2017.day09;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamProcessing {
    private static final String INPUT_PATH = "adventOfCode/2017/day09/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day09/sample.txt";


    public static void main(String[] args) {
        StreamProcessing streamProcessing = new StreamProcessing();
        String input = readFile(INPUT_PATH);
        log.info("Part1: {}", streamProcessing.execute(input, true));
        log.info("Part2: {}", streamProcessing.execute(input, false));

    }

    private static String readFile(final String filePath) {
        ClassLoader cl = StreamProcessing.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Failed to read file at provided path");
        }
    }

    public Integer execute(final String input, final boolean part1) {
        Deque<Integer> stack = new ArrayDeque<>();
        int index = 0;
        boolean garbage = false;
        int score = 0;
        int garbageCount = 0;
        int currentDepth = 0;

        while (index < input.length()) {
            char ch = input.charAt(index);
            if (ch == '!') {
                index++;
            } else if (garbage) {
                if (ch == '>') {
                    garbage = false;
                } else {
                    garbageCount++;
                }
            } else if (ch == '<') {
                garbage = true;
            } else if (ch == '{') {
                currentDepth++;
                stack.push(currentDepth);
            } else if (ch == '}') {
                currentDepth--;
                score += stack.pop();
            }

            index++;
        }

        return part1 ? score : garbageCount;
    }
}