package dev.davidson.ian.advent.year2017.day07;

import java.util.ArrayList;
import java.util.List;

public record Disk(String label, Long weight, List<String> heldDisks) {

    public static Disk newDisk(final String line) {
        //aaibd (174) -> vnivx, fcjoefo, gkvko, onnmq

        String[] parts = line.split("\\s+");
        String label = parts[0];
        Long weight = Long.parseLong(parts[1].substring(1, parts[1].length() - 1));
        List<String> heldDisks = new ArrayList<>();
        for(int i = 3; i < parts.length; i++){
            String disk = parts[i];
            if(disk.contains(",")){
                disk = disk.substring(0, disk.length()-1);
            }
            heldDisks.add(disk);
        }


        return new Disk(label, weight, heldDisks);
    }
}
