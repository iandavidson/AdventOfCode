package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrInstruction extends Instruction implements Operation {
    //          x OR y -> e
    private final String leftLabel;
    private final String rightLabel;

    public OrInstruction(final String leftLabel, final String rightLabel, final String resultLabel) {
        super(resultLabel);
        this.leftLabel = leftLabel;
        this.rightLabel = rightLabel;
    }

    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        if (labelMap.containsKey(leftLabel) && labelMap.containsKey(rightLabel)) {
            return labelMap.get(leftLabel) | labelMap.get(rightLabel);
        }

        return null;
    }
}
