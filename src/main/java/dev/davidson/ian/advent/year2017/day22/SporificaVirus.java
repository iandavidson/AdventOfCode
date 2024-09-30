package dev.davidson.ian.advent.year2017.day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SporificaVirus {
    private static final String INPUT_PATH = "adventOfCode/2017/day22/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day22/sample.txt";
    private static final Character INFECTED = '#';
    private static final Integer PART_1_TOTAL_ROUNDS = 10000;
    private static final Integer PART_2_TOTAL_ROUNDS = 10000000;

    //    The virus carrier begins in the middle of the map facing up.
    private static final Map<Direction, int[]> SHIFTS = Map.of(
            Direction.R, new int[]{0, 1},
            Direction.D, new int[]{1, 0},
            Direction.L, new int[]{0, -1},
            Direction.U, new int[]{-1, 0}
    );


    public static void main(String[] args) {
        SporificaVirus sporificaVirus = new SporificaVirus();
        List<String> initialGrid = readFile(INPUT_PATH);
        Coordinate start = findStart(initialGrid);
        Set<Coordinate> infected = findInfected(initialGrid);

        log.info("Part1: {}", sporificaVirus.part1(infected, start));
        log.info("Part2: {}", sporificaVirus.part2(infected, start));
        //2512028 too high :(
    }

    private static List<String> readFile(final String filePath) {
        List<String> initialGrid = new ArrayList<>();

        ClassLoader cl = SporificaVirus.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                initialGrid.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return initialGrid;
    }

    private static Coordinate findStart(final List<String> grid) {
        return new Coordinate(grid.size() / 2, grid.size() / 2);
    }

    private static Set<Coordinate> findInfected(final List<String> grid) {
        final Set<Coordinate> infected = new HashSet<>();

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.getFirst().length(); j++) {
                if (grid.get(i).charAt(j) == INFECTED) {
                    infected.add(new Coordinate(i, j));
                }
            }
        }

        return infected;
    }

    public int part1(final Set<Coordinate> initiallyInfected, final Coordinate start) {

        Set<Coordinate> currentlyInfected = new HashSet<>(initiallyInfected);
        Coordinate coordinate = start;
        Direction direction = Direction.U;

        int count = 0;
        for (int i = 0; i < PART_1_TOTAL_ROUNDS; i++) {

            if (currentlyInfected.contains(coordinate)) {
                //turn right
                direction = Direction.values()[(direction.ordinal() + 1) % Direction.values().length];

                //remove current location from infected set
                currentlyInfected.remove(coordinate);
            } else {
                //turn left
                direction = Direction.values()[(direction.ordinal() + 3) % Direction.values().length];

                currentlyInfected.add(coordinate);
                count++;
            }

            coordinate = new Coordinate(
                    coordinate.r() + SHIFTS.get(direction)[0],
                    coordinate.c() + SHIFTS.get(direction)[1]
            );
        }

        return count;
    }

    public int part2(final Set<Coordinate> initiallyInfected, final Coordinate start) {

        //0 - clean, 1 - weakened, 2 - infected, 3 - flagged
        Map<Coordinate, Integer> tracker = new HashMap<>();
        for (Coordinate infected : initiallyInfected) {
            tracker.put(infected, 2);
        }

        Coordinate coordinate = start;
        Direction direction = Direction.U;
        int count = 0;


        for (int i = 0; i < PART_2_TOTAL_ROUNDS; i++) {
            //default to clean state for ones we don't track
            int currentState = tracker.getOrDefault(coordinate, 0);

            switch (currentState) {
                case 0 -> {
                    direction = Direction.values()[(direction.ordinal() + 3) % Direction.values().length];
                }
                case 1 -> {
                    count++;
                }
                case 2 -> {
                    direction = Direction.values()[(direction.ordinal() + 1) % Direction.values().length];
                }
                case 3 -> {
                    direction = Direction.values()[(direction.ordinal() + 2) % Direction.values().length];
                }
            }

            //update current state in map at current
            tracker.put(coordinate, (currentState + 1) % 4);

            coordinate = new Coordinate(
                    coordinate.r() + SHIFTS.get(direction)[0],
                    coordinate.c() + SHIFTS.get(direction)[1]
            );

        }

        return count;
    }

    enum Direction {
        R, D, L, U
    }
}
