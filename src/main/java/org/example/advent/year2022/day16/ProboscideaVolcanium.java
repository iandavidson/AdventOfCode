package org.example.advent.year2022.day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class ProboscideaVolcanium {
    private static final String SAMPLE_PATH = "adventOfCode/2022/day16/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day16/input.txt";

    public static void main(String [] args){
        ProboscideaVolcanium proboscideaVolcanium = new ProboscideaVolcanium();
        System.out.println("Part1: " + proboscideaVolcanium.part1());
    }

    public long part1(){
        Map<String, Valve> valves = readFile();
        return commence(valves, 30);
    }

    private Map<String, Valve> readFile(){
        Map<String, Valve> valves = new HashMap<>();
        ClassLoader cl = ProboscideaVolcanium.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();

                String [] chunksNew = line.split("\\s+");
                String label = chunksNew[1].trim();
                int magnitude = Integer.parseInt(chunksNew[4].substring(chunksNew[4].indexOf("=")+1, chunksNew[4].indexOf(";")));
                List<String> outputs = Arrays.stream(chunksNew).skip(9).map(str -> str.charAt(str.length()-1) == ',' ? str.substring(0, str.length()-1) : str).toList();

                valves.put(label, new Valve(label, outputs, magnitude));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return valves;
    }

    private Long commence(final Map<String, Valve> valveMap, final int minutes){
        Long count = 0L;
        Valve currentValve = valveMap.get("AA");
        Set<Valve> turnedOn = new HashSet<>();
        for(int i = 0; i < minutes; i++){

            if(shouldTurnOnCurrent(valveMap, currentValve, turnedOn.contains(currentValve))){
                turnedOn.add(currentValve);
            }else{
                //determine where to go
            }
            //takes 1 minute: if / else this
            //  turn on current valve
            //  move to another valve

            for(Valve valve : turnedOn){
                count += valve.flowRate();
            }

        }
        return count;
    }

    private boolean shouldTurnOnCurrent(final Map<String, Valve> valveMap, final Valve currentValve, final boolean currentTurnedOn) {

    }

    /*
    intuition:

    Start at "AA"
    -> look at tunnels from AA, Decide based on the following:
            -> tunnel has highest pressure
            -> tunnel is closed (that means we can open and release more pressure)

            --> eventually look past immediate tunnels to tunnels-of-tunnels
     */
}
