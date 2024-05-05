package org.example.advent.year2022.day19;

import lombok.Builder;

import java.util.Objects;

@Builder
public record HarvestState(int ore, int clay, int obsidian, int geodes, int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots, int minutesLeft) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarvestState that = (HarvestState) o;
        return ore == that.ore && clay == that.clay && geodes == that.geodes && obsidian == that.obsidian && oreRobots == that.oreRobots && clayRobots == that.clayRobots && geodeRobots == that.geodeRobots && minutesLeft == that.minutesLeft && obsidianRobots == that.obsidianRobots;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ore, clay, obsidian, geodes, oreRobots, clayRobots, obsidianRobots, geodeRobots, minutesLeft);
    }
}
