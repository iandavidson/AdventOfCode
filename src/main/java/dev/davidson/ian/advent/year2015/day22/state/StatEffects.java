package dev.davidson.ian.advent.year2015.day22.state;

import lombok.Getter;

@Getter
public class StatEffects {

    private final int manaRegen;
    private final int healthRegen;
    private final int damageLinger;
    private final int armor;

    private StatEffects(int manaRegen, int healthRegen, int damageLinger, int armor) {
        this.manaRegen = manaRegen;
        this.healthRegen = healthRegen;
        this.damageLinger = damageLinger;
        this.armor = armor;
    }

    public static StatEffects newStatEffects(final GameState gameState) {
        int currentManaRegen = 0;
        int currentHealthRegen = 0;
        int currentDamageLinger = 0;
        int currentArmor = 0;
        //determine current effects
        for (SpellEffect spellEffect : gameState.getSpellEffects()) {
            currentManaRegen += spellEffect.getSpell().getManaRegen();
            currentHealthRegen += spellEffect.getSpell().getHealthRegen();
            currentDamageLinger += spellEffect.getSpell().getDamageLinger();
            currentArmor += spellEffect.getSpell().getArmorBuff();
        }

        return new StatEffects(currentManaRegen, currentHealthRegen, currentDamageLinger, currentArmor);
    }

    @Override
    public String toString() {
        return "manaRegen: " + manaRegen + " healthRegen: " + healthRegen +
                " damageLinger: " + damageLinger + " armor: " + armor;
    }
}
