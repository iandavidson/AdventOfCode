package org.example.advent.year2023.twentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SandSlabs {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day22/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day22/input.txt";
    private static final String MINI_INPUT_PATH = "adventOfCode/day22/input-mini.txt";

    public static void main(String[] args) {
        SandSlabs sandSlabs = new SandSlabs();
        System.out.println("part 1: " + sandSlabs.part1());
    }

    public long part1() {
        List<String> inputs = readFile();
        List<Slab> slabs = processInputs(inputs);
        Collections.sort(slabs);
        Map<Slab, Set<Slab>> supportedByMap = timber(slabs);

        Map<Slab, Set<Slab>> supportMap = createReverseMap(supportedByMap);

        for (Map.Entry<Slab, Set<Slab>> entry : supportedByMap.entrySet()) {
            System.out.println("key-hash: " + entry.getKey().hashCode() + " key: " + entry.getKey());
            for (Slab s : entry.getValue()) {
                System.out.println("value-hash: " + s.hashCode() + " value: " + s);
            }
            System.out.println("\n");
        }


        return computeScore(supportMap, supportedByMap);
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
                    } else if (highestXYCollidingZ == slabs.get(j).getTopZ()) {
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

    private Map<Slab, Set<Slab>> createReverseMap(Map<Slab, Set<Slab>> supportedByMap) {
        Map<Slab, Set<Slab>> supportsMap = new HashMap<>();
        for (Map.Entry<Slab, Set<Slab>> entry : supportedByMap.entrySet()) {
            for (Slab slab : entry.getValue()) {
                if (!supportsMap.containsKey(slab)) {
                    Set<Slab> supports = new HashSet<>();
                    supports.add(entry.getKey());
                    supportsMap.put(slab, supports);
                } else {
                    supportsMap.get(slab).add(entry.getKey());
                }
            }
        }

        for (Slab slab : supportedByMap.keySet()) {
            supportsMap.computeIfAbsent(slab, zilch -> new HashSet<>());
        }

        return supportsMap;
    }

    private Long computeScore(Map<Slab, Set<Slab>> supportMap, Map<Slab, Set<Slab>> supportedByMap) {
        long count = 0L;
        for (var slabEntry : supportMap.entrySet()) {

            System.out.println("code: " + slabEntry.getKey().hashCode() + "; slab: " + slabEntry.getKey());

            boolean fullySupported = true;
            for (Slab supporter : slabEntry.getValue()) {

                System.out.println("supporter; code: " + supporter.hashCode() + "; slab: " + supporter);

                // getting null pointer here
                // I can see the hash code the object hashcode matches
                Set<Slab> supporters = supportedByMap.get(supporter);

                if (fullySupported && supporters.size() < 2) {
                    fullySupported = false;
                    break;
                }
            }

            if (fullySupported) {
                count++;
            }

        }
        return count;
    }

    /*

1 -> no blocks sit on top (G); leafs can be deleted
2 -> at least one other block supports the same block
—> (B) supports both  {D, E}
    —> Iterate over set, ensure for all elements supported by B are supported by at least one other slab


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


     */
}
