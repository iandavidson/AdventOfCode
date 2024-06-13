package dev.davidson.ian.advent.year2015.day22;

import dev.davidson.ian.advent.year2015.day22.spell.Spell;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Player {

    private int hitPoints;
    private List<Spell> activeSpells;
    private int mana;


    public void endOfRound(){
        List<Spell> toBeRemoved = new ArrayList<>();
        for(Spell spell : activeSpells){
            spell.applyTurn();
            if(!spell.isActive()){
                toBeRemoved.add(spell);
            }
        }

        activeSpells.removeAll(toBeRemoved);
    }
}
