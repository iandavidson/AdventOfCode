package dev.davidson.ian.advent.year2022.day09;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static java.lang.Integer.compare;

@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class RopeKnot {
    private int x;
    private int y;

    public RopeKnot clone() {
        return new RopeKnot(x, y);
    }

    public void move(Direction dir) {
        switch (dir) {
            case R -> x++;
            case L -> x--;
            case U -> y++;
            case D -> y--;
        }
    }

    public int distance(RopeKnot other) {
        long px = other.getX() - x;
        long py = other.getY() - y;
        return (int) Math.sqrt(px * px + py * py);
    }

    public boolean catchUp(RopeKnot other) {
        if (distance(other) < 2) return false;
        x += compare(other.x - x, 0);
        y += compare(other.y - y, 0);
        return true;
    }

    @Override
    public String toString(){
        return "Row: " + this.y + "; Col: " + this.x;
    }

}
