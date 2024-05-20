package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import dev.davidson.ian.advent.year2015.day07.Wire;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AndInstruction extends Instruction implements Operation {

    private final Wire left;
    private final Wire right;

    public AndInstruction(final Wire left, final Wire right, final String resultLabel) {
        super(resultLabel);
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        if (isEligible(labelMap)) {
            return labelMap.get(left) & labelMap.get(right);
        }

        return null;
    }

    @Override
    public Boolean isEligible(Map<String, Integer> labelMap) {
        return left.isEligible(labelMap) && right.isEligible(labelMap);
    }
}
