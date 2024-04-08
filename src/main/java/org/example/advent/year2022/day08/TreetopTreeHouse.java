package org.example.advent.year2022.day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class TreetopTreeHouse {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day08/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day08/input.txt";

    public static void main(String[] args) {
        TreetopTreeHouse treetopTreeHouse = new TreetopTreeHouse();
        System.out.println("Part1: " + treetopTreeHouse.part1());
    }


    public Long part1() {
        List<List<Integer>> grid = readInput();
//        567 -> too low
        return countBoarderTrees(grid);
    }

    private List<List<Integer>> readInput() {
        List<String> lines = new ArrayList<>();
        try {
            ClassLoader classLoader = TreetopTreeHouse.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        List<List<Integer>> grid = new ArrayList<>();
        for(String line : lines){
            List<Integer> row = new ArrayList<>();
            for(Character ch : line.toCharArray()){
                row.add(ch - '0');
            }
            grid.add(row);
        }

        return grid;
    }

    private long countBoarderTrees(List<List<Integer>> grid){
        long count = 0L;

        //from left to right
        for(List<Integer> row : grid){
            long tempCount = 1;
            for(int i =1; i < row.size(); i++){
                if(row.get(i) > row.get(i-1)){
                    tempCount++;
                }else {
                    break;
                }
            }
            count += tempCount;
        }


        //from top to bottom
        for(int i = 0; i < grid.get(0).size(); i++){
            long tempCount = 1;
            for(int j = 1; j <grid.size(); j++){
                if(grid.get(j).get(i) > grid.get(j-1).get(i)){
                    tempCount++;
                }else{
                    break;
                }
            }
            count += tempCount;
        }


        //from right to left
        for(int i = 0; i < grid.size(); i++){
            long tempCount = 1;
            for(int j = grid.get(0).size()-2; j > -1; j--){
                if(grid.get(i).get(j) <  grid.get(i).get(j-1)){
                    tempCount++;
                } else {
                    break;
                }
            }
            count+= tempCount;
        }

        //from bottom to up
        for(int i = 0 ; i < grid.get(0).size(); i++){
            long tempCount = 1;
            for(int j = grid.size()-2; j > -1; j--){
                if(grid.get(j).get(i) < grid.get(j-1).get(i)){
                    tempCount++;
                }else{
                    break;
                }
            }
            count += tempCount;
        }

        return count;
    }
}
