package org.example.advent.year2022.day18;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record Boulder(int x, int y, int z){

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boulder boulder = (Boulder) o;
        return x == boulder.x && y == boulder.y && z == boulder.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public boolean isNeighbor(Boulder boulder) {
        List<Integer> diffs = new ArrayList<>(List.of(Math.abs(x - boulder.x), Math.abs(y - boulder.y), Math.abs(z - boulder.z)));
        Collections.sort(diffs);

        if(diffs.get(1) == 0 && diffs.get(2) == 1){
            //2 should be 0, 1 should be 1
            return true;
        }

        return false;
    }

    enum NEIGHBOR_STATUS {
        SAME, NEIGHBOR, NONE;


        NEIGHBOR_STATUS getStatus(final int val1, final int val2){
            int diff = Math.abs(val1 - val2);
            switch(diff){
                case 0 -> {
                    return SAME;
                }
                case 1 -> {
                    return NEIGHBOR;
                }
                default -> {
                    return NONE;
                }
            }
        }
    }
}
