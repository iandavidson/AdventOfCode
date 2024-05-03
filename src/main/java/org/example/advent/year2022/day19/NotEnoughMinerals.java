package org.example.advent.year2022.day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class NotEnoughMinerals {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day19/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day19/input.txt";

    public static void main(String [] args){
        NotEnoughMinerals notEnoughMinerals = new NotEnoughMinerals();
        System.out.println("Part1 : " + notEnoughMinerals.part1());
    }

    public long part1(){
        List<Blueprint> blueprints = readFile();
        return 0;
    }

    private List<Blueprint> readFile(){
        List<Blueprint> blueprints = new ArrayList<>();

        ClassLoader cl = NotEnoughMinerals.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                blueprints.add(Blueprint.newBlueprint(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return blueprints;
    }

}
