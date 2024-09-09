package dev.davidson.ian.advent.year2022.day22;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CubeMovementState {

    private static final Map<Integer, Map<Direction, Integer>> WRAP_MAP = wrapMap();
    private static final Map<Integer, Map<Direction, Direction>> DELTA_MAP = transformMap();
    private static final Map<Direction, Map<Direction, Integer>> ROTATION_MAP = rotationMap();
    private final Integer cubeFace;
    private final Coordinate coordinate;
    private final Direction direction;

    //direction currently going over edge, direction going on new face
    private final List<int[]> DIRECTION = List.of(new int[]{0, 1}, new int[]{1, 0}, new int[]{0, -1}, new int[]{-1,
            0}); // R, D, L, U

    public CubeMovementState changeDirection(final String turn) {
        int ordinal = direction.ordinal();

        switch (turn) {
            case "L" -> {
                ordinal--;
                if (ordinal == -1) {
                    ordinal = 3;
                }
            }
            case "R" -> ordinal = (ordinal + 1) % 4;
        }

        return new CubeMovementState(
                this.cubeFace,
                this.coordinate,
                Direction.values()[ordinal]);

    }

    public CubeMovementState moveDistance(final int distance, final TILE[][][] cube) {

        /*
        possibilities for attempting to move 1 space

        without wrapping a wall:
        - move full distance without wrapping around side (new coord)
        - hit wall without moving (same everything) -> break
        wrapping a wall:
        - wrap around side, space is open (new face, new direction, new coord)
        - hit wall directly after wrap (same everything) -> break
         */
        int currentCubeFace = this.cubeFace;
        Coordinate current = this.coordinate;
        Direction currentDirection = this.direction;

        for (int i = 0; i < distance; i++) {
            Coordinate toBeNext = new Coordinate(
                    current.row() + DIRECTION.get(currentDirection.ordinal())[0],
                    current.col() + DIRECTION.get(currentDirection.ordinal())[1]
            );


            //proposed move remains on same cube face
            if (toBeNext.row() > -1 && toBeNext.col() > -1 && toBeNext.row() < 50 && toBeNext.col() < 50) {

                //valid move & still within same cube face
                if (cube[this.cubeFace][toBeNext.row()][toBeNext.col()] == TILE.EMPTY) {
                    current = toBeNext;

                    //hit wall, stop and return
                } else {
                    break;
                }
            } else {


                //path wrapped over edge; 2 cases:
                //1. space is open on new side
                //2. space we will arrive at is blocked, and we can't actually turn corner, need to stop before moving

                //todo: get rid of delta map and just have # -> # : rotations
                Direction nextDir = DELTA_MAP.get(currentCubeFace).get(currentDirection);
                int nextCubeFace = WRAP_MAP.get(currentCubeFace).get(currentDirection);
                int rotations = rotationMap().get(currentDirection).get(nextDir);

                //take the "out of bounds" coordinate, and wrap it around a 50x50 square,
                // then using the nextDirection, we can compute how many times to rotate Coordinate instance
                // to then be appropriately placed on the new cube face
                toBeNext = computeWrapCoordinate(toBeNext, rotations);

                if (cube[nextCubeFace][toBeNext.row()][toBeNext.col()] == TILE.EMPTY) {
                    currentCubeFace = nextCubeFace;
                    currentDirection = nextDir;
                    current = toBeNext;
                } else {
                    break;
                }
            }
        }

        return new CubeMovementState(currentCubeFace, current, currentDirection);
    }

    private static Coordinate computeWrapCoordinate(final Coordinate coordinate, final int rotations) {
        int nextRow = (coordinate.row() + 50) % 50;
        int nextCol = (coordinate.col() + 50) % 50;
//        int rotations = ROTATION_MAP.get(direction).get(nextDirection);

        for(int i =0; i < rotations; i++){
            nextRow = (nextRow + 75) % 50;
            nextCol = (nextCol + 75) % 50;
            //apply
            //update nextRow, update nextCol
        }


        return new Coordinate(nextRow, nextCol);
    }

    private static Map<Integer, Map<Direction, Integer>> wrapMap() {
        Map<Integer, Map<Direction, Integer>> map = new HashMap<>();

        //0
        map.put(0, new HashMap<>());
        map.get(0).put(Direction.L, 3);
        map.get(0).put(Direction.R, 1);
        map.get(0).put(Direction.U, 5);
        map.get(0).put(Direction.D, 2);

        //1
        map.put(1, new HashMap<>());
        map.get(1).put(Direction.L, 0);
        map.get(1).put(Direction.R, 4);
        map.get(1).put(Direction.U, 5);
        map.get(1).put(Direction.D, 2);

        //2
        map.put(2, new HashMap<>());
        map.get(2).put(Direction.L, 3);
        map.get(2).put(Direction.R, 1);
        map.get(2).put(Direction.U, 0);
        map.get(2).put(Direction.D, 4);

        //3
        map.put(3, new HashMap<>());
        map.get(3).put(Direction.L, 0);
        map.get(3).put(Direction.R, 4);
        map.get(3).put(Direction.U, 2);
        map.get(3).put(Direction.D, 5);

        //4
        map.put(4, new HashMap<>());
        map.get(4).put(Direction.L, 3);
        map.get(4).put(Direction.R, 1);
        map.get(4).put(Direction.U, 2);
        map.get(4).put(Direction.D, 5);

        //5
        map.put(5, new HashMap<>());
        map.get(5).put(Direction.L, 0);
        map.get(5).put(Direction.R, 4);
        map.get(5).put(Direction.U, 3);
        map.get(5).put(Direction.D, 1);

        return map;
    }

    private static Map<Integer, Map<Direction, Direction>> transformMap() {
        Map<Integer, Map<Direction, Direction>> map = new HashMap<>();

        //0
        map.put(0, new HashMap<>());
        map.get(0).put(Direction.L, Direction.R);
        map.get(0).put(Direction.R, Direction.R);
        map.get(0).put(Direction.U, Direction.R);
        map.get(0).put(Direction.D, Direction.D);

        //1
        map.put(1, new HashMap<>());
        map.get(1).put(Direction.L, Direction.L);
        map.get(1).put(Direction.R, Direction.L);
        map.get(1).put(Direction.U, Direction.U);
        map.get(1).put(Direction.D, Direction.L);

        //2
        map.put(2, new HashMap<>());
        map.get(2).put(Direction.L, Direction.D);
        map.get(2).put(Direction.R, Direction.U);
        map.get(2).put(Direction.U, Direction.U);
        map.get(2).put(Direction.D, Direction.D);

        //3
        map.put(3, new HashMap<>());
        map.get(3).put(Direction.L, Direction.R);
        map.get(3).put(Direction.R, Direction.R);
        map.get(3).put(Direction.U, Direction.R);
        map.get(3).put(Direction.D, Direction.D);

        //4
        map.put(4, new HashMap<>());
        map.get(4).put(Direction.L, Direction.L);
        map.get(4).put(Direction.R, Direction.L);
        map.get(4).put(Direction.U, Direction.U);
        map.get(4).put(Direction.D, Direction.L);

        //5
        map.put(5, new HashMap<>());
        map.get(5).put(Direction.L, Direction.D);
        map.get(5).put(Direction.R, Direction.U);
        map.get(5).put(Direction.U, Direction.U);
        map.get(5).put(Direction.D, Direction.D);

        return map;
    }

    //clockwise rotations
    private static Map<Direction, Map<Direction, Integer>> rotationMap() {
        Map<Direction, Map<Direction, Integer>> map = new HashMap<>();

        map.put(Direction.R,
                Map.of(Direction.R, 0,
                        Direction.D, 1,
                        Direction.L, 2,
                        Direction.U, 3));

        map.put(Direction.D,
                Map.of(Direction.R, 3,
                        Direction.D, 0,
                        Direction.L, 1,
                        Direction.U, 2));

        map.put(Direction.L,
                Map.of(Direction.R, 2,
                        Direction.D, 3,
                        Direction.L, 0,
                        Direction.U, 1));

        map.put(Direction.U,
                Map.of(Direction.R, 1,
                        Direction.D, 2,
                        Direction.L, 3,
                        Direction.U, 0));

        return map;
    }
}
