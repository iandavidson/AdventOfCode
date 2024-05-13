package dev.davidson.ian.advent.year2022.day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GrovePositioningSystem {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day20/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day20/input.txt";


    public static void main(String[] args) {
        GrovePositioningSystem grovePositioningSystem = new GrovePositioningSystem();
        System.out.println("part1: " + grovePositioningSystem.part1());
    }

    public long part1() {
        List<Integer> og = readFile();
        List<Integer> workingList = new ArrayList<>(og);

        processPart1(og, workingList);

        int index = workingList.indexOf(0);

        int one = workingList.get((index + 1000) % workingList.size());
        int two = workingList.get((index + 2000) % workingList.size());
        int three = workingList.get((index + 3000) % workingList.size());

        System.out.println("one: " + one + " two: " + two + " three: " + three);

        //7462 too high
        return one + two + three;
    }

    private List<Integer> readFile(){
        List<Integer> inputs = new ArrayList<>();

        ClassLoader cl = GrovePositioningSystem.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                inputs.add(Integer.parseInt(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return inputs;
    }

    private void processPart1(final List<Integer> og, final List<Integer> workingList) {

        for (Integer element : og) {
            System.out.println(workingList);

            int index = workingList.indexOf(element);
            int valueShift = element % (og.size() - 1);
            int newIndex = index + valueShift;

            if (newIndex >= og.size()) {
                // add 1 to the new index if we are _ON_ the last index or outside of upper
                // bounds
                newIndex = newIndex - og.size() + 1;
            } else if (newIndex <= 0) {
                // subtract 1 from the new index if we are
                // on the first index or out of lower bounds
                newIndex = newIndex + og.size() - 1;
            }

            //remove from list
            workingList.remove(element);

            //add at new index
            workingList.add(newIndex, element);
        }

        System.out.println(workingList);
    }
}
