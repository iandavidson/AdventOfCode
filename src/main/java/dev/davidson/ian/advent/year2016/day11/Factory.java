package dev.davidson.ian.advent.year2016.day11;

import java.util.ArrayList;
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
    private static final Pattern GENERATOR_PATTERN = Pattern.compile("([a-z]+?)(\\s+generator)");

    @Builder.Default
    private final Integer currentFloor = 1;
    private final Map<Integer, List<String>> chipMap;
    private final Map<Integer, List<String>> genMap;
    private final Integer totalItems;

    public boolean isFinished(){
         return chipMap.get(4).size() + genMap.get(4).size() == totalItems;
    }

    public List<String> availableChips(){
        return chipMap.get(currentFloor);
    }

    public List<String> availableGenerators(){
        return genMap.get(currentFloor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factory factory = (Factory) o;

        return Objects.equals(currentFloor, factory.currentFloor) && Objects.equals(chipMap, factory.chipMap) && Objects.equals(genMap, factory.genMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentFloor, chipMap, genMap);
    }

    public static Factory newFactory(List<String> inputLines){
        Map<Integer, List<String>> chipMap = new HashMap<>();
        Map<Integer, List<String>> genMap = new HashMap<>();
        Integer totalItems = 0;

        for(int i = 0; i < inputLines.size(); i++){
            int floor = i+1;

            chipMap.put(floor, new ArrayList<>());
            genMap.put(floor, new ArrayList<>());

            Matcher chipMatcher = CHIP_PATTERN.matcher(inputLines.get(i));
            while(chipMatcher.find()){
                String chip = chipMatcher.group().split("-")[0];
                chipMap.get(floor).add(chip);
                totalItems++;
            }

            Matcher genMatcher = GENERATOR_PATTERN.matcher(inputLines.get(i));
            while(genMatcher.find()){
                String generator = genMatcher.group().split("\\s+")[0];
                genMap.get(floor).add(generator);
                totalItems++;
            }
        }

        return Factory.builder()
                .chipMap(chipMap)
                .genMap(genMap)
                .totalItems(totalItems)
                .build();
    }

    public Factory makeMove(final int nextFloor, final List<String> movedChips, final List<String> movedGenerators){
        List<String> priorFloorChips = new ArrayList<>(chipMap.get(currentFloor));
        priorFloorChips.removeAll(movedChips);

        List<String> nextFloorChips = new ArrayList<>(chipMap.get(nextFloor));
        nextFloorChips.addAll(movedChips);

        List<String> priorFloorGenerators = new ArrayList<>(genMap.get(currentFloor));
        priorFloorChips.removeAll(movedGenerators);

        List<String> nextFloorGenerators = new ArrayList<>(genMap.get(nextFloor));
        nextFloorChips.addAll(movedGenerators);

        Map<Integer, List<String>> nextChipMap = new HashMap<>();
        for(int i = 1 ; i < 5; i++){
            if(i == nextFloor){
                nextChipMap.put(i, nextFloorChips);
            } else if(i == currentFloor){
                nextChipMap.put(i, priorFloorChips);
            } else {
                nextChipMap.put(i, new ArrayList<>(chipMap.get(i)));
            }
        }

        Map<Integer, List<String>> nextGenMap = new HashMap<>();
        for(int i = 1 ; i < 5; i++){
            if(i == nextFloor){
                nextChipMap.put(i, nextFloorGenerators);
            } else if(i == currentFloor){
                nextChipMap.put(i, priorFloorGenerators);
            } else {
                nextChipMap.put(i, new ArrayList<>(chipMap.get(i)));
            }
        }

        return Factory.builder()
                .currentFloor(nextFloor)
                .totalItems(totalItems)
                .chipMap(nextChipMap)
                .genMap(nextGenMap)
                .build();
    }
}
