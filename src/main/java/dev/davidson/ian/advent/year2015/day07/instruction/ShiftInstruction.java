package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import dev.davidson.ian.advent.year2015.day07.Wire;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShiftInstruction extends Instruction implements Operation {

    private final DIRECTION direction;
    private final Wire operandLabel;
    private final Integer shiftMagnitude;

    public ShiftInstruction(final String direction, final Wire operandLabel, final Integer shiftMagnitude, final String resultLabel) {
        super(resultLabel);
        this.direction = DIRECTION.valueOf(direction);
        this.operandLabel = operandLabel;
        this.shiftMagnitude = shiftMagnitude;
    }

    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        if (isEligible(labelMap)) {
            int tempResult = operandLabel.get(labelMap);
            for (int i = 0; i < shiftMagnitude; i++) {
                tempResult = direction == DIRECTION.LSHIFT ? tempResult << 1 : tempResult >> 1;
            }

            return tempResult & 0xffff;
        }

        return null;
    }

    @Override
    public Boolean isEligible(Map<String, Integer> labelMap) {
        return operandLabel.isEligible(labelMap);
    }

    enum DIRECTION {
        LSHIFT, RSHIFT
    }

}
