package dev.davidson.ian.advent.year2015.day22;

import dev.davidson.ian.advent.year2015.day22.spell.Drain;
import dev.davidson.ian.advent.year2015.day22.spell.MagicMissile;
import dev.davidson.ian.advent.year2015.day22.spell.Poison;
import dev.davidson.ian.advent.year2015.day22.spell.Recharge;
import dev.davidson.ian.advent.year2015.day22.spell.Shield;
import dev.davidson.ian.advent.year2015.day22.spell.Spell;
import dev.davidson.ian.advent.year2015.day22.state.GameState;
import dev.davidson.ian.advent.year2015.day22.state.SpellEffect;
import dev.davidson.ian.advent.year2015.day22.state.StatEffects;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class WizardSimulator {

    private static final int ENEMY_HEALTH = 58;
    private static final int ENEMY_DAMAGE = 9;
    private static final int PLAYER_HIT_POINTS = 50;
    private static final int PLAYER_MANA_POINTS = 500;

    private static final MagicMissile MAGIC_MISSILE = new MagicMissile();
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
        Player player = new Player(PLAYER_HIT_POINTS, PLAYER_MANA_POINTS);
        GameState gameState = new GameState();

        //todo: hack
        SPELLS.add(MAGIC_MISSILE);
        SPELLS.add(DRAIN);
        SPELLS.add(POISON);
        SPELLS.add(SHIELD);
        SPELLS.add(RECHARGE);

        int min = Integer.MAX_VALUE;
        for (Spell spell : SPELLS) {
            min = Math.min(min, battle(gameState, ENEMY_HEALTH, player, spell, 0));
        }

        //1119 too low
        //??? 1255, 1415, 1481
        //1461 too high
        return min;
    }

    private int battle(final GameState gameState, int enemyHealth, final Player player, final Spell currentSpell, final int turn) {
        StatEffects statEffects = StatEffects.newStatEffects(gameState);

        //apply current effects on player and enemy
        player.addMana(statEffects.getManaRegen());
        player.regenHitPoints(statEffects.getHealthRegen());
        enemyHealth -= statEffects.getDamageLinger();

        //this will decrement the duration on spells by 1; if they now have 0 duration we will evict
        gameState.endTurn();


        if (enemyHealth <= 0) {
            return 0;
        }

        if (turn % 2 == 0) {
            int manaConsumedNow = currentSpell.getManaDrain();
            player.useMana(currentSpell.getManaDrain());
            //players turn


            switch (currentSpell) {
                case Drain drain -> {
                    enemyHealth -= drain.getDamage();
                    player.regenHitPoints(drain.getHealthRegen());

                }
                case MagicMissile magicMissile -> {
                    enemyHealth -= magicMissile.getDamage();

                }
                case Poison poison -> {
                    gameState.getSpellEffects().add(SpellEffect.toSpellEffect(poison));

                }
                case Shield shield -> {
                    gameState.getSpellEffects().add(SpellEffect.toSpellEffect(shield));

                }
                case Recharge recharge -> {
                    gameState.getSpellEffects().add(SpellEffect.toSpellEffect(recharge));

                }
                default -> throw new IllegalStateException("Unexpected value: " + currentSpell);
            }

//            log.info("turn:{}; enemyHealth:{}; {}; {}; casted:{}", turn, enemyHealth, player, statEffects, currentSpell.getName());

            if(player.getMana() < 0 ){
                return Integer.MAX_VALUE;
            }

            if (enemyHealth <= 0) {
                return manaConsumedNow;
            }

            //todo: hack
            Collections.shuffle(SPELLS);

            int min = Integer.MAX_VALUE;

            if(player.getMana() < 150){
                int i = 0;
            }

            for (Spell spell : SPELLS) {

                //if (!gameState.isMember(spell) && spell.canCast(player.getMana())) {
                if (!gameState.isMember(spell)) {

                    int consumedInFuture = battle(gameState.copy(), enemyHealth,
                            new Player(player.getHitPoints(), player.getMana()), spell, turn + 1);

                    if (consumedInFuture != Integer.MAX_VALUE) {
                        min = Math.min(min, manaConsumedNow + consumedInFuture);
                    }
                }
            }

            return min;
        } else {
            //bosses turn
            player.attacked(ENEMY_DAMAGE, statEffects.getArmor());

            if (player.getHitPoints() <= 0) {
                return Integer.MAX_VALUE;
            }

            return battle(gameState, enemyHealth, player, currentSpell, turn + 1);
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
