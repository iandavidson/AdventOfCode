package org.example.advent.year2023.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.Data;

public class CamelCards {

    private static final String INPUT_PATH = "adventOfCode/2023/day07/input.txt";
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2023/day07/input-sample.txt";

    private static final Map<Character, Integer> CHARACTER_KEY = new HashMap<Character, Integer>() {{
        put('J', 0);
        put('2', 1);
        put('3', 2);
        put('4', 3);
        put('5', 4);
        put('6', 5);
        put('7', 6);
        put('8', 7);
        put('9', 8);
        put('T', 9);
//        put('J', 10);
        put('Q', 11);
        put('K', 12);
        put('A', 13);
    }};

    /*
high card	(1)OnePair	(2)x	(3)x	(4)x	(5)x
one pair	(1)ThreeKind	(2)ThreeKind	(3)x	(4)x	(5)x
two pair	(1)full house	(2)FourKind	(3)x	(4)x	(5)x
threekind	(1)FourKind	(2)x	(3)FourKind	(4)x	(5)x
full house	(1)x	(2)FiveKind	(3)FiveKind	(4)x	(5)x
four of kind	(1)FiveKind	(2)x	(3)x	(4)FiveKind	(5)x
five of kind	(1)x	(2)x	(3)x	(4)x	(5)FiveKind
 */
    private static final Map<HandClassification, Map<Integer, HandClassification>> JOKER_HAND_CONVERSION = new HashMap<HandClassification, Map<Integer, HandClassification>>() {{
        put(HandClassification.HighCard, new HashMap<>() {{
            put(1, HandClassification.OnePair);
        }});
        put(HandClassification.OnePair, new HashMap<>() {{
            put(1, HandClassification.ThreeKind);
            put(2, HandClassification.ThreeKind);
        }});
        put(HandClassification.TwoPair, new HashMap<>() {{
            put(1, HandClassification.FullHouse);
            put(2, HandClassification.FourKind);
        }});
        put(HandClassification.ThreeKind, new HashMap<>() {{
            put(1, HandClassification.FourKind);
            put(3, HandClassification.FourKind);
        }});
        put(HandClassification.FullHouse, new HashMap<>() {{
            put(2, HandClassification.FiveKind);
            put(3, HandClassification.FiveKind);
        }});
        put(HandClassification.FourKind, new HashMap<>() {{
            put(1, HandClassification.FiveKind);
            put(4, HandClassification.FiveKind);
        }});
        put(HandClassification.FiveKind, new HashMap<>() {{
            put(5, HandClassification.FiveKind);
        }});
    }};


    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = CamelCards.class.getClassLoader();
            File file = new File(classLoader.getResource(INPUT_PATH).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    public static void main(String[] args) {
        CamelCards camelCards = new CamelCards();
//        System.out.println("final sum part1: " + camelCards.part1());
        System.out.println("final sum part1: " + camelCards.part2());

    }

    private List<Hand> processInput(List<String> inputs) {
        List<Hand> hands = new ArrayList<>();
        for (String inputLine : inputs) {
            String[] rawParts = inputLine.split("\\s+");
            Long bid = Long.parseLong(rawParts[1]);
            List<Card> cards = new ArrayList<>();
            for (Character ch : rawParts[0].toCharArray()) {
                cards.add(new Card(ch));
            }
            Hand hand = new Hand(cards, bid, rawParts[0]);
            hands.add(hand);
        }

        return hands;
    }

    public long part1() {
        List<String> inputs = readFile();
        List<Hand> hands = processInput(inputs);

        Collections.sort(hands);

        long sum = 0;

        for (int i = 0; i < hands.size(); i++) {
//            System.out.println("Current Sum: " + sum + " to be added: " + ((i + 1) * hands.get(i).getBid()));
            sum += (i + 1) * hands.get(i).getBid();

        }

        return sum;
    }

    public long part2() {
        List<String> inputs = readFile();
        List<Hand> hands = processInput(inputs);

        Collections.sort(hands);

        long sum = 0;

        for (int i = 0; i < hands.size(); i++) {
//            System.out.println("Current Sum: " + sum + " to be added: " + ((i + 1) * hands.get(i).getBid()));
            sum += (i + 1) * hands.get(i).getBid();

        }

        return sum;
    }


    public enum HandClassification {
        HighCard(0), OnePair(1), TwoPair(2), ThreeKind(3), FullHouse(4), FourKind(5), FiveKind(6);

        private final Integer score;

        HandClassification(final int score) {
            this.score = score;
        }

        public Integer getScore() {
            return this.score;
        }
    }

    @Data
    public static class Hand implements Comparable<Hand> {

        private List<Card> cards;
        private HandClassification handClassification;
        private HandClassification part2Classification;
        private Long bid;

        public Hand(List<Card> cards, Long bid, String rawInput) {
            this.cards = cards;
            this.bid = bid;


            Map<Character, Integer> characterCount = new HashMap<>();
            for (Card card : cards) {
                if (!characterCount.containsKey(card.getCard())) {
                    characterCount.put(card.getCard(), 1);
                } else {
                    characterCount.put(card.getCard(), characterCount.get(card.getCard()) + 1);
                }
            }

            //JKKK2 -> [ {J:1}, {K:3}, {2: 1}]
            //find key with highest count -> classify as:
            // [{K:4}, {2: 1}]

            if (characterCount.keySet().size() == 1) {
                this.handClassification = HandClassification.FiveKind;

            } else if (characterCount.keySet().size() == 2) {
                //4 of a kind
                //full house (3 of kind + pair)
                boolean foundSize4 = false;
                for (Character ch : characterCount.keySet()) {
                    if (characterCount.get(ch) == 4) {
                        foundSize4 = true;
                    }
                }
                this.handClassification = foundSize4 ? HandClassification.FourKind : HandClassification.FullHouse;

            } else if (characterCount.keySet().size() == 3) {
                boolean foundSize3 = false;
                for (Character ch : characterCount.keySet()) {
                    if (characterCount.get(ch) == 3) {
                        foundSize3 = true;
                    }
                }
                this.handClassification = foundSize3 ? HandClassification.ThreeKind : HandClassification.TwoPair;

            } else if (characterCount.keySet().size() == 4) {
                // 1 pair
                this.handClassification = HandClassification.OnePair;

            } else if (characterCount.keySet().size() == 5) {
                // high card
                this.handClassification = HandClassification.HighCard;
            } else {
                throw new IllegalStateException();
            }

            System.out.print("classification before: " + handClassification + " for raw input: " + rawInput);

            if (characterCount.containsKey('J')) {
                this.handClassification = JOKER_HAND_CONVERSION.get(this.handClassification).get(characterCount.get('J'));
            }

            System.out.println(" Updated classification: " + handClassification);


//            convert this.handClassification -> part2Classification
            //234JJ -> OnePair
            //23444 -> 3 of kind


        }

        @Override
        public int compareTo(Hand o) {
            //first compare handClassification
            if (handClassification.getScore() < o.getHandClassification().getScore()) {
                return -1;
            } else if (handClassification.getScore() > o.getHandClassification().getScore()) {
                return 1;
            }

            //then go from left to right comparing cards
            for (int i = 0; i < cards.size(); i++) {
                int compareVal = cards.get(i).compareTo(o.getCards().get(i));
                if (compareVal != 0) {
                    return compareVal;
                }
            }

            return 0;
        }
    }

    @Data
    @AllArgsConstructor
    public static class Card implements Comparable<Card> {
        private Character card;

        @Override
        public int compareTo(Card o) {
            return CHARACTER_KEY.get(this.card).compareTo(CHARACTER_KEY.get(o.getCard()));
        }
    }

}
