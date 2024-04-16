package org.example.advent.year2022.day11;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MonkeyCount implements Comparable<MonkeyCount> {
    private final Monkey monkey;

    @Builder.Default
    private Long count = 0L;

    public void incrementCount() {
        this.count++;
    }

    @Override
    public int compareTo(MonkeyCount o) {
        return (int) (count - o.getCount());
    }
}
