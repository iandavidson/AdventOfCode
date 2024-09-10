package dev.davidson.ian.advent.year2017.day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InverseCaptcha {

    private static final String INPUT_PATH = "adventOfCode/2017/day01/input.txt";

    public static void main(String [] args){
        InverseCaptcha inverseCaptcha = new InverseCaptcha();
        String input = readFile(INPUT_PATH);
        log.info("Part1: {}", inverseCaptcha.execute(input, true));
        log.info("Part2: {}", inverseCaptcha.execute(input, false));
    }

    public Long execute(final String input, final boolean part1){
        char [] inputChars = input.toCharArray();
        int n = inputChars.length;
        long result = 0L;
        for(int i = 0 ; i < inputChars.length; i++){
            if(part1 && inputChars[i] == inputChars[(i+1) % n]){
                result += Long.parseLong(String.valueOf(inputChars[i]));
            }else if(!part1 && inputChars[i] == inputChars[(i + n/2) % n]){
                result += Long.parseLong(String.valueOf(inputChars[i]));
            }
        }

        return result;
    }

    private static String readFile(final String filePath){
        ClassLoader cl = InverseCaptcha.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        }catch(FileNotFoundException e){
            throw new IllegalStateException();
        }

    }

}
