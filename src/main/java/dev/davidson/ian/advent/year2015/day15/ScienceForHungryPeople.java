package dev.davidson.ian.advent.year2015.day15;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ScienceForHungryPeople {


    private static final String INPUT_PATH = "adventOfCode/2015/day15/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day15/sample.txt";

    public static void main(String[] args) {
        ScienceForHungryPeople scienceForHungryPeople = new ScienceForHungryPeople();
        log.info("part1: {}", scienceForHungryPeople.part1());
    }

    public long part1() {
        List<BakingComponent> bakingComponents = readFile();
        Map<BakingComponent, Integer> countMap = new HashMap<>();

        long max = 0L;
        for(int i = 0 ; i < 101; i++){
            for(int j = 0 ; i + j < 101; j++){
                for(int k = 0; i + j + k < 101; k++){
                    for(int l = 0 ; i + j + k + l < 101; l++){
                        if(i + j + k + l == 100){
                            long prior = max;
                            max = Math.max(max, findTotalScore(bakingComponents, List.of(i, j,k,l)));
                            if(prior != max){
                                log.info("max: {}", max);
                            }
                        }
                    }
                }
            }
        }


//        return recurse(bakingComponents, 0, new LinkedHashMap<>(), new HashMap<>());


        // 200000000 too high
        return max;
    }


    private long findTotalScore(final List<BakingComponent> bakeComponents, final List<Integer> counts){
        int capacity = 0;
        int durability = 0;
        int flavor = 0;
        int texture = 0;

        for(int i = 0 ; i < bakeComponents.size(); i++){
            capacity += bakeComponents.get(i).capacity() * counts.get(i);
            durability += bakeComponents.get(i).durability() * counts.get(i);
            flavor += bakeComponents.get(i).flavor() * counts.get(i);
            texture += bakeComponents.get(i).texture() * counts.get(i);
        }

        if(capacity <= 0 || durability <= 0 || flavor <= 0 || texture <= 0){
            return 0;
        }

        return (long) capacity * durability * flavor * texture;

    }

    private List<BakingComponent> readFile() {
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
