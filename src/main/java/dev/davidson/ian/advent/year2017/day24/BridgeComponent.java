package dev.davidson.ian.advent.year2017.day24;


import java.util.List;

public record BridgeComponent(List<Integer> ports) {

    public int strength(){
        return ports.getFirst() + ports.get(1);
    }

    public boolean contains(int check){
        return ports.contains(check);
    }
}
