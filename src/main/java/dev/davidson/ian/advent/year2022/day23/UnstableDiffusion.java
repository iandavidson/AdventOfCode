package dev.davidson.ian.advent.year2022.day23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnstableDiffusion {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day23/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day23/input.txt";
    private static final String MINI_PATH = "adventOfCode/2022/day23/mini.txt";
    private static final Character ELF = '#';
    private static final Integer ROUNDS = 10;

    private static final Map<Direction, int[][]> DIRECTION_SEARCH_MAP = Map.of(
            Direction.ALL, new int[][]{
                    new int[]{-1, 0},
                    new int[]{-1, -1},
                    new int[]{-1, 1},
                    new int[]{0, -1},
                    new int[]{0, 1},
                    new int[]{1, 0},
                    new int[]{1, 1},
                    new int[]{1, -1},
            },
            Direction.NORTH, new int[][]{
                    new int[]{-1, 0},
                    new int[]{-1, -1},
                    new int[]{-1, 1},
            },
            Direction.SOUTH, new int[][]{
                    new int[]{1, 0},
                    new int[]{1, 1},
                    new int[]{1, -1},
            },
            Direction.WEST, new int[][]{
                    new int[]{0, -1},
                    new int[]{-1, -1},
                    new int[]{1, -1},
            },
            Direction.EAST, new int[][]{
                    new int[]{0, 1},
                    new int[]{-1, 1},
                    new int[]{1, 1},
            }
    );

    private static final Map<Direction, int[]> DIRECTION_SHIFT_MAP = Map.of(
            Direction.ALL, new int[]{0, 0},
            Direction.NORTH, new int[]{-1, 0},
            Direction.SOUTH, new int[]{1, 0},
            Direction.WEST, new int[]{0, -1},
            Direction.EAST, new int[]{0, 1}
    );

    private static final List<Direction> DIRECTIONS_ORDER = List.of(
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST,
            Direction.EAST
    );


    public static void main(String[] args) {
        UnstableDiffusion unstableDiffusion = new UnstableDiffusion();
        log.info("Part1: {}", unstableDiffusion.part1());
    }

    /*
    round first half:
    check to see if there is no elves around at all, if so remain
If there is no Elf in the N, NE, or NW adjacent positions, the Elf proposes moving north one step.
If there is no Elf in the S, SE, or SW adjacent positions, the Elf proposes moving south one step.
If there is no Elf in the W, NW, or SW adjacent positions, the Elf proposes moving west one step.
If there is no Elf in the E, NE, or SE adjacent positions, the Elf proposes moving east one step.

- take list of elvs, and create a new list of elves
- make new hashmap that does coordiante ->
     */

    public long part1() {
        Map<Integer, Elf> elfMap = readfile();
        Map<Integer, Map<Integer, List<Elf>>> coordinateMap = elfMapToCoordinateMap(elfMap);


        for (int i = 0; i < ROUNDS; i++) {
            //compute next moves (make new map)
//            Map<Coordinate, List<Elf>> nextRound
            Map<Integer, Map<Integer, List<Elf>>> nextMap = nextMoves(coordinateMap, elfMap.values(), i);
            elfMap = applyMoves(nextMap);
            coordinateMap = elfMapToCoordinateMap(elfMap);



            //iterate through nextMap, if sole member in List, replace elf instance with new coords, same ordinal
            //if it is not
        }


        int minRow = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int minCol = Integer.MAX_VALUE;
        int maxCol = Integer.MIN_VALUE;
        for(Elf elf : elfMap.values()){
            minRow = Math.min(minRow, elf.row());
            maxRow = Math.max(maxRow, elf.row());
            minCol = Math.min(minCol, elf.col());
            maxCol = Math.max(maxCol, elf.col());
        }

        return (long) (maxRow - minRow) * (maxCol - minCol);
    }

    private Map<Integer, Map<Integer, List<Elf>>> nextMoves(final Map<Integer, Map<Integer, List<Elf>>> currentMap
            , final Collection<Elf> elfs, final int round) {
        Map<Integer, Map<Integer, List<Elf>>> next = new HashMap<>();

        for (Elf elf : elfs) {

            //Try all sides first, in order to remain in same spot.
            boolean collision = false;
            //all
            for (int[] delta : DIRECTION_SEARCH_MAP.get(Direction.ALL)) {
                //if did not find a match select it
                if (currentMap.containsKey(elf.row() + delta[0]) && currentMap.get(elf.row() + delta[0]).containsKey(elf.col() + delta[1])) {
                    collision = true;
                    break;
                }
            }

            if (!collision) {
                if (!next.containsKey(elf.row())) {
                    next.put(elf.row(), new HashMap<>());
                }

                if (!next.get(elf.row()).containsKey(elf.col())) {
                    next.get(elf.row()).put(elf.col(), new ArrayList<>());
                }

                next.get(elf.row()).get(elf.col()).add(elf);
                continue;
            }

            //Now work through directions based on round#
            for (int i = 0; i < 4; i++) {
                Direction direction = DIRECTIONS_ORDER.get((i + round) % 4);
                collision = false;

                for (int[] delta : DIRECTION_SEARCH_MAP.get(direction)) {
                    if (currentMap.containsKey(elf.row() + delta[0]) && currentMap.get(elf.row() + delta[0]).containsKey(elf.col() + delta[1])) {
                        collision = true;
                        break;
                    }
                }

                if (!collision) {
                    int newRow = elf.row() + DIRECTION_SHIFT_MAP.get(direction)[0];
                    int newCol = elf.col() + DIRECTION_SHIFT_MAP.get(direction)[1];
                    if (!next.containsKey(newRow)) {
                        next.put(newRow, new HashMap<>());
                    }

                    if (!next.get(newRow).containsKey(newCol)) {
                        next.get(newRow).put(newCol, new ArrayList<>());
                    }

                    next.get(newRow).get(newCol).add(elf);
                    break;
                }
            }

        }
        return next;
    }

    private Map<Integer, Elf> applyMoves(final Map<Integer, Map<Integer, List<Elf>>> nextMap) {
        Map<Integer, Elf> elfMap = new HashMap<>();

        for (Integer row : nextMap.keySet()) {
            for (Integer col : nextMap.get(row).keySet()) {
                List<Elf> elvesAtCoord = nextMap.get(row).get(col);

                if (elvesAtCoord.size() == 1) {
                    //if only elf that landed at that new location, move the elf to that location
                    elfMap.put(
                            elvesAtCoord.getFirst().getOrdinal(),
                            Elf.builder().ordinal(elvesAtCoord.getFirst().getOrdinal())
                                    .coordinate(new Coordinate(row, col))
                                    .build());
                } else {
                    //if more than one elf landed at that new location, explicitly reassign from elfOrderMap to
                    // nextRoundMap
                    for(Elf elf : elvesAtCoord){
                        elfMap.put(elf.getOrdinal(), elf);
                    }
                }


            }
        }

        return elfMap;
    }

    private Map<Integer, Map<Integer, List<Elf>>> elfMapToCoordinateMap(final Map<Integer, Elf> elfMap){
        Map<Integer, Map<Integer, List<Elf>>> coordinateMap = new HashMap<>();
        for (Elf elf : elfMap.values()) {
            if (!coordinateMap.containsKey(elf.row())) {
                coordinateMap.put(elf.row(), new HashMap<>());
            }

            if (!coordinateMap.get(elf.row()).containsKey(elf.col())) {
                coordinateMap.get(elf.row()).put(elf.col(), new ArrayList<>());
            }

            coordinateMap.get(elf.row()).get(elf.col()).add(elf);
        }

        return coordinateMap;
    }


    private Map<Integer, Elf> readfile() {
        List<String> input = new ArrayList<>();

        ClassLoader cl = UnstableDiffusion.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(MINI_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, Elf> elfMap = new HashMap<>();
        Set<Elf> elfList = new HashSet<>();
        int order = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == ELF) {
                    elfMap.put(order, Elf.builder().ordinal(order++).coordinate(new Coordinate(i, j)).build());
                    order++;
//                    elfList.add(Elf.builder().ordinal(order++).coordinate(new Coordinate(i, j)).build());
                }
            }
        }

        return elfMap;
    }


}
