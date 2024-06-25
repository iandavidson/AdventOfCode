package dev.davidson.ian.advent.year2015.day24;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ItHangsInTheBalance {

    private static final String INPUT_PATH = "adventOfCode/2015/day24/input.txt";


    public static void main(String[] args) {
        ItHangsInTheBalance itHangsInTheBalance = new ItHangsInTheBalance();
        log.info("Part1: {}", itHangsInTheBalance.part1());
    }

    private long part1() {
        List<Integer> weights = readFile();
        weights.sort(Comparator.reverseOrder());

        long tempSum = weights.stream().mapToInt(Integer::intValue).sum();
        long target = tempSum / 3;

        int shortestGroup = Integer.MAX_VALUE;

        List<PresentGroup> result = new ArrayList<>();
        List<PresentGroup> list = new ArrayList<>();
        list.add(new PresentGroup());

        for (Integer present : weights) {
            List<PresentGroup> tempGroups = new ArrayList<>();
            for(PresentGroup presentGroup : list){

                //attempt to prune executions of giant subset tree being created
                if(presentGroup.getPresents().size() >= shortestGroup){
                    continue;
                }

                if(presentGroup.sumIfPresentAdded(present) <= target){
                    PresentGroup clone = presentGroup.clone();
                    clone.addPresent(present);
                    tempGroups.add(clone);

                    if(clone.getSum() == target){
                        shortestGroup = Math.min(shortestGroup, clone.getPresents().size());
                        result.add(clone);
                    }

                }
            }
            list.addAll(tempGroups);
        }

        //find all groupings that add to tempSum/3
        //then only take the ones with the smallest size
        //then take the one with the lowest product
        result.sort((a, b) -> {
           int aSize = a.getPresents().size();
           int bSize = b.getPresents().size();
           if(aSize == bSize){
               return Long.signum(a.getProduct() - b.getProduct());
           }

           return aSize - bSize;
        });

        return result.getFirst().getProduct();
    }

    private List<Integer> readFile() {
        List<Integer> weights = new ArrayList<>();
        ClassLoader cl = ItHangsInTheBalance.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                weights.add(Integer.parseInt(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        return weights;
    }
}
