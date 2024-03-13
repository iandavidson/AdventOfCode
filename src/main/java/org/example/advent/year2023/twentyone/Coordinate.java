package org.example.advent.year2023.twentyone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Coordinate implements Comparable<Coordinate> {
    private final int row;
    private final int col;

    @Override
    public int compareTo(Coordinate o) {
        if (this.row != o.getRow()) {
            return Integer.compare(this.row, o.getRow());
        } else {
            return Integer.compare(this.col, o.getCol());
        }
    }

}
