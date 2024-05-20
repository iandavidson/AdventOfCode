package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AssignmentInstruction extends Instruction implements Operation {

    private final String operandLabel;

    public AssignmentInstruction(final String operandLabel, final String resultLabel){
        super(resultLabel);
        this.operandLabel = operandLabel;
    }

    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        if(labelMap.containsKey(operandLabel)){
            return labelMap.get(operandLabel);
        }

        return null;
    }
}
