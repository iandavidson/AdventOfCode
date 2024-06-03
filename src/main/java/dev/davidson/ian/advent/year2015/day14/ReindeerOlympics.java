package dev.davidson.ian.advent.year2015.day14;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ReindeerOlympics {

    private static final String INPUT_PATH = "adventOfCode/2015/day14/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day14/sample.txt";
    private static final Integer TOTAL_TIME = 2503;//1000;
    public static void main(String[] args) {
        ReindeerOlympics reindeerOlympics = new ReindeerOlympics();
        log.info("Part1: {}", reindeerOlympics.part1());
        log.info("Part2: {}", reindeerOlympics.part2());
    }

    public int part1(){
        List<Reindeer> reindeerList = readFile();
        int max = 0;

        for(Reindeer reindeer: reindeerList){
            int distanceTraveled = 0;
            boolean currentlyResting = false;
            int i = 0;
            while(i < TOTAL_TIME){
                if(currentlyResting){
                    i += reindeer.restTime();
                    currentlyResting = false;
                }else{
                    for(int j  = 0 ; i < TOTAL_TIME && j < reindeer.runTime(); j++){
                        distanceTraveled += reindeer.runDistance();
                        i++;
                    }
                    currentlyResting = true;
                }
            }
            log.info("{}, {}", reindeer.name(), distanceTraveled);
            max = Math.max(max, distanceTraveled);
        }
        return max;
    }

    public int part2(){
        List<Reindeer> reindeerList = readFile();
        List<ReindeerRaceState> reindeerRaceStates = reindeerList.stream().map(ReindeerRaceState::toReindeerRaceState).toList();


        //do it different this time

        for(int i = 0; i < )

    }

    private List<Reindeer> readFile() {
        List<Reindeer> reindeerList = new ArrayList<>();
        ClassLoader cl = ReindeerOlympics.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\s+");
                reindeerList.add(Reindeer.builder()
                        .name(parts[0].trim())
                        .runDistance(Integer.parseInt(parts[3]))
                        .runTime(Integer.parseInt(parts[6]))
                        .restTime(Integer.parseInt(parts[13])).build());
            }
//            Dancer can fly 37 km/s for 1 seconds, but then must rest for 36 seconds.
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }

        return reindeerList;
    }
}
