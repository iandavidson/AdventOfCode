package dev.davidson.ian.advent.year2015.day24;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ItHangsInTheBalance {

    private static final String INPUT_PATH = "adventOfCode/2015/day24/input.txt";


    public static void main(String[] args) {
        ItHangsInTheBalance itHangsInTheBalance = new ItHangsInTheBalance();
        log.info("Part1: {}", itHangsInTheBalance.execute(3));
        log.info("Part2: {}", itHangsInTheBalance.execute(4));
    }

    private Long execute(final Integer groups) {
        List<Integer> weights = readFile();

        long target = weights.stream().mapToInt(Integer::intValue).sum() / groups;
        int shortestGroup = Integer.MAX_VALUE;

        List<PresentGroup> results = new ArrayList<>();
        List<PresentGroup> list = new ArrayList<>();
        list.add(new PresentGroup());

        for (Integer present : weights) {
            List<PresentGroup> tempGroups = new ArrayList<>();
            for (PresentGroup presentGroup : list) {

                //attempt to prune executions of giant subset tree being created
                if (presentGroup.getPresents().size() >= shortestGroup) {
                    continue;
                }

                if (presentGroup.sumIfPresentAdded(present) <= target) {
                    PresentGroup clone = presentGroup.clone();
                    clone.addPresent(present);
                    tempGroups.add(clone);

                    if (clone.getSum() == target) {
                        shortestGroup = Math.min(shortestGroup, clone.getPresents().size());
                        results.add(clone);
                    }

                }
            }
            list.addAll(tempGroups);
        }


        //prefer the smallest size of presents
        //prefer the lowest product value
        results.sort((a, b) -> {
            int aSize = a.getPresents().size();
            int bSize = b.getPresents().size();
            if (aSize == bSize) {
                return Long.signum(a.getProduct() - b.getProduct());
            }

            return aSize - bSize;
        });

        return results.getFirst().getProduct();
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
