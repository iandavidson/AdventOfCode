package org.example.advent.year2022.day11;

import java.util.Arrays;
import java.util.List;

public record Monkey(List<Integer> startingItems, Operation operation, Integer operationOther, Boolean otherOld,
                     Integer testDivisor, Integer trueIndex, Integer falseIndex) {

    public static Monkey newMonkey(List<String> lines) {

        List<Integer> startingItems = Arrays.stream(lines.get(1).split(":")[1].split(","))
                .map(String::trim).mapToInt(Integer::parseInt).boxed().toList();

        String [] operationChunks = lines.get(2).split("\\s+");
        String other = operationChunks[operationChunks.length-1]; //other
        Boolean otherOld = other.equals("old");
        Integer operationOther = otherOld ? null : Integer.parseInt(other);
        Operation operation =  Operation.OPERATION_MAP.get(operationChunks[operationChunks.length-2].charAt(0));

        String [] divisorLineChunks = lines.get(3).split("\\s+");
        Integer testDivisor = Integer.parseInt(divisorLineChunks[divisorLineChunks.length - 1]);

        String [] trueIndexChunks = lines.get(4).split("\\s+");
        Integer trueIndex = Integer.parseInt(trueIndexChunks[trueIndexChunks.length-1]);

        String [] falseIndexChunks = lines.get(5).split("\\s+");
        Integer falseIndex = Integer.parseInt(falseIndexChunks[falseIndexChunks.length-1]);

        return new Monkey(startingItems, operation, operationOther, otherOld, testDivisor, trueIndex, falseIndex);
    }
}

/*
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

 */
