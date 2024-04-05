package org.example.advent.year2022.day03;

import lombok.Getter;

import java.util.List;

@Getter
public class ElfCrew {

    private final List<Rucksack> elfs;
    private final Character badgeCharacter;

    private ElfCrew(final List<Rucksack> elfs, final Character badgeCharacter) {
        this.elfs = elfs;
        this.badgeCharacter = badgeCharacter;
    }

    public static ElfCrew newElfCrew(final List<Rucksack> rucksacks) {
        Character badge = null;
        for (Character ch : rucksacks.get(0).getFullInput().toCharArray()) {
            if (rucksacks.get(1).getFullInput().contains(ch.toString()) && rucksacks.get(2).getFullInput().contains(ch.toString())) {
                badge = ch;
            }
        }

        return new ElfCrew(rucksacks, badge);
    }

}
