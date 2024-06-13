package dev.davidson.ian.advent.year2015.day21;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class Loadout {

    private final List<Item> equipped = new ArrayList<>();

    public void addToEquipped(Map<ItemType, List<Item>> shop, ItemType itemType, int index) {
        if (index < 0) {
            return;
        }

        equipped.add(shop.get(itemType).get(index));
    }

    public void removeFromEquipped(Map<ItemType, List<Item>> shop, ItemType itemType, int index) {
        if (index < 0) {
            return;
        }

        equipped.remove(shop.get(itemType).get(index));
    }

}
