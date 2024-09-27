package dev.davidson.ian.advent.year2017.day20;

import lombok.Builder;

@Builder
public record Delta(int position, int velocity, int acceleration) {

    public long distanceAtMoment(final int time){
        return Math.abs((long) position +  ((long) time * velocity)  + ((long) time * time * acceleration));
    }

}
