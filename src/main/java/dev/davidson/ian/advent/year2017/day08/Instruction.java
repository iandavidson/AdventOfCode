package dev.davidson.ian.advent.year2017.day08;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record Instruction(String applyLabel, Long shift, String condLabel, String comparison, Long comparator) {
    //c inc -20 if c == 10
    //0 1   2    3 4  5  6

    private static final String DEC = "dec";

    public static Instruction newInstruction(final String line) {
        String[] parts = line.split("\\s+");

        String applyLabel = parts[0];

        long shift = Long.parseLong(parts[2]);
        if (parts[1].equals(DEC)) {
            shift *= -1;
        }

        String condLabel = parts[4];
        String comparison = parts[5];
        long comparator = Long.parseLong(parts[6]);

        return new Instruction(
                applyLabel,
                shift,
                condLabel,
                comparison,
                comparator
        );
    }
}
