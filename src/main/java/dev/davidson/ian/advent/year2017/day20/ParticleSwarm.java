package dev.davidson.ian.advent.year2017.day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParticleSwarm {

    private static final String SAMPLE_PATH = "adventOfCode/2017/day20/sample.txt";
    private static final String SAMPLE_PART_2_PATH = "adventOfCode/2017/day20/sample-2.txt";
    private static final String INPUT_PATH = "adventOfCode/2017/day20/input.txt";

    public static void main(String[] args) {
        ParticleSwarm particleSwarm = new ParticleSwarm();
        List<Particle> particles = readFile(INPUT_PATH);
        log.info("Part1: {}", particleSwarm.part1(new ArrayList<>(particles)));
        log.info("Part2: {}", particleSwarm.part2(new ArrayList<>(particles)));
    }

    private static List<Particle> readFile(final String filePath) {
        List<Particle> particles = new ArrayList<>();

        ClassLoader cl = ParticleSwarm.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {
                particles.add(Particle.newParticle(scanner.nextLine(), i));
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return particles;
    }

    public int part1(final List<Particle> particles) {

        Map<Integer, Long> map = new HashMap<>();
        for (int i = 0; i < 1_000; i++) {
            for (int j = 0; j < particles.size(); j++) {
                map.put(particles.get(j).id(), particles.get(j).getTotalManhattanDistance());
                particles.set(j, particles.get(j).update());
            }

            particles.sort((a, b) -> Long.signum(map.get(a.id()) - map.get(b.id())));
        }

        return particles.getFirst().id();
    }

    public int part2(final List<Particle> particles) {
        Map<String, List<Particle>> coordToIdMap = new HashMap<>();
        int sinceCollision = 0;

        while (sinceCollision < 1000) {
            coordToIdMap.clear();

            for (int j = 0; j < particles.size(); j++) {
                String coord = particles.get(j).toCoordinateId();
                coordToIdMap.putIfAbsent(coord, new ArrayList<>());
                coordToIdMap.get(coord).add(particles.get(j));

                particles.set(j, particles.get(j).update());
            }

            for (List<Particle> collisionGroup : coordToIdMap.values()) {
                if (collisionGroup.size() > 1) {
                    particles.removeAll(collisionGroup);
                    sinceCollision = 0;
                }
            }

            sinceCollision++;
        }

        return particles.size();
    }
}
