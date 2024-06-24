package dev.davidson.ian.advent.year2015.day22.spell;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public abstract class Spell {

    private final String name;
    private final int manaDrain;
    private final int damage;
    private final int duration;
    private final int manaRegen;
    private final int healthRegen;
    private final int damageLinger;
    private final int armorBuff;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spell spell = (Spell) o;
        return manaDrain == spell.manaDrain;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(manaDrain);
    }

    public boolean canCast(int remainingMana){
        return (remainingMana - manaDrain) >= 0;
    }
}
