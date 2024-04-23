package org.example.advent.year2022.day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class ProboscideaVolcanium {
    private static final String SAMPLE_PATH = "adventOfCode/2022/day16/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day16/input.txt";

    public static void main(String [] args){
        ProboscideaVolcanium proboscideaVolcanium = new ProboscideaVolcanium();
        System.out.println("Part1: " + proboscideaVolcanium.part1());
    }

    public long part1(){
        Map<String, Valve> valves = readFile();
        return 0L;
    }

    private Map<String, Valve> readFile(){
        Map<String, Valve> valves = new HashMap<>();
        ClassLoader cl = ProboscideaVolcanium.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                line = line.replace("Valve ","").replace(" has flow rate=", ", ").replace("; tunnels lead to valves ", ", ");
                System.out.println("...");
                String [] chunks = line.split(",");
                List<String> outputs = new ArrayList<>();
                for(int i = 2; i < chunks.length; i++){
                    outputs.add(chunks[i].trim());
                }

                valves.put(chunks[0].trim(), new Valve(chunks[0].trim(), outputs, Integer.parseInt(chunks[1].trim()), false));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return valves;
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
