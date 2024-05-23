package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import dev.davidson.ian.advent.year2015.day07.Wire;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrInstruction extends Instruction implements Operation {
    // x OR y -> e
    private final Wire left;
    private final Wire right;

    public OrInstruction(final Wire left, final Wire right, final Wire resultLabel) {
        super(resultLabel);
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean evaluate() {
        if (isEligible()) {
            int result = left.get() | right.get();
            this.getResult().setValue(result & 0xffff);
            return true;
        }

        return false;
    }

    @Override
    public Boolean isEligible() {
        return left.getValue() != null && right.getValue() != null;
    }
}
