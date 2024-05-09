package advent.year2022.day04;

public record Pair(Range first, Range second) {
    public Boolean fullyContainsPartner() {
        return (first.begin() <= second.begin() && first.end() >= second.end())
                || (second.begin() <= first.begin() && second.end() >= first.end());
    }

    public Boolean anyOverlap() {
        return (Math.max(first.begin(), second.begin()) <= Math.min(first.end(), second.end()));
    }
}
