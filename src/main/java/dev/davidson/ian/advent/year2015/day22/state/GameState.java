package dev.davidson.ian.advent.year2015.day22.state;

import dev.davidson.ian.advent.year2015.day22.spell.Spell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameState {
    private Set<SpellEffect> spellEffects = new HashSet<>();

    public GameState copy(){
        Set<SpellEffect> newSpellEffects = new HashSet<>();
        for(SpellEffect spellEffect : spellEffects){
            newSpellEffects.add(spellEffect.copy());
        }

        return new GameState(newSpellEffects);
    }


    public boolean isMember(Spell spell) {
        for (SpellEffect spellState : spellEffects) {
            if (spellState.getSpell().equals(spell)) {
                return true;
            }
        }

        return false;
    }

    public void endTurn() {
        List<SpellEffect> toBeRemoved = new ArrayList<>();
        for (SpellEffect spellState : spellEffects) {

            //this will decrement the duration remaining on each spell
            spellState.applyEndOfTurn();

            if (spellState.getDuration() < 1) {
                toBeRemoved.add(spellState);
            }
        }

        toBeRemoved.forEach(spellEffects::remove);
    }


}
