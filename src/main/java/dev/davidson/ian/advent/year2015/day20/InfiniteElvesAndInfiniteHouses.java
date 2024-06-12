package dev.davidson.ian.advent.year2015.day20;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InfiniteElvesAndInfiniteHouses {

    private static final Integer TOTAL_PRESENTS = 34_000_000;
    private static final Integer ARBITRARY_LOOP_END = 2_000_000;
    private static final Integer PART_1_MULTIPLE = 10;
    private static final Integer PART_2_MULTIPLE = 11;

    public static void main(String[] args) {
        InfiniteElvesAndInfiniteHouses infiniteElvesAndInfiniteHouses = new InfiniteElvesAndInfiniteHouses();
        log.info("Part1: {}", infiniteElvesAndInfiniteHouses.execute(false));
        log.info("Part2: {}", infiniteElvesAndInfiniteHouses.execute(true));
    }

    public int execute(final boolean part2) {
        return findMax(part2);
    }

    private int findMax(final boolean part2) {
        for (int i = 1; i < ARBITRARY_LOOP_END; i++) {
            if (findFactorSum(i, part2) >= TOTAL_PRESENTS) {
                return i;
            }
        }

        throw new IllegalStateException("Need to extend ARBITRARY_LOOP_END");
    }


    private int findFactorSum(final int num, final boolean part2) {
        int multiple = getMultiple(part2);
        int val = 0;

        for (int j = 1; j < Math.sqrt(num + 1); j++) {
            if (num % j == 0) {

                if (j * j == num) {
                    if (eligible(j, part2)) {
                        val += j * multiple;
                    }
                } else {

                    if (eligible(j, part2)) {
                        val += num / j * multiple;
                    }
                    if (eligible(num / j, part2)) {
                        val += j * multiple;
                    }
                }
            }
        }
        return val;
    }

    private int getMultiple(boolean part2) {
        return part2 ? PART_2_MULTIPLE : PART_1_MULTIPLE;
    }

    private boolean eligible(int num, boolean part2) {
        if (!part2) {
            return true;
        }

        return num <= 50;
    }
}
