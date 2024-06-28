package dev.davidson.ian.advent.year2022.day20;

import java.util.ArrayList;
import java.util.List;

public class Decrypter {
    private final List<Long> og;
    private final List<Element> altered;

    private final int cycles;

    public Decrypter(final List<Long> input, final int cycles){
        this.cycles = cycles;
        this.og = input;
        List<Element> elements = new ArrayList<>();

        for(int i = 0; i < input.size(); i++){
            elements.add(new Element(i, input.get(i)));
        }

        this.altered = elements;
    }

    private void reArrangeList()
    {
        for (int j = 0; j < cycles; j++)
        {
            for (int i = 0; i < og.size(); i++)
            {
                final Element elem = new Element(i, og.get(i));
                long currentIndex = altered.indexOf(elem);
                altered.remove(elem);
                currentIndex += elem.value();
                currentIndex = modulo(currentIndex);
                altered.add((int) currentIndex, elem);
            }
        }
    }

    private long modulo(final long index)
    {
        final int denominator = og.size() - 1;
        long modulo = index % denominator;
        if (modulo < 0)
        {
            modulo += denominator;
        }
        return modulo;
    }

    public long decrypt()
    {
        reArrangeList();

        final Element zeroElem = new Element(og.indexOf(0), 0);
        final int zeroNodeNewIndex = altered.indexOf(zeroElem);

        return altered.get((zeroNodeNewIndex+1000) % og.size()).value()
                + altered.get((zeroNodeNewIndex+2000) % og.size()).value()
                + altered.get((zeroNodeNewIndex+3000) % og.size()).value();
    }

}
