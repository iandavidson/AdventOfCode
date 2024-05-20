package dev.davidson.ian.advent.year2015.day07.instruction;

import dev.davidson.ian.advent.year2015.day07.Instruction;
import dev.davidson.ian.advent.year2015.day07.Operation;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StaticAssignmentInstruction extends Instruction implements Operation {

    private final Integer assigned;

    public StaticAssignmentInstruction(final Integer assigned, final String resultLabel) {
        super(resultLabel);
        this.assigned = assigned;
    }


    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        return assigned;
    }
}
