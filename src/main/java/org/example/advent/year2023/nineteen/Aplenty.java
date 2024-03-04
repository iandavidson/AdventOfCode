package org.example.advent.year2023.nineteen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Aplenty {
    private static final String LESS = "<";
    private static final String GREATER = ">";
    private static final String EQUAL = "=";
    private static final String R = "R";
    private static final String A = "A";
    private static final String L_CURLY = "{";
    private static final String R_CURLY = "}";
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
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
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

    private static List<String> tokenize(String rawRule){
        List<String> tokens = new ArrayList<>();
        //basecase rule
//        if(rawRule.equals(A) || rawRule.equals(R)){
        if(rawRule.length() < 4){
            tokens.add(rawRule);
            return tokens;
        }

        tokens.add(rawRule.substring(0,1));
        tokens.add(rawRule.substring(1,2));

        int index = 2;
        String number = "";
        while(Character.isDigit(rawRule.charAt(index))){
            if(Character.isDigit(rawRule.charAt(index))){
                index++;
            } else {
                break;
            }
        }

        tokens.add(rawRule.substring(2, index));
        tokens.add(rawRule.substring(index, index+1));
        tokens.add(rawRule.substring(index+1));

        return tokens;
    }

    private static Map<String, Instruction> inputToRawInstruction(List<String> inputs) {
        Map<String, Instruction> instructions = new HashMap<>();

        for (String inputLine : inputs) {
            String label = inputLine.substring(0, inputLine.indexOf(L_CURLY));
            List<Rule> rules = new ArrayList<>();
            String[] rawRules = inputLine.substring(inputLine.indexOf(L_CURLY) + 1, inputLine.indexOf(R_CURLY)).split(COMMA);

            for (String rule : rawRules) {
                rules.add(Rule.builder().tokens(tokenize(rule)).build());
            }

            instructions.put(label, Instruction.builder()
                    .label(label)
                    .rules(rules)
                    .build());
        }

        instructions.put(R, Instruction.builder().label(R).rules(Collections.singletonList(Rule.builder().tokens(Collections.singletonList(R)).build())).build());
        instructions.put(A, Instruction.builder().label(A).rules(Collections.singletonList(Rule.builder().tokens(Collections.singletonList(A)).build())).build());

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

    private Long processPart(Part part, Map<String, Instruction> grammar) {
        //Find instruction
        Instruction currentInstruction = grammar.get(START_LABEL);
        int index = 0;
        while(true){
            Rule currentRule = currentInstruction.getRules().get(index);
            if(currentRule.getTokens().size() == 1){
                //branch or terminal state
                if(currentRule.getTokens().contains(R)){
                    //rejected, return
                    return 0L;
                }else if(currentRule.getTokens().contains(A)){
                    //accepted, remember part
                    return part.sum();
                } else {
                    currentInstruction = grammar.get(currentRule.getTokens().get(0));
                    index = 0;
                    //must be branch

                }
            } else if(currentRule.getTokens().contains(LESS)){
                int memberOperand =  part.getMember(currentRule.getTokens().get(0));
                //if true -> reassign instruction and reset index
                //if false -> increment index

                if(memberOperand < Integer.parseInt(currentRule.getTokens().get(2))){
                    currentInstruction = grammar.get(currentRule.getTokens().get(4));
                    index =0;
                }else {
                    index++;
                }

            } else if(currentRule.getTokens().contains(GREATER)){
                int memberOperand =  part.getMember(currentRule.getTokens().get(0));
                if(memberOperand > Integer.parseInt(currentRule.getTokens().get(2))) {
                    currentInstruction = grammar.get(currentRule.getTokens().get(4));
                    index =0;
                }else {
                    index++;
                }
            }
        }

        //apply rule, if true, branch

        //if false fall through next rule in list

        /*
        always check for:
        - rule type:
            - some comparison (< || >) then branch
                - '<' -> execute code to do GT
                - '>' -> execute code to do LT
                - depending on result, branch or we are done
            - direct branch to label (rule.tokens.size() == 1)
            - final state (A || R) (rule.tokens.size() == 1)
                - A -> return part.sum()
                - R -> return 0L;
         */
    }

    public Long part1() {
        List<List<String>> inputs = readFile();
        Map<String, Instruction> grammar = inputToRawInstruction(inputs.get(0));
        List<Part> parts = inputToParts(inputs.get(1));

        Long sum = 0L;
        for(Part part : parts){
            //process
            sum  += processPart(part, grammar);

        }

        /*
        intuition:

        Most convenient way to represent instructions:
        Map<"Instruction Label", Instruction>
        -> when you encounter a token that is not a known symbol, we must find that instruction:
        --> in order to process: px{a<2006:qkq,m>2090:A,rfg}, we must also expand and apply "qkq" and "rfg" instructions.
        --> using hash map with label as key will be helpful
         */
        return sum;
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

        public int getMember(String member){
            return switch(member){
                case "x" -> this.x;
                case "m" -> this.m;
                case "a" -> this.a;
                case "s" -> this.s;
                default -> -1;
            };
        }


    }


    @AllArgsConstructor
    @Data
    @Builder
    public static class Instruction {
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
    public static class Rule {
        // [a, <, 2006, :, qkq]
        @Builder.Default
        private final List<String> tokens = new ArrayList<>();
    }
}
