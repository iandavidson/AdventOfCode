package dev.davidson.ian.advent.year2022.day22;

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

    public CubeMovementState changeDirection(String turn){
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


}
