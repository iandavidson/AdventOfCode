package dev.davidson.ian.advent.year2022.day16;

import java.util.List;
import java.util.Objects;

public record Valve(String label, List<String> outs, int flowRate) implements Comparable<Valve> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valve valve = (Valve) o;
        return Objects.equals(label, valve.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public int compareTo(Valve o) {
        return Integer.compare(flowRate, o.flowRate);
    }
}
