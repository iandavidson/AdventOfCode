package dev.davidson.ian.advent.year2015.day22.state;

import dev.davidson.ian.advent.year2015.day22.spell.Spell;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@AllArgsConstructor
@Data
public class SpellEffect {

    private final Spell spell;
    private int duration = 0;

    public static SpellEffect toSpellEffect(final Spell spell){
        return new SpellEffect(spell, spell.getDuration());
    }

    public SpellEffect copy(){
        return new SpellEffect(spell, duration);
    }

    public void applyEndOfTurn(){
        this.duration--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellEffect that = (SpellEffect) o;
        return Objects.equals(spell, that.spell);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(spell);
    }
}
