package dev.davidson.ian.advent.year2015.day21;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class RPGSimulator {

    private static final String SHOP_PATH = "adventOfCode/2015/day21/shop.txt";
    private static final int PLAYER_HIT_POINTS = 100;

    public static void main(String[] args) {
        RPGSimulator rpgSimulator = new RPGSimulator();
        log.info("Part1: {}", rpgSimulator.part1());
    }

    public int part1() {
        Map<ItemType, List<Item>> shop = readShop();
        Player enemy = new Player(103, 9, 2);

        int minCost = Integer.MAX_VALUE;

        //pick 1 weapon
        for (int weapon = 0; weapon < shop.get(ItemType.Weapons).size(); weapon++) {
            List<Item> equipped = new ArrayList<>();
            addToEquipped(shop, ItemType.Weapons, weapon, equipped);

            for (int armor = -1; armor < shop.get(ItemType.Armor).size(); armor++) {
                addToEquipped(shop, ItemType.Armor, armor, equipped);

                for (int ring1 = -2; ring1 < shop.get(ItemType.Rings).size(); ring1++) {
                    addToEquipped(shop, ItemType.Rings, ring1, equipped);

                    for (int ring2 = ring1 + 1; ring2 < shop.get(ItemType.Rings).size(); ring2++) {
                        addToEquipped(shop, ItemType.Rings, ring2, equipped);

                        int playerGearCost = equipped.stream().mapToInt(Item::value).sum();
                        if (playerGearCost < minCost && battle(equipped, enemy)) {
                            minCost = playerGearCost;
                        }

                        removeFromEquipped(shop, ItemType.Rings, ring2, equipped);

                    }
                    removeFromEquipped(shop, ItemType.Rings, ring1, equipped);

                }
                removeFromEquipped(shop, ItemType.Armor, armor, equipped);
            }
        }

        return minCost;
    }

    private boolean battle(List<Item> equipped, Player enemy) {
        return battle(Player.newPlayer(PLAYER_HIT_POINTS, equipped), enemy);
    }

    private boolean battle(Player player, Player enemy) {
        int playerHealth = player.hitPoints();
        int enemyHealth = enemy.hitPoints();

        int i = 0;
        while (playerHealth > 0 && enemyHealth > 0) {
            if (i % 2 == 0) {
                //player turn
                enemyHealth = enemyHealth - Math.max(1, player.damage() - enemy.armor());
            } else {
//                enemy turn
                playerHealth = playerHealth - Math.max(1, enemy.damage() - player.armor());
            }
            i++;
        }

        return playerHealth > 0;
    }

    private void addToEquipped(Map<ItemType, List<Item>> shop, ItemType itemType, int index, List<Item> equipped) {
        if (index < 0) {
            return;
        }

        equipped.add(shop.get(itemType).get(index));
    }

    private void removeFromEquipped(Map<ItemType, List<Item>> shop, ItemType itemType, int index, List<Item> equipped) {
        if (index < 0) {
            return;
        }

        equipped.remove(shop.get(itemType).get(index));
    }

    private Map<ItemType, List<Item>> readShop() {
        List<List<String>> inputs = new ArrayList<>();

        ClassLoader cl = RPGSimulator.class.getClassLoader();
        File file = new File(cl.getResource(SHOP_PATH).getFile());
        try {
            Scanner scanner = new Scanner(file);
            List<String> tempList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    inputs.add(tempList);
                    tempList = new ArrayList<>();
                } else {
                    tempList.add(line);
                }
            }
            inputs.add(tempList);

        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        Map<ItemType, List<Item>> map = new HashMap<>();

        for (List<String> catagory : inputs) {

            String type = catagory.getFirst().split("\\s+")[0];
            ItemType itemType = ItemType.valueOf(type.substring(0, type.length() - 1));
            List<Item> items = new ArrayList<>();
            for (int i = 1; i < catagory.size(); i++) {
                String[] parts = catagory.get(i).split("\\s+");
                items.add(new Item(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
            }
            Collections.sort(items);
            map.put(itemType, items);
        }

        return map;
    }



    /*
Weapons:    Cost  Damage  Armor
Dagger        8     4       0
Shortsword   10     5       0
Warhammer    25     6       0
Longsword    40     7       0
Greataxe     74     8       0

Armor:      Cost  Damage  Armor
Leather      13     0       1
Chainmail    31     0       2
Splintmail   53     0       3
Bandedmail   75     0       4
Platemail   102     0       5

Rings:      Cost  Damage  Armor
Damage +1    25     1       0
Damage +2    50     2       0
Damage +3   100     3       0
Defense +1   20     0       1
Defense +2   40     0       2
Defense +3   80     0       3

Hit Points: 103
Damage: 9
Armor: 2

     */
}
