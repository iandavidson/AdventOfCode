package dev.davidson.ian.advent.year2015.day07;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class Instruction {

    //how do I get this to be final
    private String resultLabel;

    protected abstract Integer evaluate(final Map<String, Integer> labelMap);
    protected abstract Boolean canEvaluate(final Map<String, Integer> labelMap);
}
