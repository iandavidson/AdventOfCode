package dev.davidson.ian.advent.year2017.day21;

import java.util.Arrays;
import java.util.List;

public record Rule(Pattern pattern, List<String> replacement) {
    public static Rule newRule(final String line) {
        // ##./#.#/.#. => ...#/#.#./##.#/###.
        String[] parts = line.split("=>");

        List<String> patternParts = Arrays.stream(parts[0].trim().split("/")).toList();
        List<String> replacementParts = Arrays.stream(parts[1].trim().split("/")).toList();

        return new Rule(Pattern.newPattern(patternParts), replacementParts);
    }


    //4 orientations of pattern can be matched against
    public boolean isMatch(List<String> compare){
        return pattern.isMatch(compare);
    }

}
