package dev.davidson.ian.advent.year2015.day07;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class Instruction {
    private Wire result;
}
