package dev.davidson.ian.advent.year2015.day13;

import java.util.Map;
import java.util.Objects;

public record Person(String name, Map<String, Integer> neighborMap) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
