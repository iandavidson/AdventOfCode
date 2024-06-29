package dev.davidson.ian.advent.year2022.day20;

import java.util.Objects;

public record Element(int originalIndex, long value) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return value == element.value && originalIndex == element.originalIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalIndex, value);
    }

    @Override
    public String toString() {
        return "Element{" +
                "value=" + value +
                '}';
    }
}

/*
public void execute() {
        List<Long> original = readFile();

        Decrypter decrypter = new Decrypter(original, 1);
        log.info("Part1: {}", decrypter.decrypt());

        List<Long> originalWithKey = original.stream().map(val -> val * KEY).toList();
        decrypter = new Decrypter(original, 10);
        log.info("Part2: {}", decrypter.decrypt());
    }
 */
