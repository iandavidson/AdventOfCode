package org.example.advent.year2023.day23;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class WalkState {
    private List<Coordinate> history;
    private Long steps;
    private Coordinate current;
}
