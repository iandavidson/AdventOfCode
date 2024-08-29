package dev.davidson.ian.advent.year2016.day08;

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
