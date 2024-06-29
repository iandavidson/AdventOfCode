package dev.davidson.ian.advent.year2022.day20;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Decrypter {
    private final List<Long> og;
    private final List<Element> altered;

    private final int cycles;

    public Decrypter(final List<Long> input, final int cycles) {
        this.cycles = cycles;
        this.og = input;
        List<Element> elements = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            elements.add(new Element(i, input.get(i)));
        }

        this.altered = elements;
    }

    private void reArrangeList() {
        for (int j = 0; j < cycles; j++) {
            for (int i = 0; i < og.size(); i++) {
                final Element elem = new Element(i, og.get(i));
                long currentIndex = altered.indexOf(elem);
                altered.remove(elem);
                currentIndex += elem.value();
                currentIndex = modulo(currentIndex);
                altered.add((int) currentIndex, elem);
            }
        }
    }

    private long modulo(final long index) {
        final int denominator = og.size() - 1;
        long modulo = index % denominator;
        if (modulo < 0) {
            modulo += denominator;
        }
        return modulo;
    }

    public long decrypt() {
        reArrangeList();

        final Element zero = new Element(og.indexOf(0L), 0);
        final int zeroIndex = altered.indexOf(zero);

        long first = altered.get((zeroIndex + 1000) % og.size()).value();
        long second = altered.get((zeroIndex + 2000) % og.size()).value();
        long third = altered.get((zeroIndex + 3000) % og.size()).value();

        log.info("{} + {} + {}", first, second, third);

        return first + second + third;
    }
}
