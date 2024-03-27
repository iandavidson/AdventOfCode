package org.example.advent.year2023.twentythree;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class PathSegment {
    private int length;
    private Coordinate start;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathSegment that = (PathSegment) o;
        return length == that.length && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, start, end);
    }

    private Coordinate end;
}
