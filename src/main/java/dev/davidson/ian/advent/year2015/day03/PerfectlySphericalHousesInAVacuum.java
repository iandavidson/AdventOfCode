package dev.davidson.ian.advent.year2015.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PerfectlySphericalHousesInAVacuum {

    private static final String INPUT_PATH = "adventOfCode/2015/day03/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2015/day03/sample.txt";

    private static final Map<Character, Coordinate> SHIFT_MAP = Map.of(
            '>', new Coordinate(0, 1),
            '<', new Coordinate(0, -1),
            '^', new Coordinate(-1, 0),
            'v', new Coordinate(1, 0)
    );

    public static void main(String[] args) {
        PerfectlySphericalHousesInAVacuum perfectlySphericalHousesInAVacuum = new PerfectlySphericalHousesInAVacuum();
        System.out.println(perfectlySphericalHousesInAVacuum.part1());
    }

    public Integer part1() {
        char[] inputs = readFile();
        Map<Coordinate, Integer> houseMapCount = new HashMap<>();
        Coordinate current = new Coordinate(0,0);
        houseMapCount.put(current, 1);

        for(char ch : inputs){
            Coordinate next = Coordinate.newCoordinate(current, SHIFT_MAP.get(ch));
            if(houseMapCount.containsKey(next)){
                houseMapCount.put(next, houseMapCount.get(next)+1);
            }else{
                houseMapCount.put(next, 1);
            }

            current = next;
        }

        Integer multiplePresentsCount = 0;
        for(Integer count : houseMapCount.values()){
            if(count > 1){
                multiplePresentsCount++;
            }
        }

        //1762 too low
        return multiplePresentsCount;
    }

    private char[] readFile() {
        ClassLoader cl = PerfectlySphericalHousesInAVacuum.class.getClassLoader();
        File file = new File(cl.getResource(INPUT_PATH).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                return scanner.nextLine().toCharArray();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return new char[]{};
    }
}
