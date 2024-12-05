package dev.davidson.ian.advent.year2018.day03;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Claim {

    private final int id;
    private final Coordinate start;
    private final int length;
    private final int width;

    public static Claim newClaim(final String line) {
        //example: #1352 @ 972,257: 24x15

        String[] parts = line.split("\\s+");

        int id = Integer.parseInt(parts[0].substring(1));
        Coordinate coordinate = Coordinate.newCoordinate(parts[2].substring(0, parts[2].length() - 1));
        String[] dimensions = parts[3].split("x");

        return new Claim(
                id,
                coordinate,
                Integer.parseInt(dimensions[0]),
                Integer.parseInt(dimensions[1]));
    }
}
