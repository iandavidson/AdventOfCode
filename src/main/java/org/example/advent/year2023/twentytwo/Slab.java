package org.example.advent.year2023.twentytwo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Slab implements Comparable<Slab> {
    private final Coordinate start;
    private final Coordinate end;


    public void fall(){
        start.fall();
        end.fall();
    }
    public boolean willCollideAfterFall(Slab other){
        boolean xIsPositive = Math.min(this.end.getX(), other.end.getX()) > Math.max(this.start.getX(), other.start.getX());
        boolean yIsPositive = Math.min(this.end.getY(), other.end.getY()) > Math.max(this.start.getY(), other.start.getY());
        boolean zIsPositive = Math.min(this.end.getZ()-1, other.end.getZ()) > Math.max(this.start.getZ()-1, other.start.getZ());
        return ( xIsPositive && yIsPositive && zIsPositive);
    }

    @Override
    public int compareTo(Slab other){
        return this.start.compareTo(other.getStart());
    }
}
