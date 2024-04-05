package org.example.advent.year2023.day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Scratcher {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day04/input.txt";
    private static final String INPUT_PATH = "adventOfCode/2023/day04/input2.txt";


    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = Scratcher.class.getClassLoader();
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

    private static Ticket processTicket(String ticketRaw) {
        int colonIndex = ticketRaw.indexOf(":");
        int pipeIndex = ticketRaw.indexOf("|");

        Integer gameId = Integer.parseInt(ticketRaw.substring(5, colonIndex).trim());
        String winningNumbersRaw = ticketRaw.substring(colonIndex + 1, pipeIndex).trim();
        List<Long> winningNumbers = new ArrayList<>();
        for (String number : winningNumbersRaw.split("\\s+")) {
            winningNumbers.add(Long.parseLong(number));
        }
        Collections.sort(winningNumbers);

        String givenNumbersRaw = ticketRaw.substring(pipeIndex + 1).trim();
        List<Long> givenNumbers = new ArrayList<>();
        for (String number : givenNumbersRaw.split("\\s+")) {
            givenNumbers.add(Long.parseLong(number));
        }
        Collections.sort(givenNumbers);

        int matchCounter = getMatchCounter(givenNumbers, winningNumbers);
        return new Ticket(winningNumbers, givenNumbers, matchCounter, gameId);
    }

    private static int getMatchCounter(List<Long> givenNumbers, List<Long> winningNumbers) {
        int matchCounter = 0;
        int givenNumberIndex = 0;
        int winningNumberIndex = 0;
        while (givenNumberIndex < givenNumbers.size() && winningNumberIndex < winningNumbers.size()) {
            if (givenNumbers.get(givenNumberIndex) < winningNumbers.get(winningNumberIndex)) {
                givenNumberIndex++;
            } else if (Objects.equals(givenNumbers.get(givenNumberIndex), winningNumbers.get(winningNumberIndex))) {
                givenNumberIndex++;
                winningNumberIndex++;
                matchCounter++;
            } else if (givenNumbers.get(givenNumberIndex) > winningNumbers.get(winningNumberIndex)) {
                winningNumberIndex++;
            }
        }
        return matchCounter;
    }

    public static void main(String[] args) {
        Scratcher scratcher = new Scratcher();
        scratcher.part2();
    }

    public int part1() {
        List<String> rawTickets = readFile();

        List<Ticket> tickets = new ArrayList<>();
        Integer sum = 0;
        for (String row : rawTickets) {
            Ticket ticket = processTicket(row);
            tickets.add(ticket);
            System.out.println("To be added: " + ticket.getScore());
            sum += ticket.getScore();
            System.out.println("Sum: " + sum);
        }

        System.out.println("Final Sum: " + sum);
        return sum;
    }

    public int part2() {
        List<String> rawTickets = readFile();

        List<Ticket> tickets = new ArrayList<>();
        Map<Integer, Integer> ticketCounts = new HashMap<>();
        for (String row : rawTickets) {
            Ticket ticket = processTicket(row);
            tickets.add(ticket);
            ticketCounts.put(ticket.getGameId(), 1);
        }

        int sum = 0;

        for (Ticket ticket : tickets) {
            System.out.println("Iteration, ticket: " + ticket.getGameId() + ", with matches: " + ticket.getMatches());
            for (int i = 0; i < ticket.getMatches(); i++) {
                System.out.println("Updating entry: i + 1 + gameId:" + (i + 1 + ticket.getGameId()));
                if (ticketCounts.containsKey(i + 1 + ticket.getGameId())) {
                    ticketCounts.put(i + 1 + ticket.getGameId(), ticketCounts.get(i + 1 + ticket.getGameId()) + ticketCounts.get(ticket.getGameId()));
                }
            }
            sum += ticketCounts.get(ticket.getGameId());
        }
        System.out.println("Grand total: " + sum);
        return sum;
    }

    public static class Ticket {
        private final Integer matches;
        private List<Long> winningNumbers;
        private List<Long> givenNumbers;
        private Integer gameId;
        private Integer score;

        public Ticket() {
            matches = 0;
        }

        public Ticket(List<Long> winningNumbers, List<Long> givenNumbers, Integer matches, Integer gameId) {
            this.winningNumbers = winningNumbers;
            this.givenNumbers = givenNumbers;
            this.matches = matches;
            this.gameId = gameId;
            if (matches == null || matches == 0) {
                this.score = 0;
            } else {
                this.score = (int) Math.pow(2, matches - 1);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Ticket ticket = (Ticket) o;
            return Objects.equals(gameId, ticket.gameId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(gameId);
        }

        public List<Long> getWinningNumbers() {
            return winningNumbers;
        }

        public Integer getScore() {
            return score;
        }

        public List<Long> getGivenNumbers() {
            return givenNumbers;
        }

        public Integer getMatches() {
            return matches;
        }

        public Integer getGameId() {
            return gameId;
        }
    }
}
