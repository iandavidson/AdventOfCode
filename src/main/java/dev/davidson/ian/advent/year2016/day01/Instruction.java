package dev.davidson.ian.advent.year2016.day01;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Instruction(int magnitude, int turn) {

    private static final Pattern PATTERN = Pattern.compile("\\d+");

    public static Instruction newInstruction(final String value) {

        Matcher matcher = PATTERN.matcher(value);
        if (matcher.find()) {
            return new Instruction(Integer.parseInt(matcher.group()), value.charAt(0) == 'L' ? -1
                    : 1);
        }

        throw new IllegalStateException("Failed to create instruction");
    }
}
