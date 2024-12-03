package dev.davidson.ian.advent.year2024.day02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Report(List<Integer> levels, List<List<Integer>> oneOffs) {

    public static Report newReport(final String line) {
        List<Integer> levels = Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList();

        List<List<Integer>> oneOffs = new ArrayList<>();
        for (int i = 0; i < levels.size(); i++) {
            List<Integer> oneOff = new ArrayList<>();
            for (int j = 0; j < levels.size(); j++) {
                if (j != i) {
                    oneOff.add(levels.get(j));
                }
            }
            oneOffs.add(oneOff);
        }

        return new Report(levels, oneOffs);
    }

    private static boolean isSafe(List<Integer> levels) {
        int previous = levels.getFirst();

        for (int i = 1; i < levels.size(); i++) {
            int diff = previous - levels.get(i);

            if (Math.abs(diff) == 0 || Math.abs(diff) > 3) {
                return false;
            }

            previous = levels.get(i);
        }

        int previousDiff = levels.get(0) - levels.get(1);
        for (int i = 2; i < levels.size(); i++) {
            previous = levels.get(i - 1);
            int current = levels.get(i);
            int diff = previous - current;

            if (Integer.signum(diff) != Integer.signum(previousDiff)) {
                return false;
            }

            previousDiff = diff;
        }

        return true;
    }

    public boolean isSafe() {
        return isSafe(this.levels);
    }

    public boolean isSafePart2() {
        if (isSafe()) {
            return true;
        }

        for (List<Integer> oneOff : oneOffs) {
            if (isSafe(oneOff)) {
                return true;
            }
        }

        return false;
    }
}
