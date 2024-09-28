package dev.davidson.ian.advent.year2017.day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FractalArt {

    private static final String INPUT_PATH = "adventOfCode/2017/day21/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day21/sample.txt";

    public static void main(String [] args){
        FractalArt fractalArt = new FractalArt();
        List<Rule> rules = readFile(SAMPLE_PATH);
        log.info("Part1: {}", fractalArt.part1(rules));
    }

    private static List<Rule> readFile(final String filePath){
        List<Rule> rules = new ArrayList<>();
        ClassLoader cl = FractalArt.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                rules.add(Rule.newRule(scanner.nextLine()));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return rules;
    }
}
