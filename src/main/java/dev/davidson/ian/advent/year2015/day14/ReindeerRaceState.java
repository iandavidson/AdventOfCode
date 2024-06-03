package dev.davidson.ian.advent.year2015.day14;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ReindeerRaceState {
    private final Reindeer reindeer;
    private boolean currentlyResting;
    private int scoreCount;
    private int distanceCount;

    public static ReindeerRaceState toReindeerRaceState(final Reindeer reindeer){
        return ReindeerRaceState.builder()
                .reindeer(reindeer)
                .currentlyResting(false)
                .scoreCount(0)
                .distanceCount(0)
                .build();
    }
}
