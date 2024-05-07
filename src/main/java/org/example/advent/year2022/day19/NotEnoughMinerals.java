package org.example.advent.year2022.day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class NotEnoughMinerals {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day19/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day19/input.txt";

    public static void main(String[] args) {
        NotEnoughMinerals notEnoughMinerals = new NotEnoughMinerals();
        System.out.println("Part1 : " + notEnoughMinerals.part1());
    }

    public long part1() {
        List<Blueprint> blueprints = readFile();
        Map<HarvestState, Integer> cache = new HashMap<>();
        long count = 0L;

        for (int i = 0; i < blueprints.size(); i++) {
            long max = 0;
            for (RobotType rt : RobotType.values()) {
                max = Math.max(max, (long) blueprints.get(i).id() * process(blueprints.get(i), HarvestState.builder().oreRobots(1).minutesLeft(24).robotType(rt).build(), cache));
            }
            count += max;
        }
        return count;
        //1887 too low.
        //2343 too high.
    }

    private List<Blueprint> readFile() {
        List<Blueprint> blueprints = new ArrayList<>();

        ClassLoader cl = NotEnoughMinerals.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                blueprints.add(Blueprint.newBlueprint(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return blueprints;
    }

    private int process(final Blueprint blueprint, final HarvestState state, final Map<HarvestState, Integer> cache) {
        if (state.minutesLeft() == 0) {
            return state.geodes();
        }

        final int maxOre = Math.max(blueprint.oreRobotCost(), Math.max(blueprint.clayRobotOreCost(), Math.max(blueprint.obsidianRobotOreCost(), blueprint.geodeRobotOreCost())));
        if (state.robotType() == RobotType.ORE && state.ore() >= maxOre
                || state.robotType()== RobotType.CLAY && state.clay() >= blueprint.obsidianRobotClayCost()
                || state.robotType() == RobotType.OBSIDIAN && (state.obsidian() >= blueprint.geodeRobotObsidianCost() || state.clay() == 0)
                || state.robotType() == RobotType.GEODE && state.obsidian() == 0) {

            return 0;
        }

        if (cache.containsKey(state)) {
            return cache.get(state);
        }

        int max = 0;
        HarvestState current = state;

        while (current.minutesLeft() > 0) {
            if (state.robotType().equals(RobotType.ORE) && current.ore() >= blueprint.oreRobotCost()) {
                int tempMax = 0;
                for (RobotType rt : RobotType.values()) {
                    tempMax = Math.max(tempMax, process(blueprint, new HarvestState(
                            current.ore() - blueprint.oreRobotCost() + current.oreRobots(),
                            current.clay() + current.clayRobots(),
                            current.obsidian() + current.obsidianRobots(),
                            current.geodes() + current.geodeRobots(),
                            current.oreRobots() + 1,
                            current.clayRobots(),
                            current.obsidianRobots(),
                            current.geodeRobots(),
                            current.minutesLeft() - 1,
                            rt
                    ), cache));
                }
                max = Math.max(max, tempMax);
                cache.put(current, max);
                return max;

            } else if (state.robotType().equals(RobotType.CLAY) && current.ore() >= blueprint.clayRobotOreCost()) {
                int tempMax = 0;
                for (RobotType rt : RobotType.values()) {
                    tempMax = Math.max(tempMax, process(blueprint, new HarvestState(
                            current.ore() - blueprint.clayRobotOreCost() + current.oreRobots(),
                            current.clay() + current.clayRobots(),
                            current.obsidian() + current.obsidianRobots(),
                            current.geodes() + current.geodeRobots(),
                            current.oreRobots(),
                            current.clayRobots() + 1,
                            current.obsidianRobots(),
                            current.geodeRobots(),
                            current.minutesLeft() - 1,
                            rt
                    ), cache));
                }
                max = Math.max(max, tempMax);
                cache.put(current, max);
                return max;

            } else if (state.robotType().equals(RobotType.OBSIDIAN) && current.ore() >= blueprint.obsidianRobotOreCost() && current.clay() >= blueprint.obsidianRobotClayCost()) {
                int tempMax = 0;
                for (RobotType rt : RobotType.values()) {
                    tempMax = Math.max(tempMax, process(blueprint, new HarvestState(
                            current.ore() - blueprint.obsidianRobotOreCost() + current.oreRobots(),
                            current.clay() - blueprint.obsidianRobotClayCost() + current.clayRobots(),
                            current.obsidian() + current.obsidianRobots(),
                            current.geodes() + current.geodeRobots(),
                            current.oreRobots(),
                            current.clayRobots(),
                            current.obsidianRobots() + 1,
                            current.geodeRobots(),
                            current.minutesLeft() - 1,
                            rt
                    ), cache));
                }
                max = Math.max(max, tempMax);
                cache.put(current, max);
                return max;

            } else if (state.robotType().equals(RobotType.GEODE) && current.ore() >= blueprint.geodeRobotOreCost() && current.obsidian() >= blueprint.geodeRobotObsidianCost()) {
                int tempMax = 0;
                for (RobotType rt : RobotType.values()) {
                    tempMax = Math.max(tempMax, process(blueprint, new HarvestState(
                            current.ore() - blueprint.geodeRobotOreCost() + current.oreRobots(),
                            current.clay() + current.clayRobots(),
                            current.obsidian() - blueprint.geodeRobotObsidianCost() + current.obsidianRobots(),
                            current.geodes() + current.geodeRobots(),
                            current.oreRobots(),
                            current.clayRobots(),
                            current.obsidianRobots(),
                            current.geodeRobots() + 1,
                            current.minutesLeft() - 1,
                            rt
                    ), cache));
                }
                max = Math.max(max, tempMax);
                cache.put(current, max);
                return max;
            }

            current = new HarvestState(
                    current.ore() + current.oreRobots(),
                    current.clay() + current.clayRobots(),
                    current.obsidian() + current.obsidianRobots(),
                    current.geodes() + current.geodeRobots(),
                    current.oreRobots(),
                    current.clayRobots(),
                    current.obsidianRobots(),
                    current.geodeRobots(),
                    current.minutesLeft() - 1,
                    current.robotType()
            );

            max = Math.max(max, current.geodes());
        }

        cache.put(current, max);
        return max;
    }
}
