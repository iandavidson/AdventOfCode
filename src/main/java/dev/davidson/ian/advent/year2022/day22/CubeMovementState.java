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

    private final Integer cubeFace;
    private final Coordinate coordinate;
    private final Direction direction;

    private static final Map<Integer, Map<Direction, Integer>> WRAP_MAP = wrapMap();
    private static final Map<Integer, Map<Direction, Direction>> DELTA_MAP = transformMap();
    private final List<int []> DIRECTION = List.of(new int[]{0,1}, new int[]{1,0}, new int[]{0,-1}, new int[]{-1,0}); // R, D, L, U

    public CubeMovementState changeDirection(final String turn){
        int ordinal = direction.ordinal();

        switch(turn){
            case "L" -> {
                ordinal--;
                if(ordinal == -1){
                    ordinal = 3;
                }
            }
            case "R" -> ordinal = (ordinal +  1) % 4;
        }

        return new CubeMovementState(
                this.cubeFace,
                this.coordinate,
                Direction.values()[ordinal]);

    }

    public CubeMovementState moveDistance(final int distance, final TILE[][][] cube){

        /*
        options:

        - move full distance without wrapping around side
        - move some of the distance and hit wall without wrapping
        - move, wrap around side, keep moving
         */
        int nextCubeFace = this.cubeFace;
        Coordinate next = this.coordinate;
        Direction nextDirection = this.direction;


        int remaining = distance;
        for(int i = 0; i < distance; i++){
            Coordinate toBeNext = new Coordinate(
                    next.row() + DIRECTION.get(nextDirection.ordinal())[0],
                    next.col() + DIRECTION.get(nextDirection.ordinal())[1]
            );

            if(cube[this.cubeFace][toBeNext.row()][toBeNext.col()] == TILE.BLOCKED){
                //hit blocked wall, stop and return
                break;

            }else if(toBeNext.row() > -1 && toBeNext.col() > -1 && toBeNext.row() < 50 && toBeNext.col() < 50){
                //valid move & still within same cube face
                next = toBeNext;
            }else {
                //path wrapped over edge; 2 cases:
                //1. space is open on new side
                //2. space we will arrive at is blocked, and we can't actually turn corner, need to stop before moving


            }
        }

        return new CubeMovementState(nextCubeFace, next, nextDirection);
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
}
