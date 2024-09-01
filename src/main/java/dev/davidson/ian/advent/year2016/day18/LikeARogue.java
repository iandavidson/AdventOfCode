package dev.davidson.ian.advent.year2016.day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LikeARogue {

    private static final String INPUT_PATH = "adventOfCode/2016/day18/input.txt";
    private static final Integer ROWS = 40;
    public static void main(String[] args) {
        LikeARogue likeARogue = new LikeARogue();
        log.info("Part1: {}", likeARogue.part1());
    }

    public Long part1() {
        String input = readFile();
        List<FloorTile> firstRow = Arrays.stream(input.split("")).map(str -> FloorTile.FLOOR_MAP.get(str)).toList();

        List<List<FloorTile>> hall = new ArrayList<>();
        hall.add(firstRow);

        for(int i = 1; i < ROWS; i++){
            hall.add(computeNextRow(hall.get(i)));
        }

        return hall.stream()
                .mapToLong(
                        row -> row.stream()
                                .filter(tile -> tile.equals(FloorTile.SAFE))
                                .count())
                .sum();
    }

    private List<FloorTile> computeNextRow(final List<FloorTile> current) {
        List<FloorTile> nextRow = new ArrayList<>();
        for(int i = 0 ; i < floorTiles.size(); i++){
            /*
            ABCDE
            12345

The type of tile 2 is based on the types of tiles A, B, and C; the type of tile 5 is based on tiles D, E, and an imaginary "safe" tile. Let's call these three tiles from the previous row the left, center, and right tiles, respectively.
Then, a new tile is a trap only in one of the following situations:

Its left and center tiles are traps, but its right tile is not.
Its center and right tiles are traps, but its left tile is not.
Only its left tile is a trap.
Only its right tile is a trap.
In any other situation, the new tile is safe.
             */
            FloorTile left = getPreviousTile(current, i-1);
            FloorTile center = getPreviousTile(current, i);
            FloorTile right = getPreviousTile(current, i+1);



            nextRow.add();
        }
    }

    private FloorTile getPreviousTile(final List<FloorTile> current, final int index){
        if(index < 0 || index >= current.size()){
            return FloorTile.SAFE;
        }

        return current.get(index);
    }


    private String readFile() {
        ClassLoader cl = LikeARogue.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Failed to read input file at provided path.");
        }
    }
}
