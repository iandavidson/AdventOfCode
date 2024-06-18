package dev.davidson.ian.advent.year2015.day22;

import dev.davidson.ian.advent.year2015.day22.spell.Drain;
import dev.davidson.ian.advent.year2015.day22.spell.MagicMissle;
import dev.davidson.ian.advent.year2015.day22.spell.Poison;
import dev.davidson.ian.advent.year2015.day22.spell.Recharge;
import dev.davidson.ian.advent.year2015.day22.spell.Shield;
import dev.davidson.ian.advent.year2015.day22.spell.Spell;
import dev.davidson.ian.advent.year2015.day22.state.GameState;
import dev.davidson.ian.advent.year2015.day22.state.SpellEffect;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class WizardSimulator {

    private static final int ENEMY_HEALTH = 58;
    private static final int ENEMY_DAMAGE = 9;

    private static final MagicMissle MAGIC_MISSLE = new MagicMissle();
    private static final Drain DRAIN = new Drain();
    private static final Poison POISON = new Poison();
    private static final Shield SHIELD = new Shield();
    private static final Recharge RECHARGE = new Recharge();
    private final List<Spell> SPELLS = new ArrayList<>();

    public static void main(String[] args) {
        WizardSimulator wizardSimulator = new WizardSimulator();
        log.info("Part1: {}", wizardSimulator.part1());
    }

    public int part1() {
        Player player = new Player(50, 500);
        GameState gameState = new GameState();
        SPELLS.add(MAGIC_MISSLE);
        SPELLS.add(DRAIN);
        SPELLS.add(POISON);
        SPELLS.add(SHIELD);
        SPELLS.add(RECHARGE);

        int min = Integer.MAX_VALUE;
        for (Spell spell : SPELLS) {
            min = Math.min(min, part1Helper(gameState, ENEMY_HEALTH, player, spell, 0));
        }

        return min;
    }

    private int part1Helper(final GameState gameState, int enemyHealth, final Player player, final Spell currentSpell, final int turn) {

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

            //this will decrement the duration remaining on each spell
            spellEffect.applyEndOfRound();
        }

        //apply current effects on player and enemy
        player.addMana(currentManaRegen);
        player.regenHitPoints(currentHealthRegen);

        enemyHealth -= currentDamageLinger;

        //this will evict spells that now have 0 duration (after we have applied for turn)
        gameState.removeInactiveSpells();


        if (enemyHealth <= 0) {
            return 0;
        }

        if (turn % 2 == 0) {
            int manaConsumed;
            //players turn
            switch (currentSpell) {
                case Drain drain -> {
                    manaConsumed = drain.getManaDrain();
                    player.useMana(drain.getManaDrain());
                    enemyHealth -= drain.getDamage();
                    player.setHitPoints(drain.getHealthRegen());
                }
                case MagicMissle magicMissle -> {
                    manaConsumed = magicMissle.getManaDrain();
                    player.useMana(magicMissle.getManaDrain());
                    enemyHealth -= magicMissle.getDamage();

                }
                case Poison poison -> {
                    manaConsumed = poison.getManaDrain();
                    player.useMana(poison.getManaDrain());
                    gameState.getSpellEffects().add(new SpellEffect(poison, poison.getDuration()));

                }
                case Shield shield -> {
                    manaConsumed = shield.getManaDrain();
                    player.useMana(shield.getManaDrain());
                    gameState.getSpellEffects().add(new SpellEffect(shield, shield.getDuration()));

                }
                case Recharge recharge -> {
                    manaConsumed = recharge.getManaDrain();
                    player.useMana(recharge.getManaDrain());
                    gameState.getSpellEffects().add(new SpellEffect(recharge, recharge.getDuration()));
                }
                default -> throw new IllegalStateException("Unexpected value: " + currentSpell);
            }

            if (enemyHealth <= 0) {
                return 0;
            }

            Collections.shuffle(SPELLS);

            int min = Integer.MAX_VALUE;
            for (Spell spell : SPELLS) {

                if (!gameState.isMember(spell) && spell.canCast(player.getMana())) {
                    if(!(spell instanceof Recharge)){
//                        if(spell.getManaDrain())
                        int i = 0;
                    }

                    int consumedLater = part1Helper(gameState.copy(), enemyHealth, new Player(player.getHitPoints(), player.getMana()), spell, turn + 1);

                    if (consumedLater != Integer.MAX_VALUE) {
                        min = Math.min(min, manaConsumed + consumedLater);
                    }
                }
            }

            return min;
        } else {
            //bosses turn
            player.attacked(ENEMY_DAMAGE, currentArmor);

            if (player.getHitPoints() <= 0) {
                return Integer.MAX_VALUE;
            }

            return part1Helper(gameState, enemyHealth, player, currentSpell, turn + 1);
        }
    }

    /*
    boss:
    Hit Points: 58
    Damage: 9

    Magic Missile costs 53 mana. It instantly does 4 damage.
    Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
    Shield costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is increased by 7.
    Poison costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it is active, it deals the boss 3 damage.
    Recharge costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it is active, it gives you 101 new mana.
     */
}
