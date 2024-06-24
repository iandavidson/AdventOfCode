package dev.davidson.ian.advent.year2015.day22;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Player {

    private int hitPoints;
    private int mana;


    public void addMana(int gain){
        this.mana += gain;
    }

    public void useMana(int loss){
        this.mana -= loss;
    }

    public void regenHitPoints(int gain){
        this.hitPoints += gain;
    }

    public void attacked(int loss, int armor){
        this.hitPoints -= Math.max(1, loss - armor);
    }

    @Override
    public String toString(){
        return "hitPoints: " + hitPoints + " mana: " + mana;
    }

}
