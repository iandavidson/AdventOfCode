package dev.davidson.ian.advent.year2016.day08;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Rectangle implements Instruction {

    private final Integer rows;
    private final Integer cols;

    @Override
    public List<Coordinate> execute(final List<Coordinate> currentBoard) {
        Set<Coordinate> updatedBoard = new HashSet<>(currentBoard);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                updatedBoard.add(new Coordinate(i, j));
            }
        }

        return updatedBoard.stream().toList();
    }
}
