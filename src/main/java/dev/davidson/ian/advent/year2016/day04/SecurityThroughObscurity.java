package dev.davidson.ian.advent.year2016.day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityThroughObscurity {

    private static final String INPUT_PATH = "adventOfCode/2016/day04/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2016/day04/sample.txt";
    private static final String PART_2_MATCH = "northpole-object-storage-";

    public static void main(String[] args) {
        SecurityThroughObscurity securityThroughObscurity = new SecurityThroughObscurity();
        log.info("Part1: {}", securityThroughObscurity.part1());
        log.info("Part2: {}", securityThroughObscurity.part2());
    }

    public Long part1() {
        List<RoomName> roomNames = readFile();
        return roomNames.stream().filter(RoomName::isValid).mapToLong(RoomName::sectorId).sum();
    }

    public Integer part2() {
        List<RoomName> roomNames = readFile();
        for (RoomName roomName : roomNames) {
            if (roomName.findShift().equals(PART_2_MATCH)) {
                return roomName.sectorId();
            }
        }

        return -1;
    }


    private List<RoomName> readFile() {
        List<RoomName> roomNames = new ArrayList<>();

        ClassLoader cl = SecurityThroughObscurity.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                roomNames.add(RoomName.newRoomName(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return roomNames;
    }
}
