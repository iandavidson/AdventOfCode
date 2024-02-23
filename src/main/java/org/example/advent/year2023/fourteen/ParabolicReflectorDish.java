package org.example.advent.year2023.fourteen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ParabolicReflectorDish {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day14/input-sample.txt";
    private static final String MINI_SAMPLE_INPUT_PATH = "adventOfCode/day14/input-sample-mini.txt";
    private static final String INPUT_PATH = "adventOfCode/day14/input.txt";

    private static List<String> processInput(List<String> inputs) {
        List<String> grid = new ArrayList<>();
        for (String row : inputs) {
            grid.add(row);

        }

        return grid;
    }

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = ParabolicReflectorDish.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(MINI_SAMPLE_INPUT_PATH)).getFile());
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

    private static Long findNorthSideLoadCol(List<Character> column) {
        //rolls towards index 0;
        int recentBlockIndex = -1;

        for (int i = 0; i < column.size(); i++) {
            if (column.get(i).equals('#')) {
                recentBlockIndex = i;
            } else if (column.get(i).equals('O')) {
                if (i > recentBlockIndex + 1) {
                    //we can roll back to recentBlockIndex + 1
                    column.set(i, '.');
                    column.set(recentBlockIndex + 1, 'O');

                }

                recentBlockIndex++;
            }
        }

        Long count = 0L;

        for (int i = 0; i < column.size(); i++) {
            if (column.get(i).equals('O')) {
                count += column.size() - i;
            }
        }

        // size - index
        return count;
    }

    private static Long calculateNorthernLoadPart1(List<String> grid) {
        List<List<Character>> gridColOrganized = new ArrayList<>();
        for (int i = 0; i < grid.get(0).length(); i++) {
            List<Character> temp = new ArrayList<>();

            for (int j = 0; j < grid.size(); j++) {
                temp.add(grid.get(j).charAt(i));
            }

            gridColOrganized.add(temp);
        }

        Long sum = 0L;
        for (List<Character> column : gridColOrganized) {
            sum += findNorthSideLoadCol(column);
        }

        return sum;
    }

    public static Long part1() {
        List<String> inputLines = readFile();
        List<String> input = processInput(inputLines);
        return calculateNorthernLoadPart1(input);
    }

    private static long calculateNorthernLoadPart2(List<List<Character>> grid){
        //assuming grid has already been tilted north
        long count = 0L;

        for(int i = 0; i < grid.get(0).size(); i++){
            for(int j = 0; j < grid.size(); j++){
                if (grid.get(j).get(i).equals('O')) {
                    count += grid.size() - j;
                }
            }
        }

        return count;
    }

    private static Long findLoadPart2(List<String> gridPrior) {

        List<List<Character>> grid = new ArrayList<>();
        List<Character> tempRow;
        for(String row : gridPrior){
            tempRow = new ArrayList<>();
            for(Character ch : row.toCharArray()){
                tempRow.add(ch);
            }

            grid.add(tempRow);
        }


        for (int k = 0; k < 1000; k++) {
//        for (int k = 0; k < 1000000000; k++) {
            //do north, west, south, east on grid, mutate grid, don't make new

            //roll north:
            //col: i, row: j
            for(int i = 0; i < grid.get(0).size(); i++){
                int recentBlockIndex = -1;
                for(int j = 0; j < grid.size(); j++){
                    if(grid.get(j).get(i) == '#'){
                        recentBlockIndex = j;
                    } else if (grid.get(j).get(i) == 'O'){
                        if(j > recentBlockIndex + 1){
                            grid.get(j).set(i, '.');
                            grid.get(recentBlockIndex + 1).set(i, 'O');
                        }

                        recentBlockIndex++;
                    }
                }
            }

            //roll west
            for(int i = 0; i < grid.size(); i++){
                int recentBlockIndex = -1;
                for(int j  =0; j < grid.get(i).size(); j++){
                    if(grid.get(i).get(j) == '#'){
                        recentBlockIndex = j;
                    } else if (grid.get(i).get(j) == 'O'){
                        if(j > recentBlockIndex + 1){
                            grid.get(i).set(j, '.');
                            grid.get(i).set(recentBlockIndex + 1, 'O');
                        }

                        recentBlockIndex++;
                    }
                }
            }

            //roll south
            for(int i = 0; i < grid.get(0).size(); i++){
                int recentBlockIndex = grid.size();

                for(int j = grid.size() -1 ; j > -1  ; j--){
                    if(grid.get(j).get(i) == '#'){
                        recentBlockIndex = j;
                    } else if (grid.get(j).get(i) == 'O'){
                        if(j < recentBlockIndex - 1){
                            grid.get(j).set(i, '.');
                            grid.get(recentBlockIndex -1).set(i, 'O');
                        }

                        recentBlockIndex--;
                    }
                }
            }

            //roll east
            for(int i = 0; i < grid.size(); i++){
                int recentBlockIndex = grid.get(i).size();

                for(int j = grid.get(i).size() -1; j > -1; j--){
                    if(grid.get(i).get(j) == '#'){
                        recentBlockIndex = j;
                    } else if (grid.get(i).get(j) == 'O'){
                        if(j <  recentBlockIndex -1){
                            grid.get(i).set(j, '.');
                            grid.get(i).set(recentBlockIndex - 1, 'O');
                        }

                        recentBlockIndex--;
                    }
                }
            }


            long northernLoad = calculateNorthernLoadPart2(grid);
            System.out.print(k + " : " + northernLoad);
        }

        return 0L;
    }



    public static Long part2() {
        List<String> inputLines = readFile();
        List<String> input = processInput(inputLines);
        return findLoadPart2(input);
    }

    public static void main(String[] args) {
        System.out.println("Part1 weight: " + part1());
        System.out.println("Part2 weight: " + part2());
    }
}
