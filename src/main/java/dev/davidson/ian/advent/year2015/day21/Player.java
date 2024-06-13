package dev.davidson.ian.advent.year2015.day21;

import java.util.List;

public record Player(int hitPoints, int damage, int armor) {
    public static Player newPlayer(int hitPoints, List<Item> equipped){
        int damage = 0;
        int armor = 0;
        for(Item item : equipped){
            damage += item.damage();
            armor += item.armor();
        }

        return new Player(hitPoints, damage, armor);
    }
}
