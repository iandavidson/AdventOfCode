package org.example.advent.year2022.day19;

import lombok.Getter;

import java.util.Map;

@Getter
public enum Resource {
    //Blueprint 1: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 15 clay. Each geode robot costs 2 ore and 20 obsidian.
    ORE, CLAY, OBSIDIAN;


    public static final Map<String, Resource> RESOURCE_MAP = Map.of("ore", ORE, "clay", CLAY, "obsidian", OBSIDIAN);


    //    ore, clay, obsidian;
    //
}
