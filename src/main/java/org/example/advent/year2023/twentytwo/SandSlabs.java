package org.example.advent.year2023.twentytwo;

import lombok.extern.java.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

@Log
public class SandSlabs {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day22/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day22/input.txt";
    private static final String MINI_INPUT_PATH = "adventOfCode/day22/input-mini.txt";

    public static void main(String[] args) {
        SandSlabs sandSlabs = new SandSlabs();
        log.info("part 1: " + sandSlabs.part1());
    }

    public long part1() {
        List<String> inputs = readFile();
        List<Slab> slabs = processInputs(inputs);
        Collections.sort(slabs);
        Map<Slab, Set<Slab>> supportedByMap = timber(slabs);

        Map<Slab, Set<Slab>> reverseMap = createReverseMap(supportedByMap);


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

    private List<Slab> processInputs(List<String> inputs) {
        List<Slab> slabs = new ArrayList<>();
        for (String input : inputs) {
            String[] coordinates = input.split("~");
            String[] start = coordinates[0].split(",");
            String[] end = coordinates[1].split(",");
            slabs.add(Slab.builder()
                    .start(Coordinate.builder().x(Integer.parseInt(start[0])).y(Integer.parseInt(start[1])).z(Integer.parseInt(start[2])).build())
                    .end(Coordinate.builder().x(Integer.parseInt(end[0])).y(Integer.parseInt(end[1])).z(Integer.parseInt(end[2])).build())
                    .build());
        }

        return slabs;
    }

    private Map<Slab, Set<Slab>> timber(List<Slab> slabs) {
        Map<Slab, Set<Slab>> supportedByMap = new HashMap<>();

        int currentZ = 0;
        for (int i = 0; i < slabs.size(); i++) {
            Slab slab = slabs.get(i);
            currentZ = slab.getBottomZ();
            int highestXYCollidingZ = 0;

            //determine highestXYCollidingZ
            Set<Slab> supportedBy = new HashSet<>();
            for (int j = 0; j < i; j++) {
                if (slab.willCollideAfterFall(slabs.get(j))) {

                    if (highestXYCollidingZ < slabs.get(j).getTopZ()) {
                        supportedBy.clear();
                        supportedBy.add(slabs.get(j));
                        highestXYCollidingZ = slabs.get(j).getTopZ();
                    }else if (highestXYCollidingZ == slabs.get(j).getTopZ()) {
                        supportedBy.add(slabs.get(j));
                    }
                }
            }

            supportedByMap.put(slab, supportedBy);

            if (highestXYCollidingZ + 1 == currentZ) {
                // do nothing, can't fall any further
            } else if (highestXYCollidingZ != 0) {
                // we found collision in xy plane that we can fall down to
                slab.fall(currentZ - (highestXYCollidingZ + 1));
            } else {
                // fall all the way to the ground
                slab.fall(currentZ - 1);
            }
        }
        return supportedByMap;
    }

    private Map<Slab, Set<Slab>> createReverseMap(Map<Slab, Set<Slab>> supportedByMap){
        Map<Slab, Set<Slab>> supportsMap = new HashMap<>();
        for(Map.Entry<Slab, Set<Slab>> entry: supportedByMap.entrySet()){
            for(Slab slab : entry.getValue()){
                if(!supportsMap.containsKey(slab)){
                    Set<Slab> supports = new HashSet<>();
                    supports.add(entry.getKey());
                    supportsMap.put(slab, supports);
                }else{
                    supportsMap.get(slab).add(entry.getKey());
                }
            }
        }

        return supportsMap;
    }

    /* test case that works:

        1,0,4~1,2,4 -> falls on top of slab listed below
        1,0,2~1,2,2 -> falls down to bottom by 1 value

     */


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
