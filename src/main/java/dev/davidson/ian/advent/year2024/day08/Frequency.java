package dev.davidson.ian.advent.year2024.day08;

public record Frequency(Coordinate coordinate, Character channel) implements Comparable<Frequency> {
    @Override
    public int compareTo(Frequency o) {
        return channel - o.channel;
    }
}
