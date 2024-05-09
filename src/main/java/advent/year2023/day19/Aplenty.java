package advent.year2023.day19;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

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

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day19/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2023/day19/input.txt";

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

    private static List<String> tokenize(String rawRule) {
        List<String> tokens = new ArrayList<>();
        //basecase rule
        if (rawRule.length() < 4) {
            tokens.add(rawRule);
            return tokens;
        }

        tokens.add(rawRule.substring(0, 1));
        tokens.add(rawRule.substring(1, 2));

        int index = 2;
        String number = "";
        while (Character.isDigit(rawRule.charAt(index))) {
            if (Character.isDigit(rawRule.charAt(index))) {
                index++;
            } else {
                break;
            }
        }

        tokens.add(rawRule.substring(2, index));
        tokens.add(rawRule.substring(index, index + 1));
        tokens.add(rawRule.substring(index + 1));

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
        while (true) {
            Rule currentRule = currentInstruction.getRules().get(index);
            if (currentRule.getTokens().size() == 1) {
                //branch or terminal state
                if (currentRule.getTokens().contains(R)) {
                    //rejected, return
                    return 0L;
                } else if (currentRule.getTokens().contains(A)) {
                    //accepted, remember part
                    return part.sum();
                } else {
                    currentInstruction = grammar.get(currentRule.getTokens().get(0));
                    index = 0;

                }
            } else if (currentRule.getTokens().contains(LESS)) {
                int memberOperand = part.getMember(currentRule.getTokens().get(0));
                //if true -> reassign instruction and reset index
                //if false -> increment index

                if (memberOperand < Integer.parseInt(currentRule.getTokens().get(2))) {
                    currentInstruction = grammar.get(currentRule.getTokens().get(4));
                    index = 0;
                } else {
                    index++;
                }

            } else if (currentRule.getTokens().contains(GREATER)) {
                int memberOperand = part.getMember(currentRule.getTokens().get(0));
                if (memberOperand > Integer.parseInt(currentRule.getTokens().get(2))) {
                    currentInstruction = grammar.get(currentRule.getTokens().get(4));
                    index = 0;
                } else {
                    index++;
                }
            }
        }
    }

    public Long part1() {
        List<List<String>> inputs = readFile();
        Map<String, Instruction> grammar = inputToRawInstruction(inputs.get(0));
        List<Part> parts = inputToParts(inputs.get(1));

        Long sum = 0L;
        for (Part part : parts) {
            //process
            sum += processPart(part, grammar);

        }

        return sum;
    }

    private static Long processPartSpan(PartSpan partSpan, Map<String, Instruction> grammar) {
        Long count = 0L;

        Branch init = Branch.builder().instructionLabel(START_LABEL).ruleIndex(0).partSpan(partSpan).build();

        Queue<Branch> queue = new LinkedList<>();
        queue.add(init);

        while (!queue.isEmpty()) {

            Branch branch = queue.remove();
            Instruction currentInstruction = grammar.get(branch.getInstructionLabel());
            int index = branch.getRuleIndex();
            PartSpan current = branch.getPartSpan();

            while (true) {
                Rule currentRule = currentInstruction.getRules().get(index);
                if (currentRule.getTokens().size() == 1) {
                    if (currentRule.getTokens().contains(R)) {
                        break;
                    } else if (currentRule.getTokens().contains(A)) {
                        count += current.product();
                        break;
                    } else {
                        currentInstruction = grammar.get(currentRule.getTokens().get(0));
                        index = 0;
                    }
                } else if (currentRule.getTokens().contains(LESS)) {
                    PartComp member = PartComp.valueOf(currentRule.getTokens().get(0));
                    PartSpan.Range range = current.getMember(member);

                    int split = Integer.parseInt(currentRule.getTokens().get(2));

                    if (split < range.getStart()) {
                        queue.add(Branch.builder()
                                .partSpan(current.bisect(split - 1, member).get(0))
                                .instructionLabel(currentInstruction.getLabel())
                                .ruleIndex(index + 1)
                                .build());
                    } else if (split > range.getEnd()) {
                        queue.add(Branch.builder()
                                .partSpan(current.bisect(split - 1, member).get(0))
                                .instructionLabel(grammar.get(currentRule.getTokens().get(4)).getLabel())
                                .ruleIndex(0)
                                .build());
                    } else {
                        List<PartSpan> splitSpans = current.bisect(split - 1, member);
                        //span that evaluates expression to true
                        queue.add(Branch.builder()
                                .partSpan(splitSpans.get(0))
                                .instructionLabel(grammar.get(currentRule.getTokens().get(4)).getLabel())
                                .ruleIndex(0)
                                .build());

                        //span that evaluates expression to false
                        queue.add(Branch.builder()
                                .partSpan(splitSpans.get(1))
                                .instructionLabel(currentInstruction.getLabel())
                                .ruleIndex(index + 1)
                                .build());
                    }
                    break;

                } else if (currentRule.getTokens().contains(GREATER)) {
                    PartComp member = PartComp.valueOf(currentRule.getTokens().get(0));
                    PartSpan.Range range = current.getMember(member);

                    int split = Integer.parseInt(currentRule.getTokens().get(2));
                    //x > 2000
                    if (split > range.getEnd()) {
                        // 1. split value is above range = fail, index++
                        queue.add(Branch.builder()
                                .partSpan(current.bisect(split, member).get(0))
                                .instructionLabel(currentInstruction.getLabel())
                                .ruleIndex(index + 1)
                                .build());
                    } else if (split < range.getStart()) {
                        // 2. split value is below range = success , branch to next label & index=0
                        queue.add(Branch.builder()
                                .partSpan(current.bisect(split, member).get(0))
                                .instructionLabel(grammar.get(currentRule.getTokens().get(4)).getLabel())
                                .ruleIndex(0)
                                .build());

                    } else {

                        List<PartSpan> splitSpans = current.bisect(split, member);

                        //Left side of number range (false)
                        queue.add(Branch.builder()
                                .partSpan(splitSpans.get(0))
                                .instructionLabel(currentInstruction.getLabel())
                                .ruleIndex(index + 1)
                                .build());

                        //Right side of number range (true)
                        queue.add(Branch.builder()
                                .partSpan(splitSpans.get(1))
                                .instructionLabel(grammar.get(currentRule.getTokens().get(4)).getLabel())
                                .ruleIndex(0)
                                .build());
                    }
                    break;
                }
            }
        }

        return count;
    }


    public Long part2() {
        List<List<String>> inputs = readFile();
        return processPartSpan(PartSpan.init(), inputToRawInstruction(inputs.get(0)));
    }

    public static void main(String[] args) {
        Aplenty aplenty = new Aplenty();
        System.out.println("result1: " + aplenty.part1());

        System.out.println("result2: " + aplenty.part2());
    }

    @AllArgsConstructor
    @Data
    @Builder
    public static class Branch {
        private final String instructionLabel;
        private final int ruleIndex;
        private final PartSpan partSpan;
    }


    @AllArgsConstructor
    @Data
    @Builder
    public static class PartSpan {
        private final Range x;
        private final Range m;
        private final Range a;
        private final Range s;

        public static PartSpan init() {
            return PartSpan.builder()
                    .x(Range.builder().start(1).end(4000).build())
                    .m(Range.builder().start(1).end(4000).build())
                    .a(Range.builder().start(1).end(4000).build())
                    .s(Range.builder().start(1).end(4000).build())
                    .build();
        }

        public Long product() {
            return x.getDifference() * m.getDifference() * a.getDifference() * s.getDifference();
        }


        public Range getMember(PartComp partComp) {
            return switch (partComp) {
                case x -> this.x;
                case m -> this.m;
                case a -> this.a;
                case s -> this.s;
            };
        }


        public List<PartSpan> bisect(int splitValue, PartComp partComp) {
            List<Range> newRanges;
            switch (partComp) {
                case x -> {
                    newRanges = this.x.split(splitValue);
                    return newRanges.stream().map(range -> PartSpan.builder().x(range).m(this.m).a(this.a).s(this.s).build()).toList();
                }
                case m -> {
                    newRanges = this.m.split(splitValue);
                    return newRanges.stream().map(range -> PartSpan.builder().x(this.x).m(range).a(this.a).s(this.s).build()).toList();
                }
                case a -> {
                    newRanges = this.a.split(splitValue);
                    return newRanges.stream().map(range -> PartSpan.builder().x(this.x).m(this.m).a(range).s(this.s).build()).toList();
                }
                case s -> {
                    newRanges = this.s.split(splitValue);
                    return newRanges.stream().map(range -> PartSpan.builder().x(this.x).m(this.m).a(this.a).s(range).build()).toList();
                }
            }

            throw new IllegalStateException("Not sure what happened here");
        }

        @AllArgsConstructor
        @Data
        @Builder
        static class Range {
            private final int start;
            private final int end;

            public List<Range> split(int splitValue) {
                System.out.println("splitting on : " + splitValue + "; start: "+ start + "; end:" + end);

                if (splitValue < start || splitValue > end) {
                    return List.of(this);
                }

                return List.of(
                        Range.builder().start(this.start).end(splitValue).build(),
                        Range.builder().start(splitValue + 1).end(this.end).build()
                );
            }

            public long getDifference() {
                return end - start + 1;
                //4000 -1 + 1 => should account for all in range 1-4000 inclusively
            }

        }
    }

    public enum PartComp {
        x, m, a, s;
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

        public int getMember(String member) {
            return switch (member) {
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
    @ToString
    public static class Rule {
        // [a, <, 2006, :, qkq]
        @Builder.Default
        private final List<String> tokens = new ArrayList<>();
    }
}
