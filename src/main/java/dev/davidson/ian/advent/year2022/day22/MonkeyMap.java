package dev.davidson.ian.advent.year2022.day22;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MonkeyMap {

    private List<List<TILE>> grid = new ArrayList<>();
    private List<String> instructions;
    private static final String INPUT_PATH = "adventOfCode/2022/day22/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2022/day22/sample.txt";

    public static void main(String[] args) {
        MonkeyMap monkeyMap = new MonkeyMap();
        log.info("Part1: {}", monkeyMap.part1());
    }

    public int part1() {
        readFile();

        return 0;
    }

    private void readFile() {
        List<List<TILE>> initialGrid = new ArrayList<>();
        ClassLoader cl = MonkeyMap.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            String rawRow = scanner.nextLine();
            int maxWidth = 0;
            while (!rawRow.isBlank()) {
                List<TILE> tempList = Arrays.stream(rawRow.split("")).map(val -> TILE.TILE_MAP.get(val)).toList();
                initialGrid.add(tempList);
                maxWidth = Math.max(maxWidth, tempList.size());
                rawRow = scanner.nextLine();
            }

            setGrid(initialGrid, maxWidth);
            setInstructions(scanner.nextLine());

        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }
    }

    private void setInstructions(final String line) {
        Pattern pattern = Pattern.compile("\\d+|L|R");
        Matcher matcher = pattern.matcher(line);
        List<String> instructions = new ArrayList<>();
        while (matcher.find()) {
            instructions.add(matcher.group());
        }

        this.instructions = instructions;
    }

    private void setGrid(final List<List<TILE>> initial, final int maxWidth) {
        List<List<TILE>> toBe = new ArrayList<>();
        for(int i =0 ; i < initial.size(); i++){
            List<TILE> tempList = new ArrayList<>();

            for(int j = 0; j < maxWidth; j++){

                if(j >= initial.get(i).size()){
                    tempList.add(TILE.OUT);
                    //add TILE.OUT
                }else{
                    tempList.add(initial.get(i).get(j));
                }
            }
            toBe.add(tempList);
        }

        this.grid = toBe;
    }
}
