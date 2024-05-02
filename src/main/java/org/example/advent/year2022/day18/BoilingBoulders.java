package org.example.advent.year2022.day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class BoilingBoulders {


    private static final String SAMPLE_PATH = "adventOfCode/2022/day18/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day18/input.txt";
    public static void main(String [] args){
        BoilingBoulders boilingBoulders = new BoilingBoulders();
        System.out.println("Part1: " + boilingBoulders.part1());
    }

    public long part1(){
        List<Boulder> boulders = readFile();

        long count = 0L;

        for(int i =0; i < boulders.size(); i++){
            for(int s = 0; s < 6; s++){
                for(int j = 0; j < boulders.size(); j++){
                    if(j != i && boulders.get(i).isNeighbor(boulders.get(j))){

                    }
                }
            }
        }


    }

    private List<Boulder> readFile(){
        List<Boulder> boulders = new ArrayList<>();

        ClassLoader cl = BoilingBoulders.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(SAMPLE_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String [] parts = scanner.nextLine().split(",");
                boulders.add(new Boulder(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return boulders;
    }
}
