package dev.davidson.ian.advent.year2024.day09;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Block {

    private final int id;
    private final int length;
    private BLOCKTYPE blocktype;
}
