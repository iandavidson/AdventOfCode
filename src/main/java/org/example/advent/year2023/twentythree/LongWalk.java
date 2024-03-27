package org.example.advent.year2023.twentythree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class LongWalk {

    public static final Map<DIRECTION, Map<TILE, Boolean>> IS_VALID_MOVE_MAP = new HashMap<>();
    private static final List<DIRECTION> DIRECTIONS = new ArrayList<>();

    static {{
        DIRECTIONS.add(DIRECTION.LEFT);
        DIRECTIONS.add(DIRECTION.RIGHT);
        DIRECTIONS.add(DIRECTION.UP);
        DIRECTIONS.add(DIRECTION.DOWN);

        for(DIRECTION dir : DIRECTIONS){
            IS_VALID_MOVE_MAP.put(dir, new HashMap<>());
            IS_VALID_MOVE_MAP.get(dir).put(TILE.BLOCK, Boolean.FALSE);
            IS_VALID_MOVE_MAP.get(dir).put(TILE.PATH, Boolean.TRUE);
            IS_VALID_MOVE_MAP.get(dir).put(TILE.SLOPE_RIGHT, Boolean.FALSE);
            IS_VALID_MOVE_MAP.get(dir).put(TILE.SLOPE_DOWN, Boolean.FALSE);
        }

        IS_VALID_MOVE_MAP.get(DIRECTION.RIGHT).put(TILE.SLOPE_RIGHT, Boolean.TRUE);
        IS_VALID_MOVE_MAP.get(DIRECTION.DOWN).put(TILE.SLOPE_DOWN, Boolean.TRUE);
    }}


    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day22/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day23/input.txt";

    public static void main(String[] args) {
        LongWalk longWalk = new LongWalk();
        System.out.println("part1: " + longWalk.part1());

    }

    public long part1() {
        char[][] grid = readFile();

        final Coordinate start = Coordinate.builder().row(0).col(1).build();
        final Coordinate end = Coordinate.builder().row(grid.length - 1).col(grid[0].length - 2).build();


        return findLongestPath(start, end, grid);
    }

    private char[][] readFile() {
        List<String> inputs = new ArrayList<>();
        try {
            ClassLoader classLoader = LongWalk.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                inputs.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        char[][] grid = new char[inputs.size()][inputs.get(0).length()];
        for (int i = 0; i < inputs.size(); i++) {
            grid[i] = inputs.get(i).toCharArray();
        }

        return grid;
    }

    private Long findLongestPath(Coordinate start, Coordinate end, char[][] grid){
        //begin at start, move until we find a fork in the road, build PathSegment then WalkSegment
        List<WalkSegment> walkSegments = new ArrayList<>();

        List<PathSegment> pathSegments = new ArrayList<>();
        pathSegments.add(findNextFork(start, grid));

        Queue<WalkSegment> queue = new LinkedList<>();
        WalkSegment init = WalkSegment.builder().priorPath(pathSegments).build();
        queue.add(init);
        walkSegments.add(init);

        while(!queue.isEmpty()){
            WalkSegment walkSegment = queue.remove();

            Coordinate nextStart = walkSegment.getPriorPath().get(walkSegment.getPriorPath().size()-1).getEnd();
            if(nextStart.equals(end)){
                continue;
            }

            List<Coordinate> neighbors = findValidNeighbors(nextStart, grid);


        }


    }

    private PathSegment findNextFork(Coordinate start, char[][] grid){
        int distance = 0;
        Set<Coordinate> seen = new HashSet<>();
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);

        while(!queue.isEmpty()){
            Coordinate current = queue.remove();
            distance++;

            List<Coordinate> validNeighbors = findValidNeighbors(current, grid);

            if(validNeighbors.size() == 1 || validNeighbors.size() > 2){
                return PathSegment.builder().start(start).end(current).length(distance).build();
            }

            for(Coordinate neighbor : validNeighbors){
                if(!seen.contains(neighbor)){
                    queue.add(neighbor);
                }
            }

            seen.add(current);
        }

        throw new IllegalStateException("shouldn't be here");
//        return null;
    }

    private List<Coordinate> findValidNeighbors(Coordinate current, char[][] grid){
        List<Coordinate> coords = new ArrayList<>();

        for(DIRECTION dir : DIRECTIONS){
            int [] shift = DIRECTION.move(dir);
            char nextTile = grid[current.getRow() + shift[0]][current.getCol() + shift[1]];

            Boolean valid = IS_VALID_MOVE_MAP.get(dir).get(TILE.gridMap.get(nextTile));
            if(valid != null && valid){
                coords.add(Coordinate.builder().row(current.getRow() + shift[0]).col(current.getCol() + shift[1]).build());
            }
        }

        return coords;
    }



}
