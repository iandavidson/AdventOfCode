package org.example.advent.year2022.day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DistressSignal {

    private static final String INPUT_PATH = "adventOfCode/2022/day13/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2022/day13/sample.txt";

    public static void main(String[] args) {
        DistressSignal distressSignal = new DistressSignal();
        System.out.println("Part1: " + distressSignal.part1());
        System.out.println("Part2: " + distressSignal.part2());
    }

    public Long part1() {
        List<PacketPair> packetPairs = part1ProcessInput(readFile());

        Long runningSum = 0L;

        for (int i = 0; i < packetPairs.size(); i++) {
            if (packetPairs.get(i).isPacketValid()) {
//                System.out.println("adding i:" + i + "; value added:" + (i + 1));
                runningSum += (i + 1);
            }
        }

        return runningSum;
    }

    public Long part2() {
        List<DistressElement> distressElements = part2ProcessInput(readFile());
        DistressElement added1 = new DistressElement("[[2]]");
        distressElements.add(added1);
        DistressElement added2 = new DistressElement("[[6]]");
        distressElements.add(added2);

        Collections.sort(distressElements);
        Collections.reverse(distressElements);
        return (long) (distressElements.indexOf(added1) + 1) * (distressElements.indexOf(added2) + 1);
    }


    private List<List<String>> readFile() {
        List<List<String>> inputSections = new ArrayList<>();

        ClassLoader cl = DistressSignal.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            List<String> inputChunk = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    inputSections.add(inputChunk);
                    inputChunk = new ArrayList<>();
                } else {
                    inputChunk.add(line);
                }
            }
            inputSections.add(inputChunk);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return inputSections;
    }

    private List<PacketPair> part1ProcessInput(List<List<String>> inputSections) {
        List<PacketPair> packetPairs = new ArrayList<>();
        for (List<String> inputSection : inputSections) {
            packetPairs.add(new PacketPair(new DistressElement(inputSection.get(0)), new DistressElement(inputSection.get(1))));
        }

        return packetPairs;
    }

    private List<DistressElement> part2ProcessInput(List<List<String>> inputSections) {
        List<DistressElement> distressElements = new ArrayList<>();
        for (List<String> inputSection : inputSections) {
            for (String line : inputSection) {
                distressElements.add(new DistressElement(line));
            }
        }
        return distressElements;
    }
}
