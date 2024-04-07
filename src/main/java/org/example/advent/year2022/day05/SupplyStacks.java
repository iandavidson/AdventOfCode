package org.example.advent.year2022.day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class SupplyStacks {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day05/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day05/input.txt";
    private static final Integer INPUT_WIDTH = 9; //real
//    private static final Integer INPUT_WIDTH = 3; // sample

    public static void main(String[] args) {
        SupplyStacks supplyStacks = new SupplyStacks();
        System.out.println("Part1: " + supplyStacks.part1());
//        System.out.println("Part2: " + supplyStacks.part2());
    }


    public String part1() {
        List<String> inputLines = readFile();
        List<Deque<Block>> stacks = processBlocks(inputLines);
        List<Instruction> instructions = processInstructions(inputLines);

        return "...";
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

    private List<Deque<Block>> processBlocks(List<String> inputLines){
        List<Deque<Block>> deques = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            deques.add(new ArrayDeque<>());
        }

        for(int i = 0; i < 8; i++){
            String temp = inputLines.get(i);
            for(int j = 0; j < INPUT_WIDTH; j++){
                if(temp.charAt(j*4 + 1) != ' '){
                    deques.get(j).add(new Block(temp.charAt(j*4 + 1)));
                }
            }
        }

        //deques are upside down I think...

        return deques;
    }

    private List<Instruction> processInstructions(List<String> inputLines){
        int index = 0;
        while(!inputLines.get(index).isEmpty()){
            index++;
        }

        index++;

        List<Instruction> instructions = new ArrayList<>();
        for(int i = index; i < inputLines.size(); i++){
            String [] chunks = inputLines.get(i).split("\\s+");
            instructions.add(new Instruction(Integer.parseInt(chunks[3]), Integer.parseInt(chunks[5]), Integer.parseInt(chunks[1])));
        }

        return instructions;
    }

}
