package dev.davidson.ian.advent.year2015.day25;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LetItSnow {

    private static final Integer ROW = 2947;
    private static final Integer COLUMN = 3029;
    private static final Long START_VALUE = 20151125L;
    private static final Integer LOOP_DURATION = 100000;

    public static void main(String[] args) {
        LetItSnow letItSnow = new LetItSnow();
        log.info("Part1: {}", letItSnow.part1());
    }

    public long part1() {
        long current = START_VALUE;
        for (int i = 1; i < LOOP_DURATION; i++) {
            for (int r = i; r > 0; r--) {
                int c = i - r + 1;

                if (r == 1 && c == 1) {
                    continue;
                }

                current = getNext(current);

                if (r == ROW && c == COLUMN) {
                    return current;
                }
            }
        }

        return -1L;
    }

    private Long getNext(final Long current) {
        return (current * 252533) % 33554393;
    }
}
