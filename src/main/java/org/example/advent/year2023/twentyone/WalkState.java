package org.example.advent.year2023.twentyone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class WalkState {
    private final Coordinate coordinate;
    private final int stepsRemaining;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalkState walkState = (WalkState) o;
        return stepsRemaining == walkState.stepsRemaining && Objects.equals(coordinate, walkState.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, stepsRemaining);
    }

    public int getRow(){
        return coordinate.getRow();
    }

    public int getCol(){
        return coordinate.getCol();
    }
}
