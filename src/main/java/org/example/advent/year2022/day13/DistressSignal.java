package org.example.advent.year2022.day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DistressSignal {

    private static final String INPUT_PATH = "adventOfCode/2022/day13/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2022/day13/sample.txt";

    public static void main(String[] args) {
        DistressSignal distressSignal = new DistressSignal();
        System.out.println("Part1: " + distressSignal.part1());
    }

    public Long part1() {
        List<PacketPair> packetPairs = readFile();

        return proccessPairs(packetPairs);
    }


    private List<PacketPair> readFile() {
        List<List<String>> inputChunks = new ArrayList<>();

        ClassLoader cl = DistressSignal.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            List<String> inputChunk = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    inputChunks.add(inputChunk);
                    inputChunk = new ArrayList<>();
                } else {
                    inputChunk.add(line);
                }
            }
            inputChunks.add(inputChunk);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<PacketPair> packetPairs = new ArrayList<>();
        for (List<String> inputChunk : inputChunks) {
            packetPairs.add(new PacketPair(DistressElement.newDistressPacket(inputChunk.get(0)), DistressElement.newDistressPacket(inputChunk.get(1))));
        }


        return packetPairs;
    }

    private Long processPairs(List<PacketPair> packetPairs){
        Long runningSum = 0L;
        /*
        rules:
        go left and right packets concurrently:


    If both values are integers, the lower integer should come first.
        If the left integer is lower than the right integer, the inputs are in the right order.
        If the left integer is higher than the right integer, the inputs are not in the right order.
        Otherwise, the inputs are the same integer; continue checking the next part of the input. (implicitly OK)

    If both values are lists, compare the first value of each list, then the second value, and so on.
        If the left list runs out of items first, the inputs are in the right order.
        If the right list runs out of items first, the inputs are not in the right order.
        If the lists are the same length and no comparison makes a decision about the order, continue checking the next part of the input.

    If exactly one value is an integer,
        convert the integer to a list which contains that integer as its only value, then retry the comparison.
        For example, if comparing [0,0,0] and 2, convert the right value to [2] (a list containing 2); the result is then found by instead comparing [0,0,0] and [2].

         */


        for(PacketPair packetPair : packetPairs){

        }
    }
}
