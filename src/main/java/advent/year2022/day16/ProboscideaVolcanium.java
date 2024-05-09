package advent.year2022.day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class ProboscideaVolcanium {
    private static final String SAMPLE_PATH = "adventOfCode/2022/day16/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day16/input.txt";
    private Map<ValveState, Integer> distanceMap;

    public static void main(String[] args) {
        ProboscideaVolcanium proboscideaVolcanium = new ProboscideaVolcanium();
        System.out.println("Part1: " + proboscideaVolcanium.part1());
        System.out.println("Part2: " + proboscideaVolcanium.part2());
    }

    public int part1() {
        Map<String, Valve> valves = readFile();
        distanceMap = new HashMap<>();
        Valve start = valves.get("AA");
        return commence(start, 30, new ArrayList<>(), valves, 0);
    }

    public int part2() {
        Map<String, Valve> valves = readFile();
        distanceMap = new HashMap<>();
        Valve start = valves.get("AA");
        return commence(start, 26, new ArrayList<>(), valves, 1);
    }

    private int commence(final Valve start, final int minutesRemaining, final List<Valve> opened, final Map<String, Valve> valves, final int othersCount) {
        if (minutesRemaining == 0) {
            Valve aa = valves.get("AA");
            return othersCount > 0 ? commence(aa, 26, opened, valves, othersCount - 1) : 0;
        }

        final ValveState state = new ValveState(start, minutesRemaining, opened, othersCount);
        if (distanceMap.containsKey(state)) {
            return distanceMap.get(state);
        }

        int max = 0;
        if (start.flowRate() > 0 && !opened.contains(start)) {
            opened.add(start);
            // Make sure the opened valves are sorted, to make the cache work
            Collections.sort(opened);
            final int val = (minutesRemaining - 1) * start.flowRate()
                    + commence(start, minutesRemaining - 1, opened, valves, othersCount);
            opened.remove(start);
            max = val;
        }

        for (final String n : start.outs()) {
            final Valve neighbour = valves.get(n);
            max = Math.max(max, commence(neighbour, minutesRemaining - 1, opened, valves, othersCount));
        }
        distanceMap.put(state, max);

        return max;
    }

    private Map<String, Valve> readFile() {
        Map<String, Valve> valves = new HashMap<>();
        ClassLoader cl = ProboscideaVolcanium.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] chunksNew = line.split("\\s+");
                String label = chunksNew[1].trim();
                int magnitude = Integer.parseInt(chunksNew[4].substring(chunksNew[4].indexOf("=") + 1, chunksNew[4].indexOf(";")));
                List<String> outputs = Arrays.stream(chunksNew).skip(9).map(str -> str.charAt(str.length() - 1) == ',' ? str.substring(0, str.length() - 1) : str).toList();

                valves.put(label, new Valve(label, outputs, magnitude));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return valves;
    }
}
