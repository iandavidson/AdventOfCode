package dev.davidson.ian.advent.year2016.day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BathroomSecurity {

    enum Instruction {
        U, D, L, R
    }

    private static final String INPUT_PATH = "adventOfCode/2016/day02/input.txt";

    private static final char [][] PHONE_PAD_1 = new char[][]{
            {'1','2','3'},
            {'4','5','6'},
            {'7','8','9'},
    };

    private static final char [][] PHONE_PAD_2 = new char[][]{
            {'#', '#', '1', '#', '#'},
            {'#', '2', '3', '4', '#'},
            {'5', '6', '7', '8', '9'},
            {'#', 'A', 'B', 'C', '#'},
            {'#', '#', 'D', '#', '#'}
    };

    private static final Map<Instruction, int[]> SHIFTS = Map.of(
            Instruction.U, new int[]{-1, 0},
            Instruction.D, new int[]{1, 0},
            Instruction.L, new int[]{0, -1},
            Instruction.R, new int[]{0, 1}
    );

    public static void main(String[] args) {
        BathroomSecurity bathroomSecurity = new BathroomSecurity();
        log.info("Part1: {}", bathroomSecurity.part1());
        log.info("Part2: {}", bathroomSecurity.part2());
    }

    public String part1() {
        List<List<Instruction>> instructions = readFile();
        int currentRow = 1;
        int currentCol = 1;

        StringBuilder result = new StringBuilder();

        for (List<Instruction> sequence : instructions) {

            for (Instruction instruction : sequence) {
                currentRow = Math.max(Math.min(2, currentRow + SHIFTS.get(instruction)[0]), 0);
                currentCol = Math.max(Math.min(2, currentCol + SHIFTS.get(instruction)[1]), 0);
            }

            result.append(PHONE_PAD_1[currentRow][currentCol]);

        }
        return result.toString();
    }

    public String part2() {
        List<List<Instruction>> instructions = readFile();
        int currentRow = 0;
        int currentCol = 2;

        StringBuilder result = new StringBuilder();
        for(List<Instruction> sequence  :instructions){
            for(Instruction instruction : sequence){
                int proposedRow = Math.max(Math.min(4, currentRow + SHIFTS.get(instruction)[0]), 0);
                int proposedCol = Math.max(Math.min(4, currentCol + SHIFTS.get(instruction)[1]), 0);

                if(PHONE_PAD_2[proposedRow][proposedCol] != '#'){
                    currentRow = proposedRow;
                    currentCol = proposedCol;
                }
            }

            result.append(PHONE_PAD_2[currentRow][currentCol]);
        }
        return result.toString();
    }

    private List<List<Instruction>> readFile() {
        List<List<Instruction>> instructions = new ArrayList<>();
        ClassLoader cl = BathroomSecurity.class.getClassLoader();
        File file = new File(cl.getResource(INPUT_PATH).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                List<Instruction> tempList = new ArrayList<>();
                for (char ch : scanner.nextLine().toCharArray()) {
                    tempList.add(Instruction.valueOf(String.valueOf(ch)));
                }
                instructions.add(tempList);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return instructions;
    }
}
