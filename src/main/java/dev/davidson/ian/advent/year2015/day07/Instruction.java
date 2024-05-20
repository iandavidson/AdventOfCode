package dev.davidson.ian.advent.year2015.day07;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class Instruction {
    private String resultLabel;
}
