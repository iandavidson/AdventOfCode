package org.example.advent.year2022.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class BeaconExclusionZone {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day15/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day15/input.txt";
//        private static final int Y_LINE_PART_1 = 10;
    private static final int Y_LINE_PART_1 = 2000000;
    private static final int AXIS_LIMIT_PART_2 = 4_000_000;

    public static void main(String[] args) {
        BeaconExclusionZone beaconExclusionZone = new BeaconExclusionZone();
        System.out.println("Part1: " + beaconExclusionZone.part1());
        System.out.println("Part2: " + beaconExclusionZone.part2());
    }

    public long part1() {
        Set<Coordinate> knownBeacons = new HashSet<>();
        List<Sensor> sensorList = readFile(knownBeacons);
        Set<Integer> availableSpaceSet = new HashSet<>();

        for (Sensor sensor : sensorList) {
            //find distance to beacon, this -1 tells us all other locations are free
            int viableDistance = sensor.distance();
            int distanceToYLine = Math.abs(Y_LINE_PART_1 - sensor.location().y());
            int distanceOnY = viableDistance - distanceToYLine;

            if (distanceOnY >= 0) {
                for (int i = 0; i < distanceOnY + 1; i++) {

                    //left (sensor.location().x() - i)
                    if (!knownBeacons.contains(new Coordinate(sensor.location().x() - i, Y_LINE_PART_1))) {
                        availableSpaceSet.add(sensor.location().x() - i);
                    }

                    //right (sensor.location().x() + i)
                    if (!knownBeacons.contains(new Coordinate(sensor.location().x() + i, Y_LINE_PART_1))) {
                        availableSpaceSet.add(sensor.location().x() + i);
                    }
                }
            }
        }

        return availableSpaceSet.size();
    }


    public long part2() {
        Set<Coordinate> knownBeacons = new HashSet<>();
        List<Sensor> sensorList = readFile(knownBeacons);



        return 0;
    }

    //    private Map<Coordinate, EmitterType> readFile() {
    private List<Sensor> readFile(Set<Coordinate> knownBeacons) {
        List<Sensor> sensorList = new ArrayList<>();
        ClassLoader cl = BeaconExclusionZone.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Coordinate beacon = Coordinate.newCoordinate(line.substring(line.indexOf("is at") + 6));
                knownBeacons.add(beacon);
                sensorList.add(Sensor.newSensor(Coordinate.newCoordinate(line.substring(line.indexOf('x'), line.indexOf(':'))), beacon));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return sensorList;
    }


}
