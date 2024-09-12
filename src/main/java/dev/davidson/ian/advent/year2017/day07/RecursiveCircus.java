package dev.davidson.ian.advent.year2017.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RecursiveCircus {

    private static final String INPUT_PATH = "adventOfCode/2017/day07/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day07/sample.txt";

    public static void main(String[] args) {
        RecursiveCircus recursiveCircus = new RecursiveCircus();
        List<Disk> disks = readFile();
        log.info("Part1: {}", recursiveCircus.part1(disks));
    }

    private static List<Disk> readFile() {
        List<Disk> disks = new ArrayList<>();

        ClassLoader cl = RecursiveCircus.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                disks.add(Disk.newDisk(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return disks;
    }

    public String part1(final List<Disk> disks) {

    }
}
