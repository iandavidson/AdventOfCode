package org.example.advent.year2022.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class BeaconExclusionZone {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day15/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day15/input.txt";

    public static void main(String[] args) {
        BeaconExclusionZone beaconExclusionZone = new BeaconExclusionZone();
        System.out.println("Part1: " + beaconExclusionZone.part1());
    }

    public long part1() {
        Map<Coordinate, EmitterType> map = readFile();
        return 1L;
    }

    private Map<Coordinate, EmitterType> readFile() {
        Map<Coordinate, EmitterType> map = new HashMap<>();
        ClassLoader cl = BeaconExclusionZone.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Coordinate sensor = Coordinate.newCoordinate(line.substring(line.indexOf('x'), line.indexOf(':')));
                Coordinate beacon = Coordinate.newCoordinate(line.substring(line.indexOf("is at") + 6));
                map.put(sensor, EmitterType.SENSOR);
                map.put(beacon, EmitterType.BEACON);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return map;
    }


}
