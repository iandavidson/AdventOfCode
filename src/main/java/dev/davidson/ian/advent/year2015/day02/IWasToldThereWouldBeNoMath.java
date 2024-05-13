package dev.davidson.ian.advent.year2015.day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IWasToldThereWouldBeNoMath {

    private static final String INPUT_PATH = "adventOfCode/2015/day02/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day02/sample.txt";


    public static void main(String[] args) {
        IWasToldThereWouldBeNoMath iWasToldThereWouldBeNoMath = new IWasToldThereWouldBeNoMath();
        System.out.println(iWasToldThereWouldBeNoMath.part1());
        System.out.println(iWasToldThereWouldBeNoMath.part2());
    }

    public Long part1() {
        List<Present> presents = readFile();

        Long runningSum = 0L;
        for (Present p : presents) {
            //2*l*w + 2*w*h + 2*h*l + lowest.
            runningSum += p.lh() * 2 + p.lw() * 2 + p.wh() * 2 + p.lowestValue();

        }

        return runningSum;
    }

    public Long part2() {
        List<Present> presents = readFile();

        Long runningSum = 0L;
        for (Present p : presents) {
            //2*l*w + 2*w*h + 2*h*l + lowest.
            runningSum += p.minPerim() + p.volume();
        }

        return runningSum;
    }

    private List<Present> readFile() {
        List<Present> presents = new ArrayList<>();

        ClassLoader cl = IWasToldThereWouldBeNoMath.class.getClassLoader();
        File file = new File(cl.getResource(INPUT_PATH).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("x");
                presents.add(Present.newPresent(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return presents;
    }
}
