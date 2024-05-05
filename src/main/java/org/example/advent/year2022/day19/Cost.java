package org.example.advent.year2022.day19;

import java.util.Map;

@Deprecated
public record Cost(int oreCount, int clayCount, int obsidianCount) {

    public static Cost mapToInstance(final Map<Resource, Integer> costMap){
        int ore = costMap.getOrDefault(Resource.ORE, 0);
        int clay = costMap.getOrDefault(Resource.CLAY, 0);
        int obsidian = costMap.getOrDefault(Resource.OBSIDIAN, 0);
        return new Cost(ore, clay, obsidian);
    }
}
