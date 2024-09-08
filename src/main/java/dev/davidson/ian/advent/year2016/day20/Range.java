package dev.davidson.ian.advent.year2016.day20;

public record Range(Long start, Long finish) implements Comparable<Range> {

    public static Range newRange(final String line) {
        String[] parts = line.split("-");
        return new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    public static Range combineRange(final Range range1, final Range range2) {
        //method assumes range1.start <= range2.start
        assert range1.overlaps(range2);

        return new Range(
                Math.min(range1.start(), Math.min(range1.finish(), Math.min(range2.start(), range2.finish()))),
                Math.max(range1.start(), Math.max(range1.finish(), Math.max(range2.start(), range2.finish())))
        );
    }

    public boolean overlaps(final Range other) {
        return overlaps(other.start() - 1) || overlaps(other.finish());
    }

    public boolean overlaps(final Long value) {
        return value >= start && value <= finish;
    }

    @Override
    public int compareTo(Range o) {
        if (start.equals(o.start)) {
            return Long.signum(finish - o.finish());
        } else {
            return Long.signum(start - o.start());
        }
    }
}
