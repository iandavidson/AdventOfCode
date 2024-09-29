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
    private static final Integer PART_1_ITERATIONS = 12;
    private static final Character FILLED = '#';
    private static final Character EMPTY = '.';

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

    public Integer part1(final List<Rule> rules){
        List<String> current = new ArrayList<>();
        current.add(".#.");
        current.add("..#");
        current.add("###");

        for(int i = 0;i < PART_1_ITERATIONS; i++){
            current = applyTurn(current, rules);
        }

        int count = 0;
        for(String line : current){
            for(char ch : line.toCharArray()){
                if(ch == FILLED){
                    count++;
                }
            }
        }

        return count;
    }

    private List<String> applyTurn(final List<String> current, final List<Rule> rules){
        List<List<String>> result = new ArrayList<>();

        if(current.size() % 2 == 0){
            int elements = current.size() / 2;

            for(int i =0 ; i < elements; i++) {
                //i = 0; -> 0,2
                //i = 1; -> 2,4

                List<String> resultRow = new ArrayList<>();
                for (int j = 0; j < elements; j++) {
                    List<String> tempSection = new ArrayList<>();
                    tempSection.add(current.get(i*elements).substring(j* elements, (j+1) * elements));
                    tempSection.add(current.get(i*elements + 1).substring(j* elements, (j+1) * elements));

                    for(Rule rule : rules){
                        //check for match, convert
                    }


                }
            }
            // split
            current.subList()
        }else{
            int elements = current.size() / 3;
        }

        return
    }

}
