package dev.davidson.ian.advent.year2022.day22;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Grid {
    private List<List<TILE>> grid;
    private final int maxWidth;

    public Grid(final List<List<TILE>> initial, final int maxWidth) {
        List<List<TILE>> toBe = new ArrayList<>();
        for (int i = 0; i < initial.size(); i++) {
            List<TILE> tempList = new ArrayList<>();

            for (int j = 0; j < maxWidth; j++) {

                if (j >= initial.get(i).size()) {
                    tempList.add(TILE.OUT);
                    //add TILE.OUT
                } else {
                    tempList.add(initial.get(i).get(j));
                }
            }
            toBe.add(tempList);
        }

        this.grid = toBe;
        this.maxWidth = maxWidth;
    }

    public Coordinate move(final State state) {
        int nextRow = state.getCoordinate().row() + state.getShift()[0];
        int nextCol = state.getCoordinate().col() + state.getShift()[1];

        if(nextRow < 0 || nextRow >= grid.size() || nextCol < 0 || nextCol >= maxWidth){
            //wrap around
            return wrapEdge(state);
        }else if(grid.get(nextRow).get(nextCol).equals(TILE.BLOCKED)){
            //do nothing
            return state.getCoordinate();
        }else if(grid.get(nextRow).get(nextCol).equals(TILE.EMPTY)){
            //safe to move
            return new Coordinate(nextRow, nextCol);
        }else{
            return wrapEdge(state);
            //next coordinate is out of bounds
        }
    }

    private Coordinate wrapEdge(final State state){
        switch(state.getDirectionIndex()){
            case 0 ->{
                //right
                //find leftmost column at current row
                for(int i = 0; i < grid.get(state.getCoordinate().row()).size(); i++){
                    if(grid.get(state.getCoordinate().row()).get(i).equals(TILE.EMPTY)){
                        return new Coordinate(state.getCoordinate().row(), i);
                    }
                }
            }
            case 1 ->{
                //down
                //find topmost row at current col
                for(int i = 0; i < grid.size(); i++){
                    if(grid.get(i).get(state.getCoordinate().col()).equals(TILE.EMPTY)){
                        return new Coordinate(i, state.getCoordinate().col());
                    }
                }
            }
            case 2 -> {
                //left
                for(int i = maxWidth-1; i > -1; i--){
                    if(grid.get(state.getCoordinate().row()).get(i).equals(TILE.EMPTY)){
                        return new Coordinate(state.getCoordinate().row(), i);
                    }
                }
            }
            case 3 -> {
                //up
                for(int i = grid.size()-1; i > -1; i--){
                    if(grid.get(i).get(state.getCoordinate().col()).equals(TILE.EMPTY)){
                        return new Coordinate(i, state.getCoordinate().col());
                    }
                }
            }
        }

        throw new IllegalStateException();
    }

}
