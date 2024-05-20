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
public class AssignmentInstruction extends Instruction {

    private final Integer assigned;

    public AssignmentInstruction(final Integer assigned, final String resultLabel) {
        super(resultLabel);
        this.assigned = assigned;
    }


    @Override
    public Integer evaluate(Map<String, Integer> labelMap) {
        return assigned;
    }

    @Override
    protected Boolean canEvaluate(Map<String, Integer> labelMap) {
        return true;
    }
}
