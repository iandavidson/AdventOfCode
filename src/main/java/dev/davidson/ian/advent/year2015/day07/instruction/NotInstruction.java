package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;

import java.util.Map;

public class NotInstruction extends Instruction {
    private final String operandLabel;

    public NotInstruction(final String operandLabel, final String resultLabel) {
        super(resultLabel);
        this.operandLabel = operandLabel;
    }

    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        if(canEvaluate(labelMap)){
            return ~labelMap.get(operandLabel);
        }

        return null;
    }

    @Override
    protected Boolean canEvaluate(Map<String, Integer> labelMap) {
        return labelMap.containsKey(operandLabel);
    }
}
