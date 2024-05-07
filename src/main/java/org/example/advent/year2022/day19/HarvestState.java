package org.example.advent.year2022.day19;

import lombok.Builder;

@Builder
public record HarvestState(int ore, int clay, int obsidian, int geodes, int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots, int minutesLeft, RobotType robotType) {
}
