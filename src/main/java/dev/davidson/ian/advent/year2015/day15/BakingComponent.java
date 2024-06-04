package dev.davidson.ian.advent.year2015.day15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record BakingComponent(String name, int capacity, int durability, int flavor, int texture, int calories) {

    public static BakingComponent newBakingComponent(String line) {
        Pattern pattern = Pattern.compile("^(\\w+).*?(-*\\d+).*?(-*\\d+).*?(-*\\d+).*?(-*\\d+).*?(-*\\d+)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return new BakingComponent(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
        } else {
            throw new IllegalStateException("can't interpret");
        }

    }
}
