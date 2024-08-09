package dev.davidson.ian.advent.year2022.day24;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Blizzard {
    private final Coordinate currentLocation;
    private final Direction direction;
}
