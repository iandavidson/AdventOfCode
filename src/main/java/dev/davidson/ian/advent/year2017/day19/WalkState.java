package dev.davidson.ian.advent.year2017.day19;

import java.util.List;
import java.util.Set;
import static dev.davidson.ian.advent.year2017.day19.Direction.SHIFTS;

public record WalkState(Coordinate coordinate, Direction direction) {

    public static WalkState nextStep(final WalkState walkState, final char[][] grid, Set<Character> paths,
                                     final Set<Character> letters) {
        char ch = grid[walkState.r()][walkState.c()];
        Direction nextDir = walkState.direction;
        Coordinate nextCoord = walkState.coordinate;


        if (ch == '+') {

            int ord = walkState.direction.ordinal();
            List<Direction> potentialDirections = List.of(Direction.values()[(ord + 1) % 4],
                    Direction.values()[(ord + 3) % 4]);

            for (Direction direction : potentialDirections) {
                Coordinate temp = new Coordinate(walkState.r() + SHIFTS.get(direction)[0],
                        walkState.c() + SHIFTS.get(direction)[1]);

                if (isInBounds(temp, grid.length, grid[0].length) && (paths.contains(grid[temp.r()][temp.c()]) || letters.contains(grid[temp.r()][temp.c()]))) {
                    nextDir = direction;
                    nextCoord = temp;
                    break;
                }
            }

        } else {
            nextCoord = new Coordinate(walkState.r() + SHIFTS.get(walkState.direction)[0],
                    walkState.c() + SHIFTS.get(walkState.direction)[1]);

        }

        return new WalkState(nextCoord, nextDir);
    }

    private static boolean isInBounds(final Coordinate coordinate, final int rMax, final int cMax) {
        return coordinate.r() > -1 && coordinate.c() > -1 && coordinate.r() < rMax && coordinate.c() < cMax;
    }

    public int r() {
        return coordinate.r();
    }

    public int c() {
        return coordinate.c();
    }
}
