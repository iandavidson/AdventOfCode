package org.example.advent.year2022.day17;

import java.util.List;

public record Rock(List<Coordinate> occupied, ROCKTYPE rockType, int highestY, int leftMostX,
                   int rightMostX) implements Comparable<Rock> {
    private static final int LEFT_OUT = -1;
    private static final int RIGHT_OUT = 7;

    private boolean canApplyJetStream(final DIRECTION direction) {
        //is still in bounds after move
        return (direction == DIRECTION.RIGHT && rightMostX + 1 < RIGHT_OUT) || (direction == DIRECTION.LEFT && leftMostX - 1 > LEFT_OUT);
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
        return null;
    }


    public Rock applyMoveDown(final List<Rock> rocks) {
        for (Coordinate coordinate : occupied) {
            if (coordinate.y() == 0) {
                return null;
            }
        }
        for (Rock other : rocks) {
            //potentially optimize here, based on differnce of y, rocks are sorted y high to low
            if (!canMoveDown(other)) {
                return null;
            }
        }

        List<Coordinate> moved = occupied.stream().map(coor -> new Coordinate(coor.x(), coor.y() - 1)).toList();
        return new Rock(moved, rockType, highestY - 1, leftMostX, rightMostX);
    }

    private boolean canMoveDown(Rock other) {
        for (Coordinate coordinate : occupied) {
            for (Coordinate otherCoord : other.occupied) {
                if (coordinate.y() - 1 == otherCoord.y() && coordinate.x() == otherCoord.x()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int compareTo(Rock o) {
        return Integer.compare(o.highestY, this.highestY);
    }
}
