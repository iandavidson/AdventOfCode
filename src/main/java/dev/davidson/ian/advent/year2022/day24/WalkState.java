package dev.davidson.ian.advent.year2022.day24;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class WalkState {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalkState walkState = (WalkState) o;
        return Objects.equals(coordinate, walkState.coordinate) && Objects.equals(cycleRemainder, walkState.cycleRemainder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, cycleRemainder);
    }

    private final Coordinate coordinate;
    private final Integer round;
    private final Integer cycleRemainder;
}
