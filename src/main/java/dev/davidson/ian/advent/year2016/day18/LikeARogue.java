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

    public static void main(String[] args) {
        LikeARogue likeARogue = new LikeARogue();
        log.info("Part1: {}", likeARogue.execute(39));
        log.info("Part2: {}", likeARogue.execute(399999));
    }

    public Long execute(final Integer totalRows) {
        String input = readFile();
        List<FloorTile> firstRow = Arrays.stream(input.split("")).map(str -> FloorTile.FLOOR_MAP.get(str)).toList();

        List<List<FloorTile>> hall = new ArrayList<>();
        hall.add(firstRow);

        for (int i = 0; i < totalRows; i++) {
            List<FloorTile> next = computeNextRow(hall.get(i));
            hall.add(next);
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
        for (int i = 0; i < current.size(); i++) {
            int count = getCount(
                    getPreviousTile(current, i - 1),
                    getPreviousTile(current, i),
                    getPreviousTile(current, i + 1));

            if (count == 1) {
                nextRow.add(FloorTile.TRAP);
            } else {
                nextRow.add(FloorTile.SAFE);
            }
        }
        return nextRow;
    }

    private int getCount(FloorTile left, FloorTile center, FloorTile right) {
        int count = 0;
        if (left == FloorTile.TRAP && center == FloorTile.TRAP && right == FloorTile.SAFE) {
            count++;
        }

        if (left == FloorTile.SAFE && center == FloorTile.TRAP && right == FloorTile.TRAP) {
            count++;
        }

        if (left == FloorTile.TRAP && center == FloorTile.SAFE && right == FloorTile.SAFE) {
            count++;
        }

        if (left == FloorTile.SAFE && center == FloorTile.SAFE && right == FloorTile.TRAP) {
            count++;
        }
        return count;
    }

    private FloorTile getPreviousTile(final List<FloorTile> current, final int index) {
        return index < 0 || index >= current.size() ? FloorTile.SAFE : current.get(index);
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
