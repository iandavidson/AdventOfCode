package dev.davidson.ian.advent.year2025.day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecretEntrance {

    private static final String SAMPLE_FILE_PATH = "adventOfCode/2025/day01/sample.txt";
    private static final String REAL_FILE_PATH = "adventOfCode/2025/day01/real.txt";


    //part2: 6612
    public static void main(String[] args) {
        SecretEntrance secretEntrance = new SecretEntrance();
        List<Instruction> sampleInstructions = readFile(SAMPLE_FILE_PATH);
        log.info("sample Part1: {}", secretEntrance.part1(sampleInstructions));
        log.info("sample Part2: {}", secretEntrance.part2(sampleInstructions));

        List<Instruction> realInstructions = readFile(REAL_FILE_PATH);
        log.info("real Part1: {}", secretEntrance.part1(realInstructions));
        log.info("real Part2: {}", secretEntrance.part2(realInstructions));
    }

    public int part1(final List<Instruction> instructions){

        int count = 0;
        int current = 50;
        for(Instruction instruction : instructions){
            if(instruction.direction() == 'R'){
                current = (current + instruction.magnitude()) % 100;
            }else{
                current = (current - instruction.magnitude()) % 100;
            }

            if(current == 0){
                count++;
            }
        }

        return count;
    }

    public long part2(final List<Instruction> instructions){
        long count = 0L;
        int current = 50;

        for(Instruction instruction: instructions){
            if(instruction.direction() == 'R'){
                count += (current + instruction.magnitude()) / 100;
                current  = (current + instruction.magnitude()) % 100;
            }else{

                /*
                fullRotations: figure out how many multiple of 100 the shift is

                 */

                count += instruction.magnitude() / 100;
                int modded = instruction.magnitude() % 100;

                if(current - modded < 0){
                    count++;
                }

                current = (current - instruction.magnitude()) % 100;
                if(current < 0){
                    current = 100 + current;
                }


//                if(current - instruction.magnitude() < 0){
//                    count++;
//                }
//
//                count += (current - instruction.magnitude()) / 100;
//                current = (current - instruction.magnitude()) % 100;

            }
        }

        return count;
    }


    private static List<Instruction> readFile(final String filePath) {
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader classLoader = SecretEntrance.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                instructions.add(new Instruction(line.substring(0, 1).charAt(0), Integer.valueOf(line.substring(1))));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Bonk");
        }

        return instructions;
    }

}
