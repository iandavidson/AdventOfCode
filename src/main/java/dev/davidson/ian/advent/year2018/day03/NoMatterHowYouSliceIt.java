package dev.davidson.ian.advent.year2018.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoMatterHowYouSliceIt {

    private static final String INPUT_PATH = "adventOfCode/2018/day03/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2018/day03/sample.txt";

    public static void main(String[] args) {
        NoMatterHowYouSliceIt noMatterHowYouSliceIt = new NoMatterHowYouSliceIt();
        log.info("Sample Part1: {}", noMatterHowYouSliceIt.part1(readFile(SAMPLE_PATH)));
        log.info("Real Part1: {}", noMatterHowYouSliceIt.part1(readFile(INPUT_PATH)));
    }

    private static List<Claim> readFile(final String filePath) {
        List<Claim> claims = new ArrayList<>();

        ClassLoader cl = NoMatterHowYouSliceIt.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                claims.add(Claim.newClaim(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read input file");
        }

        return claims;
    }

    public Long part1(final List<Claim> claims) {
        Map<Coordinate, Integer> map = new HashMap<>();

        for (Claim claim : claims) {
            stitchMap(map, claim);
        }

        Long sum = 0L;
        for(Integer val :  map.values().stream().filter(val -> val > 1).toList()){
            sum += val;
        }

        return sum;
    }

    private void stitchMap(Map<Coordinate, Integer> map, Claim claim) {
        Coordinate start = claim.getStart();
        for (int r = 0; r < claim.getLength(); r++) {
            for (int c = 0; c < claim.getWidth(); c++) {
                Coordinate current = new Coordinate(start.r() + r, start.c() + c);
                map.put(current, map.getOrDefault(current, 0) + 1);
            }
        }
    }
}
