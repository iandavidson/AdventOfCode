package dev.davidson.ian.advent.year2015.day13;

public record Perception(String name, int score) implements Comparable<Perception> {

    @Override
    public int compareTo(Perception other){
        //sort from high to low
        return other.score - this.score;
    }
}
