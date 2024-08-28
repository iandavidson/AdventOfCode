package dev.davidson.ian.advent.year2016.day10;

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
public class BalanceBots {

    private static final String INPUT_PATH = "adventOfCode/2016/day10/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2016/day10/sample.txt";

    private static final Integer LOW_TARGET_COMPARISON = 17;
    private static final Integer HIGH_TARGET_COMPARISON = 61;

    public static void main(String[] args) {
        BalanceBots balanceBots = new BalanceBots();
        balanceBots.execute();
    }

    public void execute() {
        Map<Integer, Bot> botMap = readFile();
        Map<Integer, Integer> outputMap = new HashMap<>();
        boolean part1Complete = false;
        boolean part2Complete = false;
        Integer targetBotId = -1;
        while (!part1Complete || !part2Complete) {
            for (Bot bot : botMap.values()) {

                if (!bot.canExecute()) {
                    continue;
                }


                if (compareConditionMet(bot)) {
                    targetBotId = bot.getId();
                    part1Complete = true;
                }

                if( outputsFull(outputMap)){
                    part2Complete = true;
                }


                int lowValue = bot.getLow();
                Rule rule = bot.getRule();
                switch (rule.lowReceiver()) {
                    case BOT -> {
                        botMap.get(rule.lowId()).addChip(lowValue);
                    }
                    case OUTPUT -> {
                        outputMap.put(rule.lowId(), lowValue);
                    }
                }

                int highValue = bot.getHigh();
                switch (rule.highReceiver()) {
                    case BOT -> {
                        botMap.get(rule.highId()).addChip(highValue);
                    }
                    case OUTPUT -> {
                        outputMap.put(rule.highId(), highValue);
                    }
                }

                bot.emptyChips();
            }
        }
        log.info("Part1: {}", targetBotId);
        log.info("Part2: {}", outputMap.get(0) * outputMap.get(1) * outputMap.get(2));
    }

    private boolean outputsFull(final Map<Integer, Integer> outputMap) {
        return outputMap.containsKey(0) && outputMap.containsKey(1) && outputMap.containsKey(2);
    }

    private boolean compareConditionMet(final Bot bot) {
        return bot.getHigh() == HIGH_TARGET_COMPARISON && bot.getLow() == LOW_TARGET_COMPARISON;
    }

    private Map<Integer, Bot> readFile() {

        List<String> inputLines = new ArrayList<>();
        ClassLoader cl = BalanceBots.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read in file");
        }

        return inputLinesToBots(inputLines);
    }

    private Map<Integer, Bot> inputLinesToBots(final List<String> inputLines) {
        Map<Integer, Bot> botIdMap = new HashMap<>();

        //initial bots for all that have initial chip assignment
        for (String line : inputLines) {
            String[] chunks = line.split("\\s+");
            if (chunks.length == 6) {
                //value 37 goes to bot 97
                int botId = Integer.parseInt(chunks[5]);
                if (botIdMap.containsKey(botId)) {
                    botIdMap.get(botId).addChip(Integer.parseInt(chunks[1]));
                } else {
                    botIdMap.put(botId, Bot.newBotChip(botId, Integer.parseInt(chunks[1])));
                }
            }
        }

        for (String line : inputLines) {
            String[] chunks = line.split("\\s+");
            if (chunks.length == 12) {
                //bot 31 gives low to bot 176 and high to bot 54
                int botId = Integer.parseInt(chunks[1]);
                if (botIdMap.containsKey(botId)) {
                    botIdMap.get(botId).setRule(new Rule(
                            Receiver.STRING_TO_RECEIVER.get(chunks[5]),
                            Integer.parseInt(chunks[6]),
                            Receiver.STRING_TO_RECEIVER.get(chunks[10]),
                            Integer.parseInt(chunks[11])
                    ));

                } else {
                    Bot bot = Bot.builder()
                            .id(botId)
                            .rule(
                                    new Rule(
                                            Receiver.STRING_TO_RECEIVER.get(chunks[5]),
                                            Integer.parseInt(chunks[6]),
                                            Receiver.STRING_TO_RECEIVER.get(chunks[10]),
                                            Integer.parseInt(chunks[11]))
                            )
                            .build();
                    botIdMap.put(botId, bot);
                }
            }
        }

        return botIdMap;
    }

}
