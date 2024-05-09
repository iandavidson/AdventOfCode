package advent.year2022.day16;

import java.util.List;

public record ValveState(Valve currentValve, int minute, List<Valve> activated, long others) {
}
