package dev.davidson.ian.advent.year2017.day09;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamProcessing {
    private static final String INPUT_PATH = "adventOfCode/2017/day09/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day09/sample.txt";


    public static void main(String[] args) {
        StreamProcessing streamProcessing = new StreamProcessing();
        String input = readFile(SAMPLE_PATH);
        log.info("Part1: {}", streamProcessing.part1(input));
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

    public Long part1(final String input) {
        Group group = Group.newGroup(input, 1);

        Queue<Group> queue = new LinkedList<>();
        queue.add(group);

        long totalScore = 0L;
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Group current = queue.remove();
                totalScore += current.getDepth();
                queue.addAll(current.getInside());
            }

        }

        return totalScore;

    }
}