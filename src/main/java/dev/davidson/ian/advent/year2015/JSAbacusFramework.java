package dev.davidson.ian.advent.year2015;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class JSAbacusFramework {

    private static final String SAMPLE_PATH = "adventOfCode/2015/day12/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2015/day12/input.txt";

    public static void main(String[] args) {
        JSAbacusFramework jsAbacusFramework = new JSAbacusFramework();
        log.info("Part1: {}", jsAbacusFramework.execute(false));
        log.info("Part2: {}", jsAbacusFramework.execute(true));
    }

    public long execute(boolean part2) {
        String json = readFile();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
            return computeSumPart1(jsonNode, part2);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private long computeSumPart1(final JsonNode jsonNode, final boolean part2) {
        long sum = 0L;
        JsonNodeType type = jsonNode.getNodeType();
        switch (type) {
            case ARRAY -> {
                for (Iterator<JsonNode> it = jsonNode.elements(); it.hasNext(); ) {
                    JsonNode child = it.next();
                    sum += computeSumPart1(child, part2);
                }
            }
            case NUMBER -> {
                sum += jsonNode.asInt();
            }
            case STRING -> {
                //don't care
            }
            case OBJECT -> {
                for (Map.Entry<String, JsonNode> child : jsonNode.properties()) {
                    if (part2 && child.getValue().asText().equals("red")) {
                        sum = 0;
                        break;
                    } else {
                        sum += computeSumPart1(child.getValue(), part2);
                    }
                }
            }
            case null, default -> {
                throw new IllegalStateException("🥸");
            }
        }

        return sum;
    }

    private String readFile() {
        ClassLoader cl = JSAbacusFramework.class.getClassLoader();
        File file = new File(cl.getResource(INPUT_PATH).getFile());
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getCause());
        }
    }
}
