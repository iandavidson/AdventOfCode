package dev.davidson.ian.advent.year2022.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Basin {
    private final Coordinate start;
    private final Coordinate finish;
    private final Integer rows;
    private final Integer cols;
    private final Integer blizzardPeriod;
    private List<Blizzard> blizzards;

    public Set<String> buildOccupiedTileSet() {
        return blizzards.stream().map(blizz -> blizz.getCurrentLocation().toId()).collect(Collectors.toSet());
    }

    public void updateBlizzards() {
        List<Blizzard> nextBlizzards = new ArrayList<>();

        for (Blizzard blizzard : blizzards) {
            nextBlizzards.add(updateBlizzard(blizzard));
        }

        this.setBlizzards(nextBlizzards);
    }

    private Blizzard updateBlizzard(final Blizzard blizzard) {

        int[] shift = Direction.DIRECTION_MAP.get(blizzard.getDirection());
        int newRow = blizzard.getCurrentLocation().row() + shift[0];
        int newCol = blizzard.getCurrentLocation().col() + shift[1];

        switch (blizzard.getDirection()) {
            case UP -> {
                if (newRow == 0) {
                    newRow = rows - 2;
                }
            }
            case DOWN -> {
                if (newRow == rows - 1) {
                    newRow = 1;
                }
            }
            case RIGHT -> {
                if (newCol == cols - 1) {
                    newCol = 1;
                }
            }
            case LEFT -> {
                if (newCol == 0) {
                    newCol = cols - 2;
                }
            }
        }

        return Blizzard.builder()
                .currentLocation(new Coordinate(newRow, newCol))
                .direction(blizzard.getDirection())
                .build();
    }
}
