package advent.year2023.day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Snowverload {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day25/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2023/day25/input.txt";

    public static void main(String[] args) {
        Snowverload snowverload = new Snowverload();
        System.out.println("part1: " + snowverload.part1());
    }

    public long part1() {
        List<String> input = readFile();
        Graph graph = processInput(input);
        while (graph.karger() != 3) {
            // No 3 cuts, so reset.
            graph = processInput(input);
        }
        return Graph.result;
    }

    private List<String> readFile(){
        List<String> inputs = new ArrayList<>();
        try {
            ClassLoader classLoader = Snowverload.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                inputs.add(myReader.nextLine());
            }
        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return inputs;
    }

    private Graph processInput(List<String> inputLines) {

        Map<String, List<String>> map = new HashMap<>();

        for (String inputLine : inputLines) {
            String[] chunks = inputLine.split(":");
            String key = chunks[0].trim();
            Set<String> values = Arrays.stream(chunks[1].trim().split("\\s+")).map(String::trim).collect(Collectors.toSet());

            for (String value : values) {
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(value);

                if (!map.containsKey(value)) {
                    map.put(value, new ArrayList<>());
                }
                map.get(value).add(key);
            }
        }

        return Graph.builder().neighborMap(map).build();
    }
}
