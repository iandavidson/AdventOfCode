package dev.davidson.ian.advent.year2017.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KnotHash {

    private static final String INPUT_PATH = "adventOfCode/2017/day10/input.txt";
    private static final Integer SIZE = 256;
    public static void main(String [] args){
        KnotHash knotHash = new KnotHash();
        List<Integer> input = readFile(INPUT_PATH);
        log.info("Part1: {}", knotHash.part1(input));
    }

    public Integer part1(final List<Integer> input){
        List<Integer> rope = IntStream.range(0, SIZE).boxed().collect(Collectors.toList());
        int skip = 0;
        int currentIndex = 0;
        for(int shift : input){
            int nextIndex = (currentIndex + shift + skip) % SIZE;
            tieKnot(rope, currentIndex, nextIndex);
            currentIndex = nextIndex;
            skip++;
        }


        return rope.get(0) * rope.get(1);
    }

    //mutates rope btw
    private void tieKnot(List<Integer> rope, final int currentIndex, final int finalIndex){
        //identify subsequence, reverse, place back in initial space
        List<Integer> indexSequence = new ArrayList<>();
        for(int i = currentIndex; i  <  finalIndex; i++){
            indexSequence.add(i % SIZE);
        }

        Collections.reverse(indexSequence);

        List<Integer> valueSequence = new ArrayList<>();
        for(int idx : indexSequence){
            valueSequence.add(rope.get(idx));
        }


        for(int i = 0; i < valueSequence.size(); i++){
            rope.set((i + currentIndex) % SIZE, valueSequence.get(i));
        }


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
