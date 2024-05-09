package advent.year2022.day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static org.example.advent.year2022.day02.GAMESTATUS.LOSE;
import static org.example.advent.year2022.day02.GAMESTATUS.TIE;
import static org.example.advent.year2022.day02.GAMESTATUS.WIN;
import static org.example.advent.year2022.day02.THROW.PAPER;
import static org.example.advent.year2022.day02.THROW.ROCK;
import static org.example.advent.year2022.day02.THROW.SCISSOR;

public class RockPaperScissors {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day02/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day02/input.txt";

    private static final Map<THROW, Map<GAMESTATUS, THROW>> DECODED_THROW_MAP = new HashMap<>();

    static {
        {
            // the key is what opponent throws, based on the game status I will need to throw
            //eg. they throw ROCK, and I must win -> I throw Paper
            DECODED_THROW_MAP.put(ROCK, new HashMap<>());
            DECODED_THROW_MAP.get(ROCK).put(WIN, PAPER);
            DECODED_THROW_MAP.get(ROCK).put(LOSE, SCISSOR);
            DECODED_THROW_MAP.get(ROCK).put(TIE, ROCK);

            DECODED_THROW_MAP.put(PAPER, new HashMap<>());
            DECODED_THROW_MAP.get(PAPER).put(WIN, SCISSOR);
            DECODED_THROW_MAP.get(PAPER).put(LOSE, ROCK);
            DECODED_THROW_MAP.get(PAPER).put(TIE, PAPER);

            DECODED_THROW_MAP.put(SCISSOR, new HashMap<>());
            DECODED_THROW_MAP.get(SCISSOR).put(WIN, ROCK);
            DECODED_THROW_MAP.get(SCISSOR).put(LOSE, PAPER);
            DECODED_THROW_MAP.get(SCISSOR).put(TIE, SCISSOR);
        }
    }

    public static void main(String[] args) {
        RockPaperScissors rockPaperScissors = new RockPaperScissors();
        System.out.println("part1: " + rockPaperScissors.part1());
        System.out.println("part2: " + rockPaperScissors.part2());
    }

    public long part1() {
        List<Pair> games = processInput(true);
        Long count = 0L;
        for (Pair game : games) {
            count += game.computeScore();
        }

        return count;
    }


    public long part2() {
        List<Pair> games = processInput(false);
        Long count = 0L;
        for (Pair game : games) {
            count += game.computeScore();
        }

        return count;

    }

    public List<Pair> processInput(Boolean part1) {
        List<Pair> games = new ArrayList<>();
        try {
            ClassLoader classLoader = RockPaperScissors.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String[] chunks = myReader.nextLine().split("\\s+");
                if (part1) {
                    games.add(new Pair(THROW.LEFT.get(chunks[0]), THROW.RIGHT.get(chunks[1])));
                } else {
                    games.add(new Pair(THROW.LEFT.get(chunks[0]), DECODED_THROW_MAP.get(THROW.LEFT.get(chunks[0])).get(GAMESTATUS.STATUS_MAP.get(chunks[1]))));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return games;
    }

}
