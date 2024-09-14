package dev.davidson.ian.advent.year2017.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KnotHash {

    private static final String INPUT_PATH = "adventOfCode/2017/day10/input.txt";

    public static void main(String [] args){
        KnotHash knotHash = new KnotHash();
        List<Integer> input = readFile(INPUT_PATH);
        log.info("Part1: {}", knotHash.part1());
    }

    public Integer part1(){

    }

    private static List<Integer> readFile(final String filePath){
        ClassLoader cl = KnotHash.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            return Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).boxed().toList();
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }
}
