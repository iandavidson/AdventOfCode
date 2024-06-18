package dev.davidson.ian.advent.year2015.day22.spell;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public abstract class Spell {

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

/*
    Magic Missile costs 53 mana. It instantly does 4 damage.
    Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
    Shield costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is increased by 7.
    Poison costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it is active, it deals the boss 3 damage.
    Recharge costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it is active, it gives you 101 new mana.
 */
}
