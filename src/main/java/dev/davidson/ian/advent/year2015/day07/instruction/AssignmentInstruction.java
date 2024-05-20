package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import dev.davidson.ian.advent.year2015.day07.Wire;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AssignmentInstruction extends Instruction implements Operation {

    private final Wire operand;

    public AssignmentInstruction(final Wire operand, final String resultLabel){
        super(resultLabel);
        this.operand = operand;
    }

    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        if(labelMap.containsKey(operand)){
            return labelMap.get(operand);
        }

        return null;
    }

    @Override
    public Boolean isEligible(Map<String, Integer> labelMap) {
        return operand.isEligible(labelMap);
    }
}
