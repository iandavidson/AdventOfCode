package advent.year2022.day03;

import lombok.Getter;

@Getter
public class Rucksack {
    private final String fullInput;
    private final BagCompartments bagCompartments;
    private final Character commonItem;

    public static Rucksack newRucksack(final String line) {
        String left = line.substring(0, line.length() / 2);
        String right = line.substring(line.length() / 2);
        BagCompartments bagCompartmentsTemp = new BagCompartments(left, right);

        Character commonItemTemp = null;
        for (Character ch : left.toCharArray()) {
            if (right.contains(ch.toString())) {
                commonItemTemp = ch;
            }
        }

        return new Rucksack(line, bagCompartmentsTemp, commonItemTemp);
    }

    private Rucksack(final String fullInput, final BagCompartments bagCompartments, final Character commonItem) {
        this.fullInput = fullInput;
        this.bagCompartments = bagCompartments;
        this.commonItem = commonItem;
    }

    public String getLeft() {
        return this.bagCompartments.left();
    }

    public String getRight() {
        return this.bagCompartments.right();
    }
}
