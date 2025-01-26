package dev.davidson.ian.advent.year2024.day04;

import java.util.Map;

public enum DIRECTION {
    R, RD, D, LD, L, LU, U, RU;

    public static final Map<DIRECTION, int[]> SHIFTS = Map.of(
            R, new int[]{0,1},
            RD, new int[]{1,1},
            D, new int[]{1,0},
            LD, new int[]{1,-1},
            L, new int[]{0,-1},
            LU, new int[]{-1,-1},
            U, new int[]{-1,0},
            RU,new int[]{-1,1}
    );

}
