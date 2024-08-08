package dev.davidson.ian.advent.year2022.day23;

import lombok.Builder;

@Builder
public record Coordinate(int row, int col) implements Comparable<Coordinate> {

    @Override
    public int compareTo(Coordinate o) {
        if(row == o.row){
            return col - o.col;
        }else{
            return row - o.row;
        }
    }
}
