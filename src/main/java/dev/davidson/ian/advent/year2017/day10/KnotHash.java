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
    private static final List<Integer> END = List.of(17, 31, 73, 47, 23);

    public static void main(String [] args){
        KnotHash knotHash = new KnotHash();
        String input = readFile(INPUT_PATH);
        log.info("Part1: {}",
                knotHash.part1(Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).boxed().toList())
        );

        log.info("Part2: {}", knotHash.part2(input));
    }

    public Integer part1(final List<Integer> input){
        List<Integer> rope = IntStream.range(0, SIZE).boxed().collect(Collectors.toList());
        int skip = 0;
        int currentIndex = 0;
        for(int shift : input){
            tieKnot(rope, currentIndex, currentIndex + shift);
            currentIndex = (currentIndex + shift + skip) % SIZE;
            skip++;
        }

        return rope.get(0) * rope.get(1);
    }

    public String part2(final String input){
        byte [] bytes = input.getBytes();
        List<Integer> inputs = IntStream.range(0, bytes.length).map(i -> bytes[i]).boxed().collect(Collectors.toList());
        inputs.addAll(END);
        List<Integer> rope = IntStream.range(0, SIZE).boxed().collect(Collectors.toList());

        int skip = 0;
        int currentIndex = 0;
        for(int i = 0; i< 64; i++){
            for(int shift : inputs){
                tieKnot(rope, currentIndex, currentIndex + shift);
                currentIndex = (currentIndex + shift + skip) % SIZE;
                skip++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 16; i++){
            Integer temp = rope.get(16 * i);
            for(int j = 1; j < 16; j++){
                temp ^= rope.get(16 * i + j);
            }

            sb.append(Integer.toHexString(temp));
        }

        return sb.toString();
    }

    private void tieKnot(List<Integer> rope, final int currentIndex, final int finalIndex){
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

    private static String readFile(final String filePath){
        ClassLoader cl = KnotHash.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }
}
