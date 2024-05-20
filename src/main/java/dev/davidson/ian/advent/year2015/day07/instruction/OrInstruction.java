package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@AllArgsConstructor
public class OrInstruction extends Instruction {
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
        if (canEvaluate(labelMap)) {
            return labelMap.get(leftLabel) | labelMap.get(rightLabel);
        }

        return null;
    }

    @Override
    protected Boolean canEvaluate(Map<String, Integer> labelMap) {
        return labelMap.containsKey(leftLabel) && labelMap.containsKey(rightLabel);
    }
}
