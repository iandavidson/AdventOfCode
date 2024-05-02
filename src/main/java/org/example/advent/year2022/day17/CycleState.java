package org.example.advent.year2022.day17;

import java.util.Objects;

public record CycleState(int jetDiff, int rockNumber) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CycleState that = (CycleState) o;
        return jetDiff == that.jetDiff;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jetDiff);
    }
}
