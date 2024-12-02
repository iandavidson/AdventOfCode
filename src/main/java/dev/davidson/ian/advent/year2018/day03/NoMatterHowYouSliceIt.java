package dev.davidson.ian.advent.year2018.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoMatterHowYouSliceIt {

    private static final String INPUT_PATH = "adventOfCode/2018/day03/input.txt";

    public static void main(String [] args){
        NoMatterHowYouSliceIt noMatterHowYouSliceIt = new NoMatterHowYouSliceIt();
        List<Claim> claims = readFile(INPUT_PATH);

        log.info("Part1: {}", noMatterHowYouSliceIt.part1(claims));
    }

    public Long part1(final List<Claim> claims){
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

        for(Claim claim : claims){

        }

    }

    private static List<Claim> readFile(final String filePath){
        List<Claim> claims = new ArrayList<>();

        ClassLoader cl = NoMatterHowYouSliceIt.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                claims.add(Claim.newClaim(scanner.nextLine()));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read input file");
        }

        return claims;
    }
}
