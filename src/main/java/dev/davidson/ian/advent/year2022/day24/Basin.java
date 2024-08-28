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
            nextBlizzards.add(blizzard.updateBlizzard(rows, cols));
        }

        this.blizzards = nextBlizzards;
    }

}
