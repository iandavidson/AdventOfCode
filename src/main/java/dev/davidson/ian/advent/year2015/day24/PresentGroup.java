package dev.davidson.ian.advent.year2015.day24;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PresentGroup implements Cloneable {
    private List<Integer> presents;
    private long product;
    private long sum;

    public PresentGroup() {
        presents = new ArrayList<>();
        product = 1;
        sum = 0;
    }

    public Long sumIfPresentAdded(Integer present) {
        return sum + present;
    }

    public void addPresent(Integer present) {
        presents.add(present);
        this.product *= present;
        this.sum += present;
    }

    @Override
    public PresentGroup clone() {
        try {
            PresentGroup clone = (PresentGroup) super.clone();
            clone.setPresents(new ArrayList<>(this.presents));

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
