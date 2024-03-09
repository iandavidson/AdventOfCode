package org.example.advent.year2023.twentyone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Coordinate {
    private final int row;
    private final int col;
}
