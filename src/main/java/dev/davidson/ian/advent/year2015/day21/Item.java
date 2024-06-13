package dev.davidson.ian.advent.year2015.day21;

public record Item(String name, int value, int damage, int armor) implements Comparable<Item> {

    @Override
    public int compareTo(Item other){
        return value - other.value;
    }
}
