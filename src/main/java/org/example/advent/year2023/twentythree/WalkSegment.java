package org.example.advent.year2023.twentythree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class WalkSegment {
    List<PathSegment> priorPath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalkSegment that = (WalkSegment) o;
        return Objects.equals(priorPath, that.priorPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priorPath);
    }

    public long rollupPath(){
        long count = 0L;
        for(PathSegment pg : this.priorPath){
            count += pg.getLength();
        }

        return count;
    }

    //call this method when we hit a fork in the road
    public List<WalkSegment> diverge(List<PathSegment> pathSegments){
        List<WalkSegment> newWalkSegments = new ArrayList<>();

        for(PathSegment pg: pathSegments){
            List<PathSegment> tempSegments = new ArrayList<>(this.priorPath);
            tempSegments.add(pg);
            newWalkSegments.add(WalkSegment.builder().priorPath(tempSegments).build());
        }

        return newWalkSegments;
    }
}
