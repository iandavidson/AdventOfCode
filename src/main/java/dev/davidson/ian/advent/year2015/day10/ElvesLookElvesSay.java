package dev.davidson.ian.advent.year2015.day10;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ElvesLookElvesSay {

    public static final String INPUT_PATH = "adventOfCode/2015/day10/input.txt";

    public static void main(String [] args){
        ElvesLookElvesSay elvesLookElvesSay = new ElvesLookElvesSay();
        elvesLookElvesSay.execute();
    }

    public void execute(){
        String line = readFile();
        for(int i = 0; i < 50; i++){

            String [] chunks = line.split("(?<=(.))(?!\\1)");
            String next = "";
            for(String chunk: chunks){
                next = next + chunk.length() +  chunk.charAt(0);
            }

            line = next;

            if(i == 39){
                log.info("part1: {}", line.length());
            }

            if(i == 49){
                log.info("part2: {}", line.length());
            }
        }
    }

    private String readFile(){
        ClassLoader cl = ElvesLookElvesSay.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        }catch(FileNotFoundException e){
            throw new IllegalStateException();
        }
    }
}
