package org.example.advent.year2022.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CathodeRayTube {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day10/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day10/input.txt";


    public static void main(String [] args){
        CathodeRayTube cathodeRayTube = new CathodeRayTube();
        System.out.println("part1: " + cathodeRayTube.part1());
    }

    public Long part1(){
        List<Instruction> instructions = readFile();

        Long cycle = 0l;
        Long register = 1l;

        for(Instruction instruction : instructions){
            cycle += instruction.instructionType().getCycles();
            register += instruction.amount();

            if((cycle -20) % 40 == 0){
                System.out.println
            }
        }

    }

    private List<Instruction> readFile() {
        List<String> inputLines = new ArrayList<>();
        ClassLoader cl = CathodeRayTube.class.getClassLoader();
        File file = new File(Objects.requireNonNull(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile()));
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.err.println("File could not be found");
            throw new RuntimeException(e);
        }

        return inputLines.stream().map(Instruction::parse).toList();
    }
}
