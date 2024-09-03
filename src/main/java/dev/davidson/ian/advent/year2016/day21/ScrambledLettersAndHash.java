package dev.davidson.ian.advent.year2016.day21;

import dev.davidson.ian.advent.year2016.day21.Instruction.MovePositionInstruction;
import dev.davidson.ian.advent.year2016.day21.Instruction.ReversePositionsInstruction;
import dev.davidson.ian.advent.year2016.day21.Instruction.RotateInstruction;
import dev.davidson.ian.advent.year2016.day21.Instruction.RotatePositionInstruction;
import dev.davidson.ian.advent.year2016.day21.Instruction.SwapLetterInstruction;
import dev.davidson.ian.advent.year2016.day21.Instruction.SwapPositionInstruction;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScrambledLettersAndHash {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day21/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day21/input.txt";

    public static void main(String [] args){
        String sampleInput = "abcde";
        String realInput = "abcdefgh";
        ScrambledLettersAndHash scrambledLettersAndHash = new ScrambledLettersAndHash();
        List<Applyable> instructions = readFile(SAMPLE_PATH);
        log.info("Part1: {}", scrambledLettersAndHash.part1(instructions, sampleInput));
    }

    public String part1(final List<Applyable> instructions, final String sequence){
        String result = sequence;
        for(Applyable applyable : instructions){
            result = applyable.apply(result);
        }

        return result;
    }



    private static List<Applyable> readFile(final String filePath){
        List<Applyable> input = new ArrayList<>();

        ClassLoader cl = ScrambledLettersAndHash.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner   = new Scanner(file);
            while(scanner.hasNextLine()){
                input.add(parseInstruction(scanner.nextLine()));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return input;
    }

    private static Applyable parseInstruction(final String line){
        String [] words = line.split("\\s+");

        if(line.startsWith("swap position")){
            return new SwapPositionInstruction(Integer.parseInt(words[2]), Integer.parseInt(words[5]));
        }else if(line.startsWith("swap letter")){
            return new SwapLetterInstruction(words[2].charAt(0), words[5].charAt(0));
        }else if(line.startsWith("reverse")){
            return new ReversePositionsInstruction(Integer.parseInt(words[2]), Integer.parseInt(words[4]));
        }else if(line.startsWith("rotate based")){
            return new RotatePositionInstruction(words[6].charAt(0));
        }else if(line.startsWith("rotate")){
            return new RotateInstruction(Integer.parseInt(words[2]), words[1].equals("left") ? RotationDirection.Left : RotationDirection.Right);
        }else if(line.startsWith("move")){
            return new MovePositionInstruction(Integer.parseInt(words[2]), Integer.parseInt(words[5]));
        }else{
            throw new IllegalStateException();
        }
    }
}
