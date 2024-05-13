package dev.davidson.ian.advent.year2022.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class BeaconExclusionZone {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day15/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day15/input.txt";
    //  private static final int Y_LINE_PART_1 = 10;
    private static final int Y_LINE_PART_1 = 2000000;

    //  private static final int AXIS_LIMIT_PART_2 = 20;
    private static final int AXIS_LIMIT_PART_2 = 4_000_000;

    public static void main(String[] args) {
        BeaconExclusionZone beaconExclusionZone = new BeaconExclusionZone();
        System.out.println("Part1: " + beaconExclusionZone.part1());
//        System.out.println("Part2: " + beaconExclusionZone.part2());
        beaconExclusionZone.part2();
    }

    public long part1() {
        Set<Coordinate> knownBeacons = new HashSet<>();
        List<Sensor> sensorList = readFile(knownBeacons);
        Set<Integer> availableSpaceSet = new HashSet<>();

        for (Sensor sensor : sensorList) {
            //find distance to beacon, this -1 tells us all other locations are free
            int distanceOnY = sensor.distance() - Math.abs(Y_LINE_PART_1 - sensor.location().y());

            if (distanceOnY >= 0) {
                for (int i = 0; i < distanceOnY + 1; i++) {
                    if (!knownBeacons.contains(new Coordinate(sensor.location().x() - i, Y_LINE_PART_1))) {
                        availableSpaceSet.add(sensor.location().x() - i);
                    }
                    if (!knownBeacons.contains(new Coordinate(sensor.location().x() + i, Y_LINE_PART_1))) {
                        availableSpaceSet.add(sensor.location().x() + i);
                    }
                }
            }
        }

        return availableSpaceSet.size();
    }


    public void part2() {
        List<Sensor> sensors = readFile(new HashSet<>());
        for (Sensor sensor : sensors) {
            for (int i = -(sensor.distance() + 1); i <= sensor.distance(); i++) {
                int width = sensor.distance() - Math.abs(i);

                if (isOutsideRange(sensors, sensor.location().x() - width - 1, sensor.location().y() + i) ||
                        isOutsideRange(sensors, sensor.location().x() + width + 1, sensor.location().y() + i)) {
                    return;
                }
            }
        }

    }

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

    private boolean isOutsideRange(List<Sensor> sensors, int x, int y) {
        if (x >= 0 && x <= AXIS_LIMIT_PART_2 && y >= 0 && y <= AXIS_LIMIT_PART_2
                && sensors.stream().noneMatch(sensor -> sensor.inBounds(x, y))) {
            System.out.println("Part2: Found point outside sensor ranges: " + x + ", " + y + "; Frequency: " + BigInteger.valueOf(x).multiply(BigInteger.valueOf(AXIS_LIMIT_PART_2)).add(BigInteger.valueOf((y))));
            return true;
        }
        return false;
    }
}
