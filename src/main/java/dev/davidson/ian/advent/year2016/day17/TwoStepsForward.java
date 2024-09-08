package dev.davidson.ian.advent.year2016.day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwoStepsForward {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day17/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day17/input.txt";
    private static final Coordinate START = new Coordinate(0, 0);
    private static final Coordinate FINISH = new Coordinate(3, 3);
    private static final Set<Character> UNLOCKED_SET = Set.of('b', 'c', 'd', 'e', 'f');
    private static final Map<DIRECTION, int[]> DIRECTION_TO_COORD_SHIFT = Map.of(
            DIRECTION.U, new int[]{-1, 0},
            DIRECTION.D, new int[]{1, 0},
            DIRECTION.L, new int[]{0, -1},
            DIRECTION.R, new int[]{0, 1}
    );

    public static void main(String[] args) {
        TwoStepsForward twoStepsForward = new TwoStepsForward();
        log.info("Part1: {}", twoStepsForward.part1());
        log.info("Part2: {}", twoStepsForward.part2());
    }

    public String part1() {
        String salt = readFile();

        Queue<PathState> queue = new LinkedList<>();
        queue.add(new PathState(START, "", Hasher.encodeToHex(salt)));

        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                PathState current = queue.remove();
                if (current.coordinate().equals(FINISH)) {
                    return current.pathsTaken();
                }

                List<PathState> pathStateList = findValidNeighbors(current, salt);
                queue.addAll(pathStateList);
            }
        }

        return "Whoops";
    }

    public Integer part2() {
        String salt = readFile();

        Queue<PathState> queue = new LinkedList<>();
        queue.add(new PathState(START, "", Hasher.encodeToHex(salt)));

        Integer pathLength = 0;

        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                PathState current = queue.remove();
                if (current.coordinate().equals(FINISH)) {
                    pathLength = Math.max(pathLength, current.pathsTaken().length());
                    continue;
                }

                List<PathState> pathStateList = findValidNeighbors(current, salt);
                queue.addAll(pathStateList);
            }
        }

        return pathLength;
    }


    private List<PathState> findValidNeighbors(final PathState current, final String salt) {
//        up, down, left, right

        List<PathState> pathStateList = new ArrayList<>();

        for (int i = 0; i < DIRECTION.values().length; i++) {
            DIRECTION dir = DIRECTION.values()[i];

            Coordinate next = new Coordinate(
                    DIRECTION_TO_COORD_SHIFT.get(dir)[0] + current.coordinate().row(),
                    DIRECTION_TO_COORD_SHIFT.get(dir)[1] + current.coordinate().col());

            boolean isUnlocked = UNLOCKED_SET.contains(current.hash().charAt(i));

            if (isInBounds(next) && isUnlocked) {
                String nextPath = current.pathsTaken() + dir.toString();
                pathStateList.add(
                        new PathState(
                                next,
                                nextPath,
                                Hasher.encodeToHex(salt + nextPath))
                );
            }

        }

        return pathStateList;
    }

    private boolean isInBounds(final Coordinate coordinate) {
        return coordinate.row() >= 0 && coordinate.col() >= 0 && coordinate.row() <= 3 && coordinate.col() <= 3;
    }

    private String readFile() {
        ClassLoader cl = TwoStepsForward.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }
}
