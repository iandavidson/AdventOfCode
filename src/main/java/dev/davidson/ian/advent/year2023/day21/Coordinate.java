package dev.davidson.ian.advent.year2023.day21;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Coordinate implements Comparable<Coordinate> {
    private final int row;
    private final int col;


    public Coordinate newNeighbor(int rowDelta, int colDelta){
        return Coordinate.builder().row(this.row + rowDelta).col(this.col + colDelta).build();
    }

    @Override
    public int compareTo(Coordinate o) {
        if (this.row != o.getRow()) {
            return Integer.compare(this.row, o.getRow());
        } else {
            return Integer.compare(this.col, o.getCol());
        }
    }

}
