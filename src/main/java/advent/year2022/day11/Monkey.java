package advent.year2022.day11;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public record Monkey(Queue<Long> items, Operation operation, Integer operationOther, Boolean otherOld,
                     Integer testDivisor, Integer trueIndex, Integer falseIndex) {

    public static Monkey newMonkey(List<String> lines) {

        Queue<Long> items = new LinkedList<>(Arrays.stream(lines.get(1).split(":")[1].split(","))
                .map(String::trim).mapToLong(Long::parseLong).boxed().toList());

        String[] operationChunks = lines.get(2).split("\\s+");
        String other = operationChunks[operationChunks.length - 1]; //other
        Boolean otherOld = other.equals("old");
        Integer operationOther = otherOld ? null : Integer.parseInt(other);
        Operation operation = Operation.OPERATION_MAP.get(operationChunks[operationChunks.length - 2].charAt(0));

        String[] divisorLineChunks = lines.get(3).split("\\s+");
        Integer testDivisor = Integer.parseInt(divisorLineChunks[divisorLineChunks.length - 1]);

        String[] trueIndexChunks = lines.get(4).split("\\s+");
        Integer trueIndex = Integer.parseInt(trueIndexChunks[trueIndexChunks.length - 1]);

        String[] falseIndexChunks = lines.get(5).split("\\s+");
        Integer falseIndex = Integer.parseInt(falseIndexChunks[falseIndexChunks.length - 1]);

        return new Monkey(items, operation, operationOther, otherOld, testDivisor, trueIndex, falseIndex);
    }

    public long applyOperation(Long worryScore) {
        if (operation.equals(Operation.PLUS)) {
            return ((this.otherOld ? worryScore : (long) this.operationOther) + worryScore);
        } else {
            return ((this.otherOld ? worryScore : (long) this.operationOther) * worryScore);
        }
    }


    public Integer throwToNewMonkey(Long worryScore) {
        if (worryScore % this.testDivisor == 0) {
            return this.trueIndex;
        } else {
            return this.falseIndex;
        }
    }

    public Long loseInterest(Long worryScore) {
        return worryScore / 3;
    }
}
