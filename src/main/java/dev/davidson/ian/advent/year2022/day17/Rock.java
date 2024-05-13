package dev.davidson.ian.advent.year2022.day17;

import java.util.List;

public record Rock(List<Coordinate> coordinates, ROCKTYPE rockType, int highestY, int leftMostX,
                   int rightMostX) implements Comparable<Rock> {
    private static final int LEFT_OUT = -1;
    private static final int RIGHT_OUT = 7;

    private boolean canApplyJetStream(final DIRECTION direction, final List<Rock> rocks) {
        //is still in bounds after move
        boolean clearFromSides = (direction == DIRECTION.RIGHT && rightMostX + 1 < RIGHT_OUT) || (direction == DIRECTION.LEFT && leftMostX - 1 > LEFT_OUT);
        if (!clearFromSides) {
            return false;
        }

        for (Rock other : rocks) {
            if (!canMove(other, direction == DIRECTION.LEFT ? -1 : 1, 0)) {
                return false;
            }
        }

        return true;
    }

    public Rock applyJetStream(final DIRECTION direction, final List<Rock> rocks) {
        if (canApplyJetStream(direction, rocks)) {
            switch (direction) {
                case LEFT -> {
                    List<Coordinate> moved = coordinates.stream().map(coor -> new Coordinate(coor.x() - 1, coor.y())).toList();
                    return new Rock(moved, rockType, highestY, leftMostX - 1, rightMostX - 1);
                }
                case RIGHT -> {
                    List<Coordinate> moved = coordinates.stream().map(coor -> new Coordinate(coor.x() + 1, coor.y())).toList();
                    return new Rock(moved, rockType, highestY, leftMostX + 1, rightMostX + 1);
                }
            }
        }
        return null;
    }


    public Rock applyMoveDown(final List<Rock> rocks) {
        for (Coordinate coordinate : coordinates) {
            if ((coordinate.y() - 1) == 0) {
                return null;
            }
        }
        for (Rock other : rocks) {
            //potentially optimize here, based on differnce of y, rocks are sorted y high to low
            if (!canMove(other, 0, -1)) {
                return null;
            }
        }

        List<Coordinate> moved = coordinates.stream().map(coor -> new Coordinate(coor.x(), coor.y() - 1)).toList();
        return new Rock(moved, rockType, highestY - 1, leftMostX, rightMostX);
    }

    private boolean canMove(final Rock other, final int deltaX, final int deltaY) {
        for (Coordinate coordinate : coordinates) {
            for (Coordinate otherCoord : other.coordinates) {
                if (coordinate.y() + deltaY == otherCoord.y() && coordinate.x() + deltaX == otherCoord.x()) {
                    return false;
                }
            }
        }

        return true;
    }

    //sort high to low
    @Override
    public int compareTo(Rock o) {
        return Integer.compare(o.highestY, this.highestY);
    }
}
