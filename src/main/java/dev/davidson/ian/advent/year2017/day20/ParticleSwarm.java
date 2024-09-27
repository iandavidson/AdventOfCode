package dev.davidson.ian.advent.year2017.day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParticleSwarm {

    private static final String SAMPLE_PATH = "adventOfCode/2017/day20/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2017/day20/input.txt";

    public static void main(String [] args){
        ParticleSwarm particleSwarm = new ParticleSwarm();
        List<Particle> particles = readFile(SAMPLE_PATH);
    }

    private static List<Particle> readFile(final String filePath){
        List<Particle> particles = new ArrayList<>();

        ClassLoader cl = ParticleSwarm.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            int i = 0;
            while(scanner.hasNextLine()){
                particles.add(Particle.newParticle(scanner.nextLine(), i));
                i++;
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return particles;
    }

    public int part1(final List<Particle> particles){

    }
}
