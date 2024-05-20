package dev.davidson.ian.advent.year2015.day07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wire {
    private Integer value;
    private String label;
}
