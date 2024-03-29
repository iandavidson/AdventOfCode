package org.example.advent.year2023.twentyfour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class HailTrajectory {
    private final long x;
    private final long y;
    private final long z;

    private final long deltaX;
    private final long deltaY;
    private final long deltaZ;


    public String part1ToString(){
        return new StringBuilder().append("P{" + this.x + ", " + this.y + "}, D{" + this.deltaX + " ," + this.deltaY + "}; ").toString();
    }
}
