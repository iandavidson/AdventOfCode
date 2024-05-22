package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import dev.davidson.ian.advent.year2015.day07.Wire;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShiftInstruction extends Instruction implements Operation {

    private final DIRECTION direction;
    private final Wire operand;
    private final Integer shiftMagnitude;

    public ShiftInstruction(final String direction, final Wire operandLabel, final Integer shiftMagnitude, final Wire resultLabel) {
        super(resultLabel);
        this.direction = DIRECTION.valueOf(direction);
        this.operand = operandLabel;
        this.shiftMagnitude = shiftMagnitude;
    }

    @Override
    public Boolean evaluate() {
        if (isEligible()) {
            int tempResult = operand.get();
            for (int i = 0; i < shiftMagnitude; i++) {
                tempResult = direction == DIRECTION.LSHIFT ? tempResult << 1 : tempResult >> 1;
            }
            int result = tempResult & 0xffff;
            this.getResult().setValue(result);
            return true;
        }

        return false;
    }

    @Override
    public Boolean isEligible() {
        return operand.getValue() != null;
    }

    enum DIRECTION {
        LSHIFT, RSHIFT
    }

}
