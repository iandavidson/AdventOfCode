package dev.davidson.ian.advent.year2016.day01;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record Instruction(int magnitude, int turn) {

    public static Instruction newInstruction(final String value) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(value);

        if(matcher.find()){
            return new Instruction(Integer.parseInt(matcher.group()), value.charAt(0) == 'L' ? -1
                    : 1);
        }

        throw new IllegalStateException("failed to create instruction");
    }
}
