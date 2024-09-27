package dev.davidson.ian.advent.year2017.day20;

import lombok.Builder;

@Builder
public record Delta(Long position, Long velocity, Long acceleration) {

    public Delta update() {
        long v = velocity + acceleration;
        long p = position + v;

        return Delta.builder()
                .position(p)
                .velocity(v)
                .acceleration(acceleration)
                .build();
    }

}
