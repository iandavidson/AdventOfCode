package org.example.advent.year2023.twentytwo;

import lombok.extern.java.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;

@Log
public class SandSlabs {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day22/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day22/input.txt";

    public static void main(String [] args){
        SandSlabs sandSlabs = new SandSlabs();
        log.info("part 1: " + sandSlabs.part1());
    }

    public long part1(){
        List<String> inputs = readFile();
        List<Slab> slabs = processInputs(inputs);
        Collections.sort(slabs);

        return 0L;
    }

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = SandSlabs.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(SAMPLE_INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    private List<Slab> processInputs(List<String> inputs){
        List<Slab> slabs = new ArrayList<>();
        for(String input: inputs){
            String [] coordinates = input.split("~");
            String [] start = coordinates[0].split(",");
            String [] end = coordinates[1].split(",");
            slabs.add(Slab.builder()
                            .start(Coordinate.builder().x(Integer.parseInt(start[0])).y(Integer.parseInt(start[1])).z(Integer.parseInt(start[2])).build())
                            .end(Coordinate.builder().x(Integer.parseInt(end[0])).y(Integer.parseInt(end[1])).z(Integer.parseInt(end[2])).build())
                    .build());
        }

        return slabs;
    }

    private List<Slab> timber(List<Slab> slabs){

    }


    /*

	     G
         |
         F
         |
	     D   E
	     |\ /|
	      B C
	      \ /
	       A

A -> B
A -> C
B -> D
B -> E
C -> D
C -> E
D -> F
F -> G

2 cases:
1 -> no blocks sit on top (G); leafs can be deleted
2 -> at least one other block supports the same block
—> (B) supports both  {D, E}
    —> Iterate over set, ensure for all elements supported by B are supported by at least one other slab
     */
}
