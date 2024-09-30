package dev.davidson.ian.advent.year2017.day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElectromagneticMoat {
    private static final String INPUT_PATH = "adventOfCode/2017/day24/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day24/sample.txt";

    public static void main(String [] args){
        ElectromagneticMoat electromagneticMoat = new ElectromagneticMoat();
        List<BridgeComponent> components = readFile(SAMPLE_PATH);

        log.info("Part1: {}", electromagneticMoat.part1(components));
    }

    private static List<BridgeComponent> readFile(final String filePath){
        List<BridgeComponent> bridgeComponents = new ArrayList<>();

        ClassLoader cl = ElectromagneticMoat.class.getClassLoader();
        File file = new File(
                Objects.requireNonNull(cl.getResource(filePath)).getFile()
        );
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                bridgeComponents.add(new BridgeComponent(
                        Arrays.stream(scanner.nextLine().split("/"))
                                .mapToInt(Integer::parseInt).boxed()
                                .collect(Collectors.toList())
                ));
            }
        }catch (FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return  bridgeComponents;
    }


    public Integer part1(final List<BridgeComponent> components){
        List<>
    }
}
