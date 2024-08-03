package dev.davidson.ian.advent.year2022.day23;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnstableDiffusion {

    private static final String SAMPLE_PATH = "adventOfCode/2022/day23/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day23/input.txt";

    private static final Map<Character, Tile> MAP = Map.of('#', Tile.ELF, '.', Tile.ELF);

    public static void main(String [] args){
        UnstableDiffusion unstableDiffusion = new UnstableDiffusion();
        log.info("Part1: {}", unstableDiffusion.part1());
    }

    /*

     */

    public long part1(){

    }




}
