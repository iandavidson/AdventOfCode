package org.example.advent.year2022.day16;

import java.util.List;

public record Valve(String label, List<String> out, int flowRate) {
}
