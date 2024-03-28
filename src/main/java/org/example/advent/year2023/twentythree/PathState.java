package org.example.advent.year2023.twentythree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PathState {
    private Coordinate coordinate;
    private Long steps;

    public int getRow() {
        return coordinate.getRow();
    }

    public int getCol() {
        return coordinate.getCol();
    }
}
