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

        return 0l;
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
}
