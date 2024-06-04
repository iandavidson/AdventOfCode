package dev.davidson.ian.advent.year2015.day15;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ScienceForHungryPeople {


    private static final String INPUT_PATH = "adventOfCode/2015/day15/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day15/sample.txt";

    public static void main(String[] args) {
        ScienceForHungryPeople scienceForHungryPeople = new ScienceForHungryPeople();
        List<BakingComponent> bakingComponents = scienceForHungryPeople.readFile();
        log.info("Part1: {}", scienceForHungryPeople.execute(bakingComponents, false));
        log.info("Part2: {}", scienceForHungryPeople.execute(bakingComponents, true));
    }

    public long execute(final List<BakingComponent> bakingComponents, final boolean part2) {
        long max = 0L;
        for (int i = 0; i < 101; i++) {
            for (int j = 0; i + j < 101; j++) {
                for (int k = 0; i + j + k < 101; k++) {
                    for (int l = 0; i + j + k + l < 101; l++) {

                        //only check if composition has 100 items over all
                        if (i + j + k + l == 100) {

                            long prior = max;
                            max = Math.max(max, findTotalScoreWithCalories(bakingComponents, List.of(i, j, k, l), part2));

                            if (prior != max) {
                                log.info("new max: {}", max);
                            }
                        }
                    }
                }
            }
        }

        return max;
    }

    private long findTotalScoreWithCalories(final List<BakingComponent> bakeComponents, final List<Integer> counts, final boolean part2) {
        int capacity = 0;
        int durability = 0;
        int flavor = 0;
        int texture = 0;
        int calories = 0;

        for (int i = 0; i < bakeComponents.size(); i++) {
            capacity += bakeComponents.get(i).capacity() * counts.get(i);
            durability += bakeComponents.get(i).durability() * counts.get(i);
            flavor += bakeComponents.get(i).flavor() * counts.get(i);
            texture += bakeComponents.get(i).texture() * counts.get(i);
            calories += bakeComponents.get(i).calories() * counts.get(i);
        }

        if (part2 && calories != 500) {
            return 0;
        }

        if (capacity <= 0 || durability <= 0 || flavor <= 0 || texture <= 0) {
            return 0;
        }

        return (long) capacity * durability * flavor * texture;
    }

    public List<BakingComponent> readFile() {
        List<BakingComponent> components = new ArrayList<>();

        ClassLoader cl = ScienceForHungryPeople.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                components.add(BakingComponent.newBakingComponent(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        return components;
    }
}
