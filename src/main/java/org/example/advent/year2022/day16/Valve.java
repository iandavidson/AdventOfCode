package org.example.advent.year2022.day16;

import java.util.List;
import java.util.Objects;

public record Valve(String label, List<String> out, int flowRate) {
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
}
