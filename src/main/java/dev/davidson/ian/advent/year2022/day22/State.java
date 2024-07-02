package dev.davidson.ian.advent.year2022.day22;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
public class State {
    private final List<int []> DIRECTION = List.of(new int[]{0,1}, new int[]{1,0}, new int[]{0,-1}, new int[]{-1,0}); // R, D, L, U

    //0,1 -> 1,0 -> 0,-1, -1,0
    private int directionIndex = 0;
    private Coordinate coordinate;

    public int [] getShift(){
        return DIRECTION.get(this.directionIndex);
    }

    public State(final Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public void changeDirection(final String newDirection){
        switch(newDirection){
            case "L" ->{
                directionIndex = directionIndex == 0 ? 3 : directionIndex-1;
            }
            case "R" -> {
                directionIndex = directionIndex == 3 ? 0 : directionIndex+1;
            }
            case null, default -> {
                log.info("got a problem, new direction: {}", newDirection);
                throw new RuntimeException();
            }
        }
    }


//    public State(final State prior, String)
}
