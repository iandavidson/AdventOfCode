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

    public ShiftInstruction(final String direction, final Wire operandLabel, final Integer shiftMagnitude, final Wire resultLabel) {
        super(resultLabel);
        this.direction = DIRECTION.valueOf(direction);
        this.operandLabel = operandLabel;
        this.shiftMagnitude = shiftMagnitude;
    }

    @Override
    public Boolean evaluate(Map<String, Integer> labelMap) {
        if (isEligible()) {
            int tempResult = operandLabel.get();
            for (int i = 0; i < shiftMagnitude; i++) {
                tempResult = direction == DIRECTION.LSHIFT ? tempResult << 1 : tempResult >> 1;
            }
            int result = tempResult & 0xffff;
            this.getResult().setValue(result);
            labelMap.putIfAbsent(this.getResult().getLabel(), result);
            return true;
        }

        return false;
    }

    @Override
    public String getResultLabel() {
        return this.getResult().getLabel();
    }

    @Override
    public Boolean isEligible() {
        return operandLabel.isEligible();
    }

    enum DIRECTION {
        LSHIFT, RSHIFT
    }

}
