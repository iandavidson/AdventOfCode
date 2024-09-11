package dev.davidson.ian.advent.year2017.day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MazeOfTwistyTrampolines {

    private static final String INPUT_PATH = "adventOfCode/2017/day05/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day05/sample.txt";

    public static void main(String[] args) {
        MazeOfTwistyTrampolines maze = new MazeOfTwistyTrampolines();
        List<Integer> inputs = readFile(INPUT_PATH);
        log.info("Part1: {}", maze.part1(new ArrayList<>(inputs)));
        log.info("Part2: {}", maze.part2(new ArrayList<>(inputs)));
    }

    private static List<Integer> readFile(final String filePath) {
        List<Integer> inputs = new ArrayList<>();

        ClassLoader cl = MazeOfTwistyTrampolines.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputs.add(Integer.parseInt(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return inputs;
    }

    private Long part1(final List<Integer> inputs) {
        int index = 0;
        int n = inputs.size();
        Long stepsTaken = 0L;
        while (index < n && index > -1) {
            int offset = inputs.get(index);
            inputs.set(index, offset + 1);
            index += offset;
            stepsTaken++;
        }

        return stepsTaken;
    }

    private Long part2(final List<Integer> inputs){
        int index = 0;
        int n = inputs.size();
        Long stepsTaken = 0L;
        while (index < n && index > -1) {
            int offset = inputs.get(index);
            if(offset >= 3){
                inputs.set(index, offset - 1);
            }else{
                inputs.set(index, offset + 1);
            }
            index += offset;
            stepsTaken++;
        }

        return stepsTaken;
    }
}
