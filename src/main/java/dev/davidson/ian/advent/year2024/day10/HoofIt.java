package dev.davidson.ian.advent.year2024.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HoofIt {

    private static final String SAMPLE_INPUT = "adventOfCode/2024/day10/sample.txt";
    private static final String SAMPLE_2_INPUT = "adventOfCode/2024/day10/sample2.txt";
    private static final String REAL_INPUT = "adventOfCode/2024/day10/real.txt";

    public static void main(String[] args) {
        List<List<Integer>> sampleGrid = readFile(SAMPLE_INPUT);
        List<List<Integer>> sample2Grid = readFile(SAMPLE_2_INPUT);
        List<List<Integer>> readGrid = readFile(REAL_INPUT);
        HoofIt hoofIt = new HoofIt();


        log.info("sample-PART1 result: {}", hoofIt.part1(sampleGrid));
        log.info("sample2-PART1 result: {}", hoofIt.part1(sample2Grid));
        log.info("real-PART1 result: {}", hoofIt.part1(readGrid));
    }

    public long part1(final List<List<Integer>> grid){
        //find all coordinates of 0s
        List<Coordinate> trailheads = new ArrayList<>();
        for(int r = 0; r < grid.size(); r++){
            for(int c = 0; c < grid.get(r).size(); c++){
                if(grid.get(r).get(c) == 0){
                    trailheads.add(new Coordinate(r,c));
                }
            }
        }

        long grandTotal = 0L;
        for(Coordinate trailhead: trailheads){
            grandTotal += totalPathsFromTrailhead(grid, trailhead);
        }

        return grandTotal;
    }

    private long totalPathsFromTrailhead(final List<List<Integer>> grid, final Coordinate trailhead){
        int totalPaths = 0;

        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(trailhead);

        while(!queue.isEmpty()){
            Coordinate current  = queue.remove();
            int currentAlt = grid.get(current.r()).get(current.c());
            if(currentAlt == 9){
                totalPaths++;
                continue;
            }

            queue.addAll(getNeighbors(grid, current, currentAlt));
        }

        return totalPaths;
    }

    private List<Coordinate> getNeighbors(final List<List<Integer>> grid, final Coordinate current,
                                          final int currentAlt){
        List<Coordinate> coordinates = new ArrayList<>();
        List<int []> shifts = List.of(new int[]{-1,0}, new int[]{1,0}, new int[]{0,1}, new int[]{0,-1});

        for(int [] shift : shifts){
            Coordinate neighbor = new Coordinate(current.r() + shift[0], current.c() + shift[1]);

            if(neighbor.isInBounds(grid.size(), grid.getFirst().size())){
                if(currentAlt + 1 == neighbor.getValueInGrid(grid)){
                    coordinates.add(neighbor);
                }
            }
        }

        return coordinates;
    }

    private static List<List<Integer>> readFile(final String filepath) {
        List<List<Integer>> inputGrid = new ArrayList<>();

        ClassLoader cl = HoofIt.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filepath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputGrid.add(Arrays.stream(scanner.nextLine().split("")).mapToInt(Integer::parseInt).boxed().toList());

            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File don't exist 🙀🖕🏻🥸");
        }

        return inputGrid;
    }
    record Coordinate(int r, int c){

        public Boolean isInBounds(int totalRows, int totalCols){
            return r >= 0 && c >= 0 && r < totalRows && c < totalCols;
        }

        public Integer getValueInGrid(List<List<Integer>> grid){
            return grid.get(r).get(c);
        }
    }
}


