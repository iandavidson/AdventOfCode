package dev.davidson.ian.advent.year2022.day24;

import java.util.Objects;
import lombok.Builder;

@Builder
public record WalkState(Coordinate coordinate, Integer round, Integer cycleRemainder) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalkState walkState = (WalkState) o;
        return Objects.equals(coordinate, walkState.coordinate) && Objects.equals(cycleRemainder,
                walkState.cycleRemainder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, cycleRemainder);
    }

}
