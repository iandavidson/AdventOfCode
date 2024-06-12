package dev.davidson.ian.advent.year2015.day20;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InfiniteElvesAndInfiniteHouses {

    private static final Integer PART_1_INPUT = 34_000_000;
    private static final Integer ARBITRARY_LOOP_END = 2_500_000;

    public static void main(String[] args) {
        InfiniteElvesAndInfiniteHouses infiniteElvesAndInfiniteHouses = new InfiniteElvesAndInfiniteHouses();
        log.info("Part1: {}", infiniteElvesAndInfiniteHouses.part1());
    }

    public int part1() {
        return findMax();
    }

    private int findMax() {
        for (int i = 1; i < ARBITRARY_LOOP_END; i++) {
            if (findFactorSum(i) >= PART_1_INPUT) {
                return i;
            }
        }

        throw new IllegalStateException("whoops shouldn't be here, 🥸");
    }


    private int findFactorSum(int num) {
        int val = 0;

        for (int j = 1; j < Math.sqrt(num + 1); j++) {
            if (num % j == 0) {
                if (j * j == num) {
                    val += j * 10;
                } else {
                    val += (j * 10) + (num / j * 10);
                }
            }
        }

        return val;
    }
}
