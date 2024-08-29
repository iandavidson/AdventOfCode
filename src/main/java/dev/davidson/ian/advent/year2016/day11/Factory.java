package dev.davidson.ian.advent.year2016.day11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Factory {

    private static final Pattern CHIP_PATTERN = Pattern.compile("([a-z]+?)(-compatible)");
    private static final String CHIP_SUFFIX = "-chip";
    private static final Pattern GENERATOR_PATTERN = Pattern.compile("([a-z]+?)(\\s+generator)");
    private static final String GENERATOR_SUFFIX = "-gen";

    @Builder.Default
    private final Integer currentFloor = 1;
    private final Map<Integer, List<String>> floorMap;
    private final Integer totalItems;

    public static Factory newFactory(List<String> inputLines) {
        Map<Integer, List<String>> floorMap = new HashMap<>();
        Integer totalItems = 0;

        for (int i = 0; i < inputLines.size(); i++) {
            int floor = i + 1;

            floorMap.put(floor, new ArrayList<>());

            Matcher chipMatcher = CHIP_PATTERN.matcher(inputLines.get(i));
            while (chipMatcher.find()) {
                String chip = chipMatcher.group().split("-")[0];
                floorMap.get(floor).add(chip + CHIP_SUFFIX);
                totalItems++;
            }

            Matcher genMatcher = GENERATOR_PATTERN.matcher(inputLines.get(i));
            while (genMatcher.find()) {
                String generator = genMatcher.group().split("\\s+")[0];
                floorMap.get(floor).add(generator + GENERATOR_SUFFIX);
                totalItems++;
            }
        }

        return Factory.builder()
                .floorMap(floorMap)
                .totalItems(totalItems)
                .build();
    }

    public boolean isValid() {
        for (int i = 1; i < 5; i++) {
            List<String> chips = chipsAtFloor(i);
            List<String> gens = generatorsAtFloor(i);

            if (gens.isEmpty()) {
                continue;
            }

            //we know the there are at least 1 generator on floor
            for (String chip : chips) {
                String chipId = chip.substring(0, chip.indexOf('-'));
                if (gens.stream().noneMatch(gen -> gen.startsWith(chipId))) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isFinished() {
        return floorMap.get(4).size() == totalItems;
    }

    public List<String> getCurrentFloorItems() {
        return floorMap.get(currentFloor);
    }

    public List<String> availableChips() {
        return chipsAtFloor(currentFloor);
    }

    public List<String> chipsAtFloor(final int floor) {
        return floorMap.get(floor).stream().filter(name -> name.endsWith(CHIP_SUFFIX)).toList();
    }

    public List<String> availableGenerators() {
        return generatorsAtFloor(currentFloor);
    }

    public List<String> generatorsAtFloor(final int floor) {
        return floorMap.get(floor).stream().filter(name -> name.endsWith(GENERATOR_SUFFIX)).toList();
    }

    public List<Factory> findValidMoves(final Factory current) {
        List<Factory> validMoves = new ArrayList<>();
        validMoves.addAll(findValidMovesAtFloor(current, current.getCurrentFloor() + 1));
        validMoves.addAll(findValidMovesAtFloor(current, current.getCurrentFloor() - 1));
        return validMoves;
    }

    private List<Factory> findValidMovesAtFloor(final Factory current, final int proposedFloor) {
        if (proposedFloor == 0 || proposedFloor == 5) {
            return Collections.emptyList();
        }

        List<Factory> factories = new ArrayList<>();

        List<String> chipCandidates = current.availableChips();
        List<String> genCandidates = current.availableGenerators();
        for (String chipCandidate : chipCandidates) {
            factories.add(current.makeMove(proposedFloor, List.of(chipCandidate)));
        }

        for (String genCandidate : genCandidates) {
            factories.add(current.makeMove(proposedFloor, List.of(genCandidate)));
        }

        int n = current.getCurrentFloorItems().size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                factories.add(
                        current.makeMove(
                                proposedFloor,
                                List.of(current.getCurrentFloorItems().get(i), current.getCurrentFloorItems().get(j))));
            }
        }

        return factories.stream().filter(Factory::isValid).toList();
    }

    public Factory makeMove(final int nextFloor, final List<String> movedItems) {
        Map<Integer, List<String>> nextFloorMap = new HashMap<>();

        for (int i = 1; i < 5; i++) {
            if (i == currentFloor) {
                //copy from this, remove "moved items"
                List<String> tempList = new ArrayList<>(floorMap.get(currentFloor));
                tempList.removeAll(movedItems);
                Collections.sort(tempList);
                nextFloorMap.put(currentFloor, tempList);

            } else if (i == nextFloor) {
                //copy from this, add "moved Items"
                List<String> tempList = new ArrayList<>(floorMap.get(nextFloor));
                tempList.addAll(movedItems);
                Collections.sort(tempList);
                nextFloorMap.put(nextFloor, tempList);

            } else {
                //copy from this
                List<String> tempList = new ArrayList<>(floorMap.get(i));
                Collections.sort(tempList);
                nextFloorMap.put(i, tempList);
            }
        }

        return Factory.builder()
                .floorMap(nextFloorMap)
                .totalItems(totalItems)
                .currentFloor(nextFloor)
                .build();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factory factory = (Factory) o;

        return Objects.equals(currentFloor, factory.currentFloor) && Objects.equals(floorMap, factory.floorMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentFloor, floorMap);
    }
}
