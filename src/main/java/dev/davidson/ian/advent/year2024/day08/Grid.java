package dev.davidson.ian.advent.year2024.day08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Grid(Map<Character, List<Frequency>> frequencies, int rows, int cols) {

    public static Grid newGrid(final List<String> rows) {
        Map<Character, List<Frequency>> frequencies = new HashMap<>();

        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).length(); j++) {
                char freq = rows.get(i).charAt(j);

                if (freq != '.') {
                    frequencies.putIfAbsent(freq, new ArrayList<>());
                    frequencies.get(freq).add(new Frequency(new Coordinate(i, j), freq));
                }
            }
        }

        return new Grid(frequencies, rows.size(), rows.getFirst().length());
    }

    public boolean isInBounds(final Coordinate coordinate) {
        return coordinate.r() >= 0 && coordinate.r() < rows && coordinate.c() >= 0 && coordinate.c() < cols;
    }
}
