package dev.davidson.ian.advent.year2015.day22;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class WizardSimulator {


    public static void main(String [] args){
        WizardSimulator wizardSimulator = new WizardSimulator();
        log.info("Part1: {}", wizardSimulator.part1());
    }

    public int part1(){
        Enemy enemy = new Enemy(58, 9);
        Player player = new Player(50, new ArrayList<>(), 500);

        //think of it like depth first search or backtracking via using the lowest mana cost spells to highest mana spells

        //if we can hit all paths, then throw away cases after we already have a winning battle where less mana was used than current
        // I think that will be computationally feasible

        return -1;
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
