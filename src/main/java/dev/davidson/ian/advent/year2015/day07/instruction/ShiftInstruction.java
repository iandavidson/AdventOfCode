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
public class ShiftInstruction extends Instruction {

    private final DIRECTION direction;
    private final String operandLabel;
    private final Integer shiftMagnitude;

    public ShiftInstruction(final String direction, final String operandLabel, final Integer shiftMagnitude, final String resultLabel) {
        super(resultLabel);
        this.direction = DIRECTION.valueOf(direction);
        this.operandLabel = operandLabel;
        this.shiftMagnitude = shiftMagnitude;
    }

    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        if (canEvaluate(labelMap)) {
            int tempResult = labelMap.get(operandLabel);
            for (int i = 0; i < shiftMagnitude; i++) {
                tempResult = direction == DIRECTION.LSHIFT ? tempResult << 1 : tempResult >> 1;
            }

            return tempResult;
        }

        return null;
    }

    @Override
    protected Boolean canEvaluate(Map<String, Integer> labelMap) {
        return labelMap.containsKey(operandLabel);
    }

    enum DIRECTION {
        LSHIFT, RSHIFT
    }

}
