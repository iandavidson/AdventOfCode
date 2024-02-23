package org.example.advent.year2023.two;


import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Puzzle {

    private static final String INPUT_PATH = "adventOfCode/day2/input.txt";
    private static final String SAMPLE_INPUT_PATH = "aventOfCode/day2/input-sample.txt";
    private static final int BLUE_MAX = 14;
    private static final int GREEN_MAX = 13;
    private static final int RED_MAX = 12;
    private static final String BLUE = "blue";
    private static final String RED = "red";
    private static final String GREEN = "green";

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = Puzzle.class.getClassLoader();
            File file = new File(classLoader.getResource(INPUT_PATH).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return input;
    }

    private static List<Game> interpretGames(List<String> inputLines) {
        List<Game> games = new ArrayList<>();
        for (String line : inputLines) {
//            System.out.println("Input: " + line);
            int gameId = Integer.parseInt(line.substring(4, line.indexOf(":")).trim());

            Game game = interpretGame(line.substring(line.indexOf(":") + 1).split(";"), gameId);
            if (game.isPossible()) {
                games.add(game);
            }

        }
        return games;
    }

    private static Game interpretGame(String[] rounds, int gameId) {
        boolean possible = true;
        int maxBlueFound = 0;
        int maxGreenFound = 0;
        int maxRedFound = 0;
        for (String round : rounds) {
            int blueCounter = 0;
            int redCounter = 0;
            int greenCounter = 0;
            int temp = 0;
//
            for (String colorValue : round.trim().split(",")) {
//                System.out.println("Raw pair: " + colorValue);
                String[] pair = colorValue.trim().split("\\s+");
                temp = Integer.parseInt(pair[0]); // number
                switch (pair[1]) {
                    case BLUE -> blueCounter += temp;
                    case GREEN -> greenCounter += temp;
                    case RED -> redCounter += temp;
                    default -> throw new IllegalArgumentException("whats going on");
                }
            }
            if (blueCounter > BLUE_MAX || greenCounter > GREEN_MAX || redCounter > RED_MAX) {
//                System.out.println("GameId: " + gameId + "Impossible round: " + round.trim());
                possible = false;
            }

            if (blueCounter > maxBlueFound) {
                maxBlueFound = blueCounter;
            }
            if (greenCounter > maxGreenFound) {
                maxGreenFound = greenCounter;
            }
            if (redCounter > maxRedFound) {
                maxRedFound = redCounter;
            }

        }
        return new Game(gameId, possible, maxBlueFound, maxGreenFound, maxRedFound);
    }

    public int part1() {
        List<String> inputLines = readFile();

        List<Game> games = interpretGames(inputLines);

        int sum = 0;
        for (Game game : games) {
            sum += game.getGameId();
        }

        return sum;
    }

    public int part2() {
        List<String> inputLines = readFile();

        List<Game> games = interpretGames(inputLines);

        int result = 0;
        for (Game game : games) {
            result += game.getMaxBlueFound() * game.getMaxGreenFound() * game.getMaxRedFound();
        }

        return result;
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle();
        System.out.println("Sum:  " + puzzle.part1());
        System.out.println("Product:  " + puzzle.part2());
    }

    @Getter
    public static class Game {
        private final int gameId;
        private final boolean possible;
        private final int maxBlueFound;
        private final int maxGreenFound;
        private final int maxRedFound;

        public Game() {
            gameId = 0;
            possible = true;
            maxRedFound = 0;
            maxGreenFound = 0;
            maxBlueFound = 0;
        }

        public Game(final int gameId, final boolean possible, final int maxBlueFound, final int maxGreenFound, final int maxRedFound) {
            this.gameId = gameId;
            this.possible = possible;
            this.maxBlueFound = maxBlueFound;
            this.maxGreenFound = maxGreenFound;
            this.maxRedFound = maxRedFound;
        }
    }
}
