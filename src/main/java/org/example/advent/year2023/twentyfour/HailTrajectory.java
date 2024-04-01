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


    public String part1ToString() {
        return new StringBuilder().append("P{").append(this.x).append(", ").append(this.y).append("}, D{").append(this.deltaX).append(" ,").append(this.deltaY).append("}; ").toString();
    }


    public double getM() {
        long x2 = x + deltaX;
        long y2 = y + deltaY;
        return (double) (y - y2) / (x - x2);
    }

    public double getC() {
        //-c = mx - y
        return -(getM() * x - y);
    }

    public Coordinate crossesAt(HailTrajectory o) {
        //O - coordinate of intersection

        //a1x + b1y + c1 = O
        //a2x + b2y + c2 = O
        //-> a = m, b = -1, c = c

        double oX = (-o.getC() + getC()) / (-getM() + 1 * o.getM());
        double oY = (getC() * o.getM() - o.getC() * getM()) / (-getM() + o.getM());
        return new Coordinate(oX, oY);
    }
}
