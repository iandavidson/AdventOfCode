package dev.davidson.ian.advent.year2015.day21;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class RPGSimulator {

    private static final String SHOP_PATH = "adventOfCode/2015/day21/shop.txt";
    private static final int PLAYER_HIT_POINTS = 100;

    public static void main(String[] args) {
        RPGSimulator rpgSimulator = new RPGSimulator();
        rpgSimulator.execute();
    }

    public void execute() {
        Map<ItemType, List<Item>> shop = readShop();
        Player enemy = new Player(103, 9, 2);

        int minWinCost = Integer.MAX_VALUE; //part1
        int maxLossCost = Integer.MIN_VALUE; //part2

        Loadout loadout = new Loadout();
        for (int weapon = 0; weapon < shop.get(ItemType.Weapons).size(); weapon++) {
            loadout.addToEquipped(shop, ItemType.Weapons, weapon);

            for (int armor = -1; armor < shop.get(ItemType.Armor).size(); armor++) {
                loadout.addToEquipped(shop, ItemType.Armor, armor);

                for (int ring1 = -2; ring1 < shop.get(ItemType.Rings).size(); ring1++) {
                    loadout.addToEquipped(shop, ItemType.Rings, ring1);

                    for (int ring2 = ring1 + 1; ring2 < shop.get(ItemType.Rings).size(); ring2++) {
                        loadout.addToEquipped(shop, ItemType.Rings, ring2);

                        int playerGearCost = loadout.getEquipped().stream().mapToInt(Item::value).sum();
                        if (battle(loadout, enemy)) {
                            minWinCost = Math.min(minWinCost, playerGearCost);
                        } else {
                            maxLossCost = Math.max(maxLossCost, playerGearCost);
                        }

                        loadout.removeFromEquipped(shop, ItemType.Rings, ring2);
                    }
                    loadout.removeFromEquipped(shop, ItemType.Rings, ring1);
                }
                loadout.removeFromEquipped(shop, ItemType.Armor, armor);
            }
            loadout.removeFromEquipped(shop, ItemType.Weapons, weapon);
        }

        log.info("Part1: {}", minWinCost);
        log.info("Part2: {}", maxLossCost);
    }

    private boolean battle(Loadout loadout, Player enemy) {
        return battle(Player.newPlayer(PLAYER_HIT_POINTS, loadout.getEquipped()), enemy);
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
                //enemy turn
                playerHealth = playerHealth - Math.max(1, enemy.damage() - player.armor());
            }
            i++;
        }

        return playerHealth > 0;
    }

    private Map<ItemType, List<Item>> readShop() {
        List<List<String>> inputs = getInputLists();

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

    private static List<List<String>> getInputLists() {
        List<List<String>> inputs = new ArrayList<>();

        ClassLoader cl = RPGSimulator.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SHOP_PATH)).getFile());
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
        return inputs;
    }
}
