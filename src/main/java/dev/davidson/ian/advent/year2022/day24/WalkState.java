package dev.davidson.ian.advent.year2022.day24;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class WalkState {
    private final Coordinate coordinate;
    private final Integer round;
    private final Integer cycleRemainder;
}
