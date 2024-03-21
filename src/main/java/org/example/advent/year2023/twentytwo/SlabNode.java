package org.example.advent.year2023.twentytwo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class SlabNode {

    private Slab value;

    @Builder.Default
    private List<Slab> children = new ArrayList<>();

    @Builder.Default
    private Slab parent = null;


}
