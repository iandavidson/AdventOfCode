package org.example.advent.year2022.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class NoSpaceLeftOnDevice {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day07/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day07/input.txt";
    private static final Long MAX_TOTAL_DISK_SPACE = 40000000L;
    private static final Character COMMAND_INDICATOR = '$';

    public static void main(String[] args) {
        NoSpaceLeftOnDevice noSpaceLeftOnDevice = new NoSpaceLeftOnDevice();
        System.out.println("Part1: " + noSpaceLeftOnDevice.part1());
        System.out.println("Part2: " + noSpaceLeftOnDevice.part2());
    }

    public Long part1() {
        List<String> inputLines = readInput();
        Directory root = Directory.builder().name("/").build();
        SortedSet<Directory> directorySortedSet = buildRepresentation(inputLines, root);
        return calculatePart1Sum(root);
    }

    public Long part2() {
        List<String> inputLines = readInput();
        Directory root = Directory.builder().name("/").build();
        SortedSet<Directory> directorySortedSet = buildRepresentation(inputLines, root);
        return calculatePart2Size(directorySortedSet);
    }

    private List<String> readInput() {
        List<String> lines = new ArrayList<>();
        try {
            ClassLoader classLoader = NoSpaceLeftOnDevice.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return lines;
    }

    private SortedSet<Directory> buildRepresentation(final List<String> inputLines, final Directory root) {
        Set<Directory> directories = new HashSet<>();
        Directory pwd = root;
        directories.add(root);
        int index = 1;
        while (index < inputLines.size()) {
            String command = inputLines.get(index);
            String[] commandChunks = command.split("\\s+");
            if (command.charAt(0) == '$') {
                switch (commandChunks[1]) {
                    case "cd" -> {
                        switch (commandChunks[2]) {
                            case ".." -> {
                                pwd = pwd.getParent();
                            }
                            default -> {
                                //add parent backlink
                                for (Directory childDir : pwd.getChildDirectories()) {
                                    if (childDir.getName().equals(commandChunks[2])) {
                                        pwd = childDir;
                                    }
                                }
                            }
                        }

                        index++;
                    }
                    case "ls" -> {
                        //increment so we see the results from ls command
                        index++;
                        while (index < inputLines.size() && inputLines.get(index).charAt(0) != COMMAND_INDICATOR) {
                            String[] lsResultChunks = inputLines.get(index).split("\\s+");
                            switch (lsResultChunks[0]) {
                                case "dir" -> {
                                    Directory directory = Directory.builder()
                                            .name(lsResultChunks[1])
                                            .parent(pwd)
                                            .build();
                                    pwd.getChildDirectories().add(directory);
                                    directories.add(directory);
                                }
                                default -> {
                                    //file case
                                    pwd.getChildFiles().add(new SystemFile(Long.parseLong(lsResultChunks[0]), lsResultChunks[1]));
                                }
                            }
                            index++;
                        }
                    }
                    case null, default -> throw new IllegalStateException("whoops 🙀");
                }

            }
        }

        SortedSet<Directory> directorySortedSet = new TreeSet<>();

        directories.forEach(directory -> {
            directory.setSize(directory.calculateSize());
            directorySortedSet.add(directory);
        });

        return directorySortedSet;
    }


    private Long calculatePart1Sum(Directory root) {
        Long sum = 0L;
        Queue<Directory> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Directory current = queue.remove();

            long dirSize = current.calculateSize();
            if (dirSize <= 100000) {
                sum += dirSize;
            }

            for (Directory child : current.getChildDirectories()) {
                queue.add(child);
            }

        }

        return sum;
    }

    private Long calculatePart2Size(SortedSet<Directory> directories) {
        List<Long> acceptableDir = new ArrayList<>();
        directories.stream().forEachOrdered(directory -> {
//            System.out.println(directory.getName() + " , dir size: " + directory.getSize() +  " remaining disk:" + (directories.getLast().getSize() - directory.getSize()) + " ; larger than max size? :" + (directories.getLast().getSize() - directory.getSize() < MAX_TOTAL_DISK_SPACE));
            if (directories.getLast().getSize() - directory.getSize() < MAX_TOTAL_DISK_SPACE) {
                acceptableDir.add(directory.getSize());
            }
        });

        Collections.sort(acceptableDir);
        return acceptableDir.getFirst();
    }
}
