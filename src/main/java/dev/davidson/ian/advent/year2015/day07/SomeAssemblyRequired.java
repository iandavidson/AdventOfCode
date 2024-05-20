package dev.davidson.ian.advent.year2015.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SomeAssemblyRequired {

    private static final String SAMPLE_PATH = "adventOfCode/2015/day07/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2015/day07/input.txt";

    public static void main(String [] args){
        SomeAssemblyRequired someAssemblyRequired = new SomeAssemblyRequired();

    }

    public int part1(){
        List<Instruction> instructions = readFile();
        Map<String, Integer> map = new HashMap<>();


    }

    private List<Instruction> readFile(){
        List<Instruction> instructions = new ArrayList<>();

        ClassLoader cl = SomeAssemblyRequired.class.getClassLoader();
        File file = new File(cl.getResource(SAMPLE_PATH).getFile());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                instructions.add(InstructionFactory.newInstruction(scanner.nextLine()));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException();
        }

        return instructions;
    }


}
