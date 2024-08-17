package dev.davidson.ian.advent.year2016.day08;

import java.util.List;

public interface Instruction {
    List<Coordinate> execute(List<Coordinate> currentBoard);
}
