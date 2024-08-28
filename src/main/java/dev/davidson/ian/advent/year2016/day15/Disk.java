package dev.davidson.ian.advent.year2016.day15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Disk(
        Integer order,
        Integer positions,
        Integer startPosition) {

    //Disc #1 has 13 positions; at time=0, it is at position 1.

    private static final Pattern SPLIT_REGEX = Pattern.compile(
            "#(\\d+).+?(\\d+).+position (\\d+)"
    );

    public static Disk newDisk(final String line) {
        Matcher matcher = SPLIT_REGEX.matcher(line);

        if (matcher.find()) {
            return new Disk(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3))
            );
        }

        throw new IllegalStateException("Couldn't read input line");
    }
}
