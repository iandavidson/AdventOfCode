package dev.davidson.ian.advent.year2015.day08;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class Matchsticks {

    private static final String INPUT_PATH = "adventOfCode/2015/day08/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day08/sample.txt";

    public static void main(String [] args){
        Matchsticks matchsticks = new Matchsticks();
        log.info("part1: {}", matchsticks.part1());
    }

    public int part1(){
        List<Code> codes = readFile();

        long literalTotal = 0L;
        long memTotal = 0L;

        for(Code code : codes){
            literalTotal += code.literalLength();
            memTotal += code.inMemLength();
        }

        //1461 too high
        return (int) (literalTotal - memTotal);
    }

    private List<Code> readFile(){
        List<Code> codes = new ArrayList<>();

        ClassLoader cl = Matchsticks.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                codes.add(Code.newCode(scanner.nextLine()));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException();
        }

        return codes;
    }
}
