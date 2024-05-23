package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import dev.davidson.ian.advent.year2015.day07.Wire;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AssignmentInstruction extends Instruction implements Operation {

    private final Wire operand;

    public AssignmentInstruction(final Wire operand, final Wire resultLabel){
        super(resultLabel);
        this.operand = operand;
    }

    @Override
    public Boolean evaluate() {
        if(isEligible()){
            int result = operand.get();
            this.getResult().setValue(result & 0xffff);
            return true;
        }

        return false;
    }

    @Override
    public Boolean isEligible() {
        return operand.getValue() != null;
    }
}
