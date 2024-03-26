package org.example.advent.year2023.twentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;


public class SandSlabs {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day22/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day22/input.txt";
    private static final String MINI_INPUT_PATH = "adventOfCode/day22/input-mini.txt";
    private static final String MINI_INPUT_2_PATH = "adventOfCode/day22/input-mini-2.txt";

    public static void main(String[] args) {
        SandSlabs sandSlabs = new SandSlabs();
        System.out.println("part 1: " + sandSlabs.part1());
        System.out.println("part 2: " + sandSlabs.part2());
    }

    public long part1() {
        List<String> inputs = readFile();
        List<Slab> slabs = processInputs(inputs);
        Collections.sort(slabs);
        Map<Slab, Set<Slab>> supportedByMap = supportedByMap(slabs);
        Map<Slab, Set<Slab>> supportMap = createSupportMap(supportedByMap);

        return computeScore(supportMap, supportedByMap);
    }

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = SandSlabs.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
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

    private Map<Slab, Set<Slab>> supportedByMap(List<Slab> slabs) {
        Map<Slab, Set<Slab>> supportedByMap = new HashMap<>();

        int currentZ;
        for (int i = 0; i < slabs.size(); i++) {
            Slab slab = slabs.get(i);
            currentZ = slab.getBottomZ();
            int highestXYCollidingZ = 0;

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

            if (highestXYCollidingZ + 1 == currentZ) {
                // do nothing, can't fall any further
            } else if (highestXYCollidingZ != 0) {
                // we found collision in xy plane that we can fall down to
                slab.fall(currentZ - (highestXYCollidingZ + 1));
            } else {
                // fall all the way to the ground
                slab.fall(currentZ - 1);
            }

            supportedByMap.put(slab, supportedBy);
        }
        return supportedByMap;
    }

    private Map<Slab, Set<Slab>> createSupportMap(Map<Slab, Set<Slab>> supportedByMap) {
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
            boolean safe = true;
            for (Slab supporter : slabEntry.getValue()) {

                Set<Slab> supporters = supportedByMap.get(supporter);

                if (safe && supporters.size() < 2) {
                    safe = false;
                    break;
                }
            }

            if (safe) {
                count++;
            }

        }
        return count;
    }

    public long part2() {
        List<String> inputs = readFile();
        List<Slab> slabs = processInputs(inputs);
        Collections.sort(slabs);
        Map<Slab, Set<Slab>> supportedByMap = supportedByMap(slabs);
        Map<Slab, Set<Slab>> supportMap = createSupportMap(supportedByMap);
        Set<Slab> safeToRemoves = findSafeToRemove(supportMap, supportedByMap);
        return computeChainReaction(supportMap, supportedByMap, safeToRemoves);
    }

    private Set<Slab> findSafeToRemove(Map<Slab, Set<Slab>> supportMap, Map<Slab, Set<Slab>> supportedByMap) {
        Set<Slab> slabSet = new HashSet<>();
        for (var slabEntry : supportMap.entrySet()) {
            boolean safe = true;
            for (Slab supporter : slabEntry.getValue()) {

                Set<Slab> supporters = supportedByMap.get(supporter);

                if (safe && supporters.size() < 2) {
                    safe = false;
                    break;
                }
            }

            if (safe) {
                slabSet.add(slabEntry.getKey());
            }

        }
        return slabSet;
    }

    private Long computeChainReaction(Map<Slab, Set<Slab>> supportMap, Map<Slab, Set<Slab>> supportedByMap, Set<Slab> safeToRemove) {
        Set<Slab> alreadyProcessed = new HashSet<>();
        Map<Slab, Long> weightMap = new HashMap<>();
        Queue<Slab> queue = new LinkedList<>();

        for (Slab slab : safeToRemove) {
            //initialize slab entries for ones that can be safely removed (leafs included)
            weightMap.put(slab, 0L);
        }

        for (Slab slab : supportMap.keySet()) {
            if (supportMap.get(slab).isEmpty()) {
                //leaf nodes, disintegrating causes none to fall, start with these
                queue.add(slab);
            }
        }

        Slab current = null;
        while (!queue.isEmpty()) {
            current = queue.remove();

            if (alreadyProcessed.contains(current)) {
                continue;
            }

            if (!weightMap.containsKey(current)) {
                //calculate how many are above
                weightMap.put(current, howManyFall(current, supportMap, supportedByMap));
            }


            //add supporters to queue to be processed
            for (Slab supporter : supportedByMap.get(current)) {
                if (!alreadyProcessed.contains(supporter)) {
                    queue.add(supporter);
                }
            }

            alreadyProcessed.add(current);

        }

        return weightMap.values().stream().mapToLong(Long::longValue).sum();
        //answer being computed: "113700" is too high says AOC website
        //answer being computed: "48854" is too high says AOC website
        //answer being computed: "35744" is too high says AOC website
        //correct answer : 35654
    }

    private Long howManyFall(Slab slab, Map<Slab, Set<Slab>> supportMap, Map<Slab, Set<Slab>> supportedByMap) {
        Queue<Slab> queue = new LinkedList<>();
        Set<Slab> seen = new HashSet<>();
        Set<Slab> ancestors = new HashSet<>();
        queue.addAll(supportMap.get(slab));
        seen.addAll(supportMap.get(slab));
//        queue.add(slab);
        seen.add(slab);

        while (!queue.isEmpty()) {
            Slab current = queue.remove();

            if (ancestors.contains(current)) {
                continue;
            }

            //determine if slabs currently supported will also fall, if so add to queue.
            for (Slab supportee : supportMap.get(current)) {
                boolean willFall = true;
                for (Slab under : supportedByMap.get(supportee)) {

                    if (!seen.contains(under)) {
                        willFall = false;
                        break;
                    }
                }

                seen.add(supportee);
                if (willFall) {
                    queue.add(supportee);
                }
            }

            ancestors.add(current);
        }

        return (long) ancestors.size();
    }

    /*
    part 1 intuition:

1 -> no blocks sit on top (G); leafs can be deleted
2 -> at least one other block supports the same block
—> (B) supports both  {D, E}
    —> Iterate over set, ensure for all elements supported by B are supported by at least one other slab


	       G
           |
           F
         /   \
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



    part 2 intuition:

current == B

supportMap(B) => { A }

    can only add A into queue if all supporters of A can reach slab(method parameter)
    -> instaed right now I'm just looking in the Seen set, sounds like working all the way down to "slab" is a lot of work. close to n^3



[  A  ]
[B] [C]
[D] [E]

if D is removed, only B disintegrated.

if E is removed, only C is disintegrated.

but I counted A in both cases.


     */
}
