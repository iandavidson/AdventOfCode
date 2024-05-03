package org.example.advent.year2022.day19;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public record Blueprint(int id, Cost oreRobot, Cost clayRobot, Cost obsidianRobot, Cost geodeRobot) {
    @Getter
    enum RESOURCE {
        //Blueprint 1: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 15 clay. Each geode robot costs 2 ore and 20 obsidian.
        ORE("ore"), CLAY("clay"), OBSIDIAN("obsidian");

        private final String wordName;

        RESOURCE(String wordName) {
            this.wordName = wordName;
        }
    }

    public static Blueprint newBlueprint(final String line) {
        int id = Integer.parseInt(line.substring(10, line.indexOf(":")));

        String robots = line.split(":")[1].trim();

        String[] robotChunks = robots.split("\\.", 4);
        List<Cost> costs = new ArrayList<>();
        for (String robotChunk : robotChunks) {
            String[] parts = robotChunk.substring(robotChunk.indexOf("costs") + 5).trim().split("\\s+");
            if (parts.length == 2) {

            } else {

            }

            //"4 ore" OR "3 ore and 14 clay"
        }

        return new Blueprint(id, null, null, null, null);
    }
//Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
}
