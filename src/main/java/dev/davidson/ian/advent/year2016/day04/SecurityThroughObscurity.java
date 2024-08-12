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

    public static void main(String [] args){
        SecurityThroughObscurity securityThroughObscurity = new SecurityThroughObscurity();
        log.info("Part1: {}", securityThroughObscurity.part1());
    }

    public Long part1(){
        List<RoomName> roomNames = readFile();
        return roomNames.stream().filter(RoomName::isValid).count();
        //337 too low
    }

    private List<RoomName> readFile(){
        List<RoomName> roomNames = new ArrayList<>();

        ClassLoader cl = SecurityThroughObscurity.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                roomNames.add(RoomName.newRoomName(scanner.nextLine()));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException();
        }

        return roomNames;
    }
}
