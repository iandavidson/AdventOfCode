package dev.davidson.ian.advent.year2017.day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HexFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiskDefragmentation {

    private static final String INPUT_PATH = "adventOfCode/2017/day14/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day14/sample.txt";


    public static void main(String[] args) {
        DiskDefragmentation diskDefragmentation = new DiskDefragmentation();
        String input = readFile(SAMPLE_PATH);
        log.info("Part1: {}", diskDefragmentation.part1(input));

    }

    private static String readFile(final String filePath) {
        ClassLoader cl = DiskDefragmentation.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided path");
        }
    }

    private Integer part1(final String key){
        int count = 0;
        for(int i = 0 ; i < 128; i ++){
            String rowKey = key + "-" + i;
            log.info("row: {}", rowKey);
            String hexString = HexFormat.of().formatHex(rowKey.getBytes());
            //TODO: looks like the hex string I generate isn't 32 chars, this throws off answer for sample input
            log.info("row as hex: {}", hexString);
            String binary = new BigInteger(hexString, 16).toString(2);
            log.info("row as binary: {}", binary);
            for(char ch : binary.toCharArray()){
                if(ch == '1'){
                    count++;
                }
            }
        }

        return count;
    }

//    static String hexToBin(String s) {
//        return new BigInteger(s, 16).toString(2);
//    }

}
