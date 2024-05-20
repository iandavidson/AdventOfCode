package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;

import java.util.Map;

public class AndInstruction extends Instruction {

    private final String leftLabel;
    private final String rightLabel;

    public AndInstruction(final String leftLabel, final String rightLabel, final String resultLabel) {
        super(resultLabel);
        this.leftLabel = leftLabel;
        this.rightLabel = rightLabel;
    }

    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        if (canEvaluate(labelMap)) {
            return labelMap.get(leftLabel) & labelMap.get(rightLabel);
        }

        return null;
    }

    @Override
    protected Boolean canEvaluate(Map<String, Integer> labelMap) {
        return labelMap.containsKey(leftLabel) && labelMap.containsKey(rightLabel);
    }
}
