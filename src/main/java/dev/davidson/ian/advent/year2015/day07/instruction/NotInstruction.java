package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import dev.davidson.ian.advent.year2015.day07.Wire;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NotInstruction extends Instruction implements Operation {
    private final Wire operand;

    public NotInstruction(final Wire operand, final Wire resultLabel) {
        super(resultLabel);
        this.operand = operand;
    }

    @Override
    public Boolean evaluate() {
        if(isEligible()){
            int result = ~operand.get() & 0xffff;
            this.getResult().setValue(result);
            return true;
        }

        return false;
    }

    @Override
    public Boolean isEligible() {
        return operand.getValue() != null;
    }
}
