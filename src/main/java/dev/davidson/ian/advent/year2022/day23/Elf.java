package dev.davidson.ian.advent.year2022.day23;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Elf {
    private final int ordinal;
    private final Coordinate coordinate;

    public Integer row(){
        return coordinate.row();
    }

    public Integer col(){
        return coordinate.col();
    }
}
