package org.example.advent.year2022.day09;

public record Instruction(Direction direction, Integer magnitude) {
    @Override
    public String toString(){
        return direction.name() + " " + magnitude;
    }
}
