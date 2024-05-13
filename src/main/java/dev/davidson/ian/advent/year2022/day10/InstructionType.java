package dev.davidson.ian.advent.year2022.day10;

public enum InstructionType {
    noop(1), addx(2);

    int cycles;

    InstructionType(int cycles) {
        this.cycles = cycles;
    }

    public Integer getCycles(){
        return this.cycles;
    }
}
