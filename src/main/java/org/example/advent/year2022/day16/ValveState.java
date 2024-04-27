package org.example.advent.year2022.day16;

import java.util.Set;

public record ValveState(Set<String> activated, int timeLeft, long totalFLowed, String currentValveLabel) {
}
