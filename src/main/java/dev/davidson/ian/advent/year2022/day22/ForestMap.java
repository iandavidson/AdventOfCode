package dev.davidson.ian.advent.year2022.day22;

public interface ForestMap {
    Coordinate move(final MovementState state);
    Coordinate getStart();
}
