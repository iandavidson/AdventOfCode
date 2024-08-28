package dev.davidson.ian.advent.year2016.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimingIsEverything {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day15/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day15/input.txt";

    public static void main(String[] args) {
        TimingIsEverything timingIsEverything = new TimingIsEverything();
        List<Disk> disks = timingIsEverything.readFile();
        log.info("Part1: {}", timingIsEverything.execute(disks));
        disks.add(new Disk(disks.size() + 1, 11, 0));
        log.info("Part2: {}", timingIsEverything.execute(disks));
    }

    public Long execute(final List<Disk> disks) {
        // Disk calculation:
        //when: (Position + disk# + time) % |positions| == 0

        //compoundPeriod starts at 1
        // Disc #1 has 5 positions; at time=0, it is at position 4.
        // (4 + 1 + ?1) % 5 == 0;
        // Once we made it through disk 1, compoundPeriod *= disk1.positions; compoundPeriod == 5

        //1 more disk; ( 7 positions) & assuming we magically land at (...% == 0)
        //compoundPeriod *= 7, compoundPeriod = 35

        //we now arrive at
        // Disc #3 has 3 positions; at time=0, it is at position 1; if time is 3 when we arrive at disk 3
        // we dont make it into disk 3.

        //next attempt, start at time += compoundPeriod; (where time starts at 35)
        // This guarantees that we will make it through the disks up until the point we failed last iteration.

        long time = 0;
        boolean done = false;
        while (!done) {
            long compoundPeriod = 1;
            done = true;

            for (Disk current : disks) {
                long operand = time + current.order() + current.startPosition();
                if (operand % current.positions() == 0) {
                    compoundPeriod *= current.positions();
                } else {
                    time += compoundPeriod;
                    done = false;
                    break;
                }
            }
        }

        return time;
    }

    public List<Disk> readFile() {
        List<Disk> disks = new ArrayList<>();

        ClassLoader cl = TimingIsEverything.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                disks.add(Disk.newDisk(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file given file path");
        }

        return disks;
    }
}
