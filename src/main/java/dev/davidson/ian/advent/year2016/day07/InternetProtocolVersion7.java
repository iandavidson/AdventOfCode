package dev.davidson.ian.advent.year2016.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternetProtocolVersion7 {

    private static final String SAMPLE_PATH = "adventOfCode/2016/day07/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2016/day07/input.txt";

    public static void main(String [] args){
        InternetProtocolVersion7 internetProtocolVersion7 = new InternetProtocolVersion7();
        internetProtocolVersion7.execute();
    }

    public void execute(){
        List<IP7> ipList = readFile();
        log.info("Part1: {}", ipList.stream().filter(IP7::supportsTLS).count());
        log.info("Part2: {}", ipList.stream().filter(IP7::supportsSSL).count());
    }

    private List<IP7> readFile(){
        List<IP7> ip7s = new ArrayList<>();

        ClassLoader cl = InternetProtocolVersion7.class.getClassLoader();
        File file  = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                ip7s.add(IP7.newIP7(scanner.nextLine()));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file");
        }

        return ip7s;
    }
}
