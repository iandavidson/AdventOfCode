package dev.davidson.ian.advent.year2015.day17;

import java.util.List;

public record EggnogComposition(int total, List<Integer> containers) {
    public static EggnogComposition newEggnogComposition(final List<Integer> containers) {
        int count = 0;
        for (Integer value : containers) {
            count += value;
        }

        return new EggnogComposition(count, containers);
    }

    public int size(){
        return containers.size();
    }

}
