package dev.davidson.ian.advent.year2022.day22;

import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MovementState {
    private final List<int[]> DIRECTION = List.of(new int[]{0, 1}, new int[]{1, 0}, new int[]{0, -1}, new int[]{-1,
            0}); // R, D, L, U

    //0,1 -> 1,0 -> 0,-1, -1,0
    private int directionIndex = 0;
    private Coordinate coordinate;

    public MovementState(final Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int[] getShift() {
        return DIRECTION.get(this.directionIndex);
    }

    public void changeDirection(final String newDirection) {
        switch (newDirection) {
            case "L" -> directionIndex = (directionIndex -1 + 4) % 4;
            case "R" -> directionIndex = (directionIndex +1) % 4;
            case null, default -> {
                log.info("got a problem, new direction: {}", newDirection);
                throw new RuntimeException();
            }
        }
    }
}
