package org.example.advent.year2023.twentyfour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class HailCollision {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day24/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day24/input.txt";

    public static void main(String[] args) {
        HailCollision hailCollision = new HailCollision();
        System.out.println("part1: " + hailCollision.part1());
    }


    public Long part1() {
        List<HailTrajectory> hailTrajectories = processInput();
    }

    private List<HailTrajectory> processInput() {

        List<String> inputs = new ArrayList<>();
        try {
            ClassLoader classLoader = HailCollision.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                inputs.add(myReader.nextLine());
            }
        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        List<HailTrajectory> hailTrajectories = new ArrayList<>();

        for(String inputLine: inputs){
            String [] chunks = inputLine.split("@");

            String [] positions = chunks[0].split(",");
            String [] deltas = chunks[1].split(",");

            hailTrajectories.add(
              HailTrajectory.builder()
                      .x(Long.parseLong(positions[0].trim()))
                      .y(Long.parseLong(positions[1].trim()))
                      .z(Long.parseLong(positions[2].trim()))
                      .deltaX(Long.parseLong(deltas[0].trim()))
                      .deltaY(Long.parseLong(deltas[1].trim()))
                      .deltaZ(Long.parseLong(deltas[2].trim()))
                      .build()
            );
        }

        return hailTrajectories;
    }
}
