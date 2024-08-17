package dev.davidson.ian.advent.year2016.day08;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Shift implements Instruction {

    private final Integer magnitude;
    private final Integer index;
    private final Axis axis;
    private static final Integer ROW_MAX = 6;
    private static final Integer COL_MAX = 50;

    @Override
    public List<Coordinate> execute(List<Coordinate> currentBoard) {

        Set<Coordinate> nextBoard = new HashSet<>();

        switch(axis){
            case X -> {
                for(Coordinate coor : currentBoard){
                    if(coor.col() == index){
                        nextBoard.add(new Coordinate((coor.row() + magnitude) % ROW_MAX, coor.col()));
                    }else{
                        nextBoard.add(coor);
                    }
                }
            }
            case Y -> {
                for(Coordinate coor : currentBoard){
                    if(coor.row() == index){
                        nextBoard.add(new Coordinate(coor.row(), (coor.col() + magnitude) % COL_MAX));
                    }else{
                        nextBoard.add(coor);
                    }
                }
            }
        }

        return nextBoard.stream().toList();
    }
}
