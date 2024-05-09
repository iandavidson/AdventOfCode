package advent.year2023.day22;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
@ToString
public class Slab implements Comparable<Slab> {
    private final Coordinate start;
    private final Coordinate end;

    public Slab clone(){
        return Slab.builder().start(new Coordinate(this.start)).end(new Coordinate(this.end)).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slab slab = (Slab) o;
        return Objects.equals(start, slab.start) && Objects.equals(end, slab.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    public void fall(int delta) {
        start.fall(delta);
        end.fall(delta);
    }

    public boolean willCollideAfterFall(Slab other) {
        boolean xIsPositive = Math.min(this.end.getX(), other.end.getX()) >= Math.max(this.start.getX(), other.start.getX());
        boolean yIsPositive = Math.min(this.end.getY(), other.end.getY()) >= Math.max(this.start.getY(), other.start.getY());
        boolean zIsPositive = this.getBottomZ() > other.getTopZ();

        return (xIsPositive && yIsPositive && zIsPositive);
    }

    public Integer getBottomZ() {
        return this.start.getZ();
    }

    public Integer getTopZ() {
        return this.end.getZ();
    }

    @Override
    public int compareTo(Slab other) {
        return this.start.compareTo(other.getStart());
    }
}
