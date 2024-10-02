package dev.davidson.ian.advent.year2017.day24;


import java.util.List;
import java.util.Objects;

public record BridgeComponent(List<Integer> ports) {

    public int strength(){
        return ports.getFirst() + ports.get(1);
    }

    public boolean contains(int check){
        return ports.contains(check);
    }

    public boolean isSymmetric(){
        return Objects.equals(ports.getFirst(), ports.get(1));
    }
}
