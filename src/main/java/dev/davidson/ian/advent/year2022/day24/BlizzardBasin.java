package dev.davidson.ian.advent.year2022.day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlizzardBasin {

    private static final String INPUT_PATH = "adventOfCode/2022/day24/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2022/day24/sample.txt";
    private static final String MINI_PATH = "adventOfCode/2022/day24/mini.txt";

    private static final List<int[]> SHIFTS = List.of(
            new int[]{0, 0},
            new int[]{-1, 0},
            new int[]{1, 0},
            new int[]{0, 1},
            new int[]{0, -1}
    );

    /*
    intuition:
    - Blizzards will always be in the same place after ((|rows| -2) * (|cols| -2)) turns
      - when tracking "visited", we can prune less than desirable paths
        - eg: 12 rows by 12 cols; 100 turn blizzard cycle
        - if we land at 3,3 on turn 5, then we also land on 3,3 at turn 105, we can throw away the 105 execution path,
        - additionally, if 3,3 was landed on at 105 before, and have another path that lands there on 5, we should
        replace the entry ^105^5^, as 5 turns was optimal.
      - -> Visited State rough idea:
            - Coordinate
            - Turn Landed on coordinate
            - landed on % blizzardCycle

    - Dimensions of real input(25 * 121), excluding border
        - total tracking as stated above 3025 tiles, cycle length 3025: 9150625
        -> if we had a completely opened grid with no blizzards.
     */


    public static void main(String[] args) {
        BlizzardBasin blizzardBasin = new BlizzardBasin();
        log.info("Part1: {}", blizzardBasin.part1());
        log.info("Part2: {}", blizzardBasin.part2());
    }


    public Integer part1() {
        Basin basin = readFile();
        Coordinate start = new Coordinate(0, 1);
        Coordinate finish = new Coordinate(basin.getRows() - 1, basin.getCols() - 2);
        return execute(basin, start, finish);
    }

    public Integer part2() {
        Basin basin = readFile();
        Coordinate start = new Coordinate(0, 1);
        Coordinate finish = new Coordinate(basin.getRows() - 1, basin.getCols() - 2);
        Integer count = execute(basin, start, finish);
        count += execute(basin, finish, start) + 1;
        count += execute(basin, start, finish) + 1;
        return count;
    }

    private Integer execute(final Basin basin, final Coordinate start, final Coordinate finish) {
        Set<String> occupiedTileSet;

        int round = 0;
        WalkState initial = WalkState.builder()
                .coordinate(start)
                .round(round)
                .cycleRemainder(round)
                .build();

        Queue<WalkState> queue = new LinkedList<>();
        queue.add(initial);
        Set<WalkState> visited = new HashSet<>();
        visited.add(initial);

        while (!queue.isEmpty()) {

            int n = queue.size();
            basin.updateBlizzards();
            occupiedTileSet = basin.buildOccupiedTileSet();
            round += 1;

            for (int i = 0; i < n; i++) {
                WalkState current = queue.remove();

                if (current.coordinate().equals(finish)) {
                    return round - 1;
                }

                List<WalkState> validNeighbors = findValidNeighbors(occupiedTileSet, current, basin);
                for (WalkState neighbor : validNeighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }

        return -1;
    }

    private List<WalkState> findValidNeighbors(final Set<String> occupiedTileSet,
                                               final WalkState current,
                                               final Basin basin) {

        List<WalkState> neighbors = new ArrayList<>();

        for (int[] shift : SHIFTS) {

            Coordinate proposedCoordinate = new Coordinate(current.coordinate().row() + shift[0],
                    current.coordinate().col() + shift[1]);

            if (isValidMove(occupiedTileSet, basin, proposedCoordinate)) {
                neighbors.add(WalkState.builder()
                        .coordinate(proposedCoordinate)
                        .round(current.round() + 1)
                        .cycleRemainder((current.round() + 1) % basin.getBlizzardPeriod())
                        .build());
            }
        }

        return neighbors;
    }

    private boolean isValidMove(final Set<String> occupiedTileSet,
                                final Basin basin,
                                final Coordinate proposedCoordinate) {

        if (!isInBounds(basin, proposedCoordinate)) {
            //check if valid tile on map
            return false;
        }

        //ensure blizzard is not at proposed location
        return !occupiedTileSet.contains(proposedCoordinate.toId());
    }

    private boolean isInBounds(final Basin basin, final Coordinate proposedCoordinate) {
        int row = proposedCoordinate.row();
        int col = proposedCoordinate.col();
        int rowMax = basin.getRows();
        int colMax = basin.getCols();

        if (row == 0 && col == 1 || row == rowMax - 1 && col == colMax - 2) {
            //check start and finish locations explicitly
            return true;
        }

        //check if within borders
        return row > 0 && row < rowMax - 1 && col > 0 && col < colMax - 1;
    }

    private Basin readFile() {
        List<String> inputLines = new ArrayList<>();

        ClassLoader classLoader = BlizzardBasin.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read input file");
        }

        int rows = inputLines.size();
        int cols = inputLines.getFirst().length();
        List<Blizzard> blizzards = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Character ch = inputLines.get(i).charAt(j);
                if (Direction.TILE_TO_DIRECTION_MAP.containsKey(ch)) {
                    blizzards.add(
                            Blizzard.builder()
                                    .currentLocation(new Coordinate(i, j))
                                    .direction(Direction.TILE_TO_DIRECTION_MAP.get(ch))
                                    .build());
                }
            }
        }

        return Basin.builder()
                .rows(rows)
                .cols(cols)
                .blizzardPeriod((cols - 2) * (rows - 2))
                .blizzards(blizzards)
                .build();
    }
}
