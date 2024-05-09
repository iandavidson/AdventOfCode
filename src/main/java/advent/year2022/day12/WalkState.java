package advent.year2022.day12;

import java.util.Objects;

public record WalkState(Coordinate coordinate, Long steps) implements Comparable<WalkState> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalkState walkState = (WalkState) o;
        return Objects.equals(coordinate, walkState.coordinate) && Objects.equals(steps, walkState.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, steps);
    }

    @Override
    public int compareTo(WalkState o) {
        return (int) (steps - o.steps());
    }

    public Integer row() {
        return coordinate().row();
    }

    public Integer col() {
        return coordinate().col();
    }
}
