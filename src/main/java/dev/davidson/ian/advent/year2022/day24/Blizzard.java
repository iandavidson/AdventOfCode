package dev.davidson.ian.advent.year2022.day24;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Blizzard {
    private final Coordinate currentLocation;
    private final Direction direction;


    public Blizzard updateBlizzard(final int totalRows, final int totalCols) {

        int[] shift = Direction.DIRECTION_MAP.get(direction);
        int newRow = currentLocation.row() + shift[0];
        int newCol = currentLocation.col() + shift[1];

        switch (direction) {
            case UP -> {
                if (newRow == 0) {
                    newRow = totalRows - 2;
                }
            }
            case DOWN -> {
                if (newRow == totalRows - 1) {
                    newRow = 1;
                }
            }
            case RIGHT -> {
                if (newCol == totalCols - 1) {
                    newCol = 1;
                }
            }
            case LEFT -> {
                if (newCol == 0) {
                    newCol = totalCols - 2;
                }
            }
        }

        return Blizzard.builder()
                .currentLocation(new Coordinate(newRow, newCol))
                .direction(direction)
                .build();
    }
}
