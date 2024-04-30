package org.example.advent.year2022.day17;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Rock implements Comparable<Rock> {
    private final List<Coordinate> occupied;
    private final ROCKTYPE rockType;
    private final int highestY;
    private final int leftMostX;
    private final int rightMostX;

    private static final int LEFT_OUT = -1;
    private static final int RIGHT_OUT = 7;

    private boolean canApplyJetStream(final DIRECTION direction) {
        return (direction == DIRECTION.RIGHT && rightMostX + 1 < RIGHT_OUT) || (direction == DIRECTION.LEFT && leftMostX - 1 <= LEFT_OUT);
    }

    public Rock applyJetStream(final DIRECTION direction) {
        if (canApplyJetStream(direction)) {
            switch (direction) {
                case LEFT -> {
                    List<Coordinate> moved = occupied.stream().map(coor -> new Coordinate(coor.x() - 1, coor.y())).toList();
                    return new Rock(moved, rockType, highestY, leftMostX - 1, rightMostX - 1);
                }
                case RIGHT -> {
                    List<Coordinate> moved = occupied.stream().map(coor -> new Coordinate(coor.x() + 1, coor.y())).toList();
                    return new Rock(moved, rockType, highestY, leftMostX + 1, rightMostX + 1);
                }
            }
        }
        throw new IllegalStateException("Shouldn't be here");
    }


    @Override
    public int compareTo(Rock o) {
        return Integer.compare(highestY, o.highestY);
    }
}
