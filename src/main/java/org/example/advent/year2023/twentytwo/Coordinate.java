package org.example.advent.year2023.twentytwo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Coordinate implements Comparable<Coordinate> {
    private final Integer x;
    private final Integer y;
    private Integer z;


    public void fall(int delta) {
        setZ(z - delta);
    }

    @Override
    public int compareTo(Coordinate o) {
        return this.z - o.getZ();
    }
}
