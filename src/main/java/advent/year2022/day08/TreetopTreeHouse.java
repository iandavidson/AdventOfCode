package advent.year2022.day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class TreetopTreeHouse {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day08/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day08/input.txt";

    public static void main(String[] args) {
        TreetopTreeHouse treetopTreeHouse = new TreetopTreeHouse();
        System.out.println("Part1: " + treetopTreeHouse.part1());
        System.out.println("Part2: " + treetopTreeHouse.part2());
    }


    public Long part1() {
        List<List<Integer>> grid = readInput();
        return countBoarderTrees(grid);
    }

    public Long part2() {
        List<List<Integer>> grid = readInput();

        long max = 0L;
        for(int i = 1; i < grid.size()-1; i++){
            for(int j = 1; j < grid.size()-1; j++){
                long score = calculateTreeHouseScore(i,j,grid);
//                System.out.println("row : " + i + "; col: " + j + " treeheight: "  + grid.get(i).get(j) + " ; score: " + score);
                max = Math.max(max, score);
            }
        }
        return max;
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
        Set<String> canSee = new HashSet<>();

        //from left to right
        for(int i = 0; i < grid.size(); i++){
            int max = -1;
            for(int j =0; j < grid.get(i).size(); j++){
                if(max < grid.get(i).get(j)){
                    canSee.add(i + ":" + j);
                    max = grid.get(i).get(j);
                }
            }
        }

        //from top to bottom
        for(int i = 0; i < grid.get(0).size(); i++){
            int max = -1;
            for(int j = 0; j < grid.size(); j++){
                if(max < grid.get(j).get(i)){
                    canSee.add(j + ":" + i);
                    max = grid.get(j).get(i);
                }
            }
        }


        //from right to left
        for(int i = 0; i < grid.size(); i++){
            int max = -1;
            for(int j = grid.get(0).size()-1; j > -1; j--){
                if(max < grid.get(i).get(j)){
                    max = grid.get(i).get(j);
                    canSee.add(i + ":" + j);
                }
            }
            //I still have the max
        }

        //from bottom to up
        for(int i = 0 ; i < grid.get(0).size(); i++){
            int max = -1;
            for(int j = grid.size()-1; j > -1; j--){
                if(max < grid.get(j).get(i)){
                    max = grid.get(j).get(i);
                    canSee.add(j + ":" + i);
                }
            }
        }

        return canSee.size();
    }

    private Long calculateTreeHouseScore(int row, int col, List<List<Integer>> grid){
        int currentHeight = grid.get(row).get(col);

        long left = 0L;
        for(int i = col-1; i > -1; i--){
            if(grid.get(row).get(i) < currentHeight){
                left++;
            }else {
                left++;
                break;
            }
        }

        long right = 0L;
        for(int i = col+1; i < grid.get(0).size(); i++){
            if(grid.get(row).get(i) < currentHeight){
                right++;
            } else {
                right++;
                break;
            }
        }

        long up = 0L;
        for(int i = row -1; i > -1; i--){
            if(grid.get(i).get(col) < currentHeight){
                up++;
            } else {
                up++;
                break;
            }
        }

        long down = 0L;
        for(int i = row+1; i < grid.size(); i++){
            if(grid.get(i).get(col) < currentHeight){
                down++;
            } else {
                down++;
                break;
            }
        }


        return left * right * up * down;
    }
}
