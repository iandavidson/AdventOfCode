package org.example.advent.year2022.day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SupplyStacks {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day05/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day05/input.txt";
    private static final Integer INPUT_WIDTH = 9; //real
//    private static final Integer INPUT_WIDTH = 3; // sample

    private static final Integer INPUT_DEPTH = 8; // real
//    private static final Integer INPUT_DEPTH = 3; // sample

    public static void main(String[] args) {
        SupplyStacks supplyStacks = new SupplyStacks();
        System.out.println("Part1: " + supplyStacks.part1());
        System.out.println("Part2: " + supplyStacks.part2());
    }


    public String part1() {
        List<String> inputLines = readFile();
        List<Deque<Block>> deques = processBlocks(inputLines);
        List<Instruction> instructions = processInstructions(inputLines);
        executeInstructions(deques, instructions);

        return concatHeads(deques);
    }

    public String part2() {
        List<String> inputLines = readFile();
        List<Deque<Block>> deques = processBlocks(inputLines);
        List<Instruction> instructions = processInstructions(inputLines);
        executeInstructionsPart2(deques, instructions);

        return concatHeads(deques);
    }

    private List<String> readFile() {
        List<String> lines = new ArrayList<>();
        try {
            ClassLoader classLoader = SupplyStacks.class.getClassLoader();
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

    private List<Deque<Block>> processBlocks(List<String> inputLines) {
        List<Deque<Block>> deques = new ArrayList<>(INPUT_WIDTH);
        for (int i = 0; i < INPUT_WIDTH; i++) {
            deques.add(new ArrayDeque<>());
        }

        List<List<Block>> bigBlockTowers = new ArrayList<>();
        for (int i = 0; i < INPUT_WIDTH; i++) {
            bigBlockTowers.add(new ArrayList<>());
        }

        for (int i = 0; i < INPUT_DEPTH; i++) {
            String line = inputLines.get(i);
            for (int j = 0; j < INPUT_WIDTH; j++) {
                if (line.length() < (j * 4 + 1)) {
                    continue;
                } else if (line.charAt(j * 4 + 1) != ' ') {
                    bigBlockTowers.get(j).add(new Block(line.charAt(j * 4 + 1)));
                }
            }
        }

        for (int i = 0; i < bigBlockTowers.size(); i++) {
            deques.set(i, new ArrayDeque<>(bigBlockTowers.get(i)));
        }

        return deques;
    }

    private List<Instruction> processInstructions(List<String> inputLines) {
        int index = 0;
        while (!inputLines.get(index).isEmpty()) {
            index++;
        }

        index++;

        List<Instruction> instructions = new ArrayList<>();
        for (int i = index; i < inputLines.size(); i++) {
            String[] chunks = inputLines.get(i).split("\\s+");
            instructions.add(new Instruction(Integer.parseInt(chunks[3]) - 1, Integer.parseInt(chunks[5]) - 1, Integer.parseInt(chunks[1])));
        }

        return instructions;
    }

    private void executeInstructions(List<Deque<Block>> deques, List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.moveAmount(); i++) {
                deques.get(instruction.toIndex()).push(deques.get(instruction.fromIndex()).pop());
            }
        }
    }

    private void executeInstructionsPart2(List<Deque<Block>> deques, List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            List<Block> temp = new ArrayList<>();
            for (int i = 0; i < instruction.moveAmount(); i++) {
                temp.add(deques.get(instruction.fromIndex()).pop());
            }

            Collections.reverse(temp);

            for (Block block : temp) {
                deques.get(instruction.toIndex()).push(block);
            }
        }
    }

    private String concatHeads(List<Deque<Block>> deques) {
        StringBuilder sb = new StringBuilder();
        for (Deque<Block> blockDeque : deques) {
            assert blockDeque.peek() != null;
            sb.append(blockDeque.peek().label());
        }

        return sb.toString();
    }

}
