package dev.davidson.ian.advent.year2017.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpiralMemory {

    private static final String INPUT_PATH = "adventOfCode/2017/day03/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day03/sample.txt";
    private static final List<Coordinate> ALL_NEIGHBORS = List.of(
            new Coordinate(-1, -1),
            new Coordinate(-1, 0),
            new Coordinate(-1, 1),
            new Coordinate(0, -1),
            new Coordinate(0, 1),
            new Coordinate(1, -1),
            new Coordinate(1, 0),
            new Coordinate(1, 1)
    );

    public static void main(String[] args) {
        SpiralMemory spiralMemory = new SpiralMemory();
        Integer input = readFile(INPUT_PATH);
        spiralMemory.execute(input);
    }

    public void execute(final Integer input) {
        int endRow = 0;
        int endCol = 0;

        Coordinate current = new Coordinate(1, 0);

        Direction dir = Direction.R;
        Map<Coordinate, Long> occupied = new HashMap<>();

        boolean foundPart2 = false;
        long part2Value = 0L;

        for (int i = 1; i < input + 1; i++) {
            current = new Coordinate(current.r() + Direction.SHIFT.get(dir)[0],
                    current.c() + Direction.SHIFT.get(dir)[1]);

            if (i == 1) {
                occupied.put(current, 1L);
            } else {
                long value = findNeighborSum(occupied, current);
                if (!foundPart2 && value > input) {
                    part2Value = value;
                    foundPart2 = true;
                }

                occupied.put(current, value);
            }


            Direction look = Direction.LOOK_MAP.get(dir);
            Coordinate lookCoordinate = new Coordinate(current.r() + Direction.SHIFT.get(look)[0],
                    current.c() + Direction.SHIFT.get(look)[1]);

            if (i != 1 && !occupied.containsKey(lookCoordinate)) {
                dir = look;
            }

            if (i == input) {
                endRow = current.r();
                endCol = current.c();
            }
        }

        log.info("Part1: {}", Math.abs(endRow - 1) + Math.abs(endCol - 1));
        log.info("Part2: {}", part2Value);
    }

    private long findNeighborSum(final Map<Coordinate, Long> map, final Coordinate current) {
        Long sum = 0L;
        for (Coordinate shift : ALL_NEIGHBORS) {
            Coordinate potentialNeighbor = Coordinate.combineCoordinate(current, shift);
            if (map.containsKey(potentialNeighbor)) {
                sum += map.get(potentialNeighbor);
            }
        }

        return sum;
    }

    private static Integer readFile(final String filePath) {
        ClassLoader cl = SpiralMemory.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return Integer.parseInt(scanner.nextLine());
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }

}
