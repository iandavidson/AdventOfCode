package org.example.advent.year2022.day09;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class Rope {
    private List<RopeKnot> ropeKnots = new ArrayList<>();
    private Set<RopeKnot> tailVisited = new HashSet<>();

    public Rope(final int knots) {
        for (int i = 0; i < knots; i++) {
            ropeKnots.add(new RopeKnot(0, 0));
        }
    }

    private boolean catchUpTail(int tailNo) {
        return this.ropeKnots.get(tailNo).catchUp(this.ropeKnots.get(tailNo - 1));
    }

    public void move(Instruction instruction) {
        for (int i = 0; i < instruction.magnitude(); i++) {
            ropeKnots.get(0).move(instruction.direction());
            for (int j = 1; j < ropeKnots.size(); j++) {
                if (!catchUpTail(j)) break;
            }
            tailVisited.add(ropeKnots.getLast().clone());
        }
    }


}
