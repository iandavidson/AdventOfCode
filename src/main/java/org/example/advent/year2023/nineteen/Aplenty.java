package org.example.advent.year2023.nineteen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Aplenty {

    private static final String LESS = "<";
    private static final String GREATER = ">";
    private static final String EQUAL = "=";
    private static final String R = "R";
    private static final String A = "A";
    private static final String L_CURLY = "{";
    private static final String R_CURLY = "}";
    private static final String COLON = ":";
    private static final String COMMA = ",";

    private static final String START_LABEL = "in";

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day19/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day19/input.txt";

    /*
  example input:

    hdj{m>838:A,pv}
    //more of these

    {x=787,m=2655,a=1222,s=2876}
    //more of these
     */

    private static List<List<String>> readFile() {
        List<List<String>> input = new ArrayList<>();
        List<String> inputSet1 = new ArrayList<>();
        List<String> inputSet2 = new ArrayList<>();
        input.add(inputSet1);
        input.add(inputSet2);
        boolean seenBreak = false;
        try {
            ClassLoader classLoader = Aplenty.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(SAMPLE_INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String nextLine = myReader.nextLine();
                if (nextLine.isBlank()) {
                    seenBreak = true;
                } else if (!seenBreak) {
                    inputSet1.add(nextLine);
                } else {
                    inputSet2.add(nextLine);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    private static Map<String, Instruction> inputToRawInstruction(List<String> inputs) {
        Map<String, Instruction> instructions = new HashMap<>();

        for (String inputLine : inputs) {
            String label = inputLine.substring(0, inputLine.indexOf(L_CURLY));
            List<Rule> rules = new ArrayList<>();
            String[] rawRules = inputLine.substring(inputLine.indexOf(L_CURLY) + 1, inputLine.indexOf(R_CURLY)).split(COMMA);

            for (String rule : rawRules) {
                rules.add(Rule.builder().tokens(Arrays.stream(rule.split(COMMA)).toList()).build());
            }

            instructions.put(label, Instruction.builder()
                    .label(label)
                    .rules(rules)
                    .build());
        }

        return instructions;
    }


    private static List<Part> inputToParts(List<String> inputs) {
        List<Part> parts = new ArrayList<>();
        for (String inputLine : inputs) {
            String[] chunks = inputLine.substring(inputLine.indexOf(L_CURLY) + 1, inputLine.indexOf(R_CURLY)).split(COMMA);
            parts.add(
                    Part.builder()
                            .x(Integer.parseInt(chunks[0].split(EQUAL)[1]))
                            .m(Integer.parseInt(chunks[1].split(EQUAL)[1]))
                            .a(Integer.parseInt(chunks[2].split(EQUAL)[1]))
                            .s(Integer.parseInt(chunks[3].split(EQUAL)[1]))
                            .build()
            );
        }

        return parts;
    }

    private static Long processPart(Part part, Map<String, Instruction> grammar){
        //Find instruction

        //apply rule, if true, branch

        //if false fall through next rule in list

        /*
        always check for:
        - rule type:
            - some comparison (< || >) then branch
            - direct branch to label
            - final state (A || R)
                - A -> return part.sum()
                - R -> return 0L;
         */


        return 0L;
    }

    public Long part1() {
        List<List<String>> inputs = readFile();
        Map<String, Instruction> grammar = inputToRawInstruction(inputs.get(0));
        List<Part> parts = inputToParts(inputs.get(1));

        Long sum = 0L;
        for(Part part : parts){
            //process
            sum  += processesPart(part, grammar);
        }

        /*
        intuition:

        Most convenient way to represent instructions:
        Map<"Instruction Label", Instruction>
        -> when you encounter a token that is not a known symbol, we must find that instruction:
        --> in order to process: px{a<2006:qkq,m>2090:A,rfg}, we must also expand and apply "qkq" and "rfg" instructions.
        --> using hash map with label as key will be helpful
         */
        return 0L;
    }

    public static void main(String[] args) {
        Aplenty aplenty = new Aplenty();
        long result = aplenty.part1();
        System.out.println("result1: " + result);
    }

    @AllArgsConstructor
    @Data
    @Builder
    public static class Part {
        private final int x;
        private final int m;
        private final int a;
        private final int s;

        public long sum() {
            return (long) x + m + a + s;
        }
    }


    @AllArgsConstructor
    @Data
    @Builder
    public class Instruction {
//        px{a<2006:qkq,m>2090:A,rfg}

        //px
        private final String label;

        // [ {a, <, 2006, :, qkq}, {m, >, 2090, :, A}, {rfg} ]
        @Builder.Default
        private final List<Rule> rules = new ArrayList<>();
    }

    @AllArgsConstructor
    @Data
    @Builder
    public class Rule {
        // [a, <, 2006, :, qkq]
        @Builder.Default
        private final List<String> tokens = new ArrayList<>();
    }
}
