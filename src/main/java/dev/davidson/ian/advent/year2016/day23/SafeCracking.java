package dev.davidson.ian.advent.year2016.day23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SafeCracking {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day23/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day23/input.txt";

    public static void main(String [] args){
        SafeCracking safeCracking = new SafeCracking();
        List<Instruction> instructions = readFile(SAMPLE_PATH);
        log.info("Part1: {}", safeCracking.part1(instructions));
    }

    public Long part1(final List<Instruction> instructions){

    }

    private static List<Instruction> readFile(final String filePath){
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = SafeCracking.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                instructions.add(Instruction.newInstruction(scanner.nextLine()));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided file path");
        }

        return instructions;
    }


}
