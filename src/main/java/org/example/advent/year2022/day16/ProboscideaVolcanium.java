package org.example.advent.year2022.day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
        List<Valve> valves = readFile();
        return 0L;
    }

    private List<Valve> readFile(){
        List<Valve> valves = new ArrayList<>();
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

                valves.add(new Valve(chunks[0].trim(), outputs, Integer.parseInt(chunks[1].trim())));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return valves;
    }


}
