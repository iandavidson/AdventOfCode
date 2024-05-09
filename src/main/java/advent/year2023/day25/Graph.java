package advent.year2023.day25;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@AllArgsConstructor
@Builder
@Data
public class Graph {

    private final Map<String, List<String>> neighborMap;
    public static long result = 0;

    public void addVertex(String label) {
        neighborMap.putIfAbsent(label, new ArrayList<>());
    }

    public void removeVertex(String label) {
        neighborMap.values().forEach(e -> e.remove(label));
        neighborMap.remove(label);
    }

    private void removeRemaining(String oldV1, String oldV2) {
        for (Map.Entry<String, List<String>> entry : neighborMap.entrySet()) {
            entry.getValue().remove(oldV1);
            entry.getValue().remove(oldV2);
        }

        removeVertex(oldV1);
        removeVertex(oldV2);
    }

    public void addEdge(String vertex1, String vertex2) {
        neighborMap.get(vertex1).add(vertex2);
        neighborMap.get(vertex2).add(vertex1);
    }

    public void removeEdge(String vertex1, String vertex2) {
        List<String> v1Neighbors = neighborMap.get(vertex1);
        List<String> v2Neighbors = neighborMap.get(vertex2);
        if (v1Neighbors != null)
            v1Neighbors.remove(vertex2);
        if (v2Neighbors != null)
            v2Neighbors.remove(vertex1);
    }

    public List<String> getneighborMap(String label) {
        return neighborMap.get(label);
    }

    private void combine(String vertex1, String vertex2) {
        // Remove the old edges between v1 and v2
        removeEdge(vertex1, vertex2);
        removeEdge(vertex2, vertex1);
        // Combines the other edges from v1 and v2 into one.
        List<String> edges1 = neighborMap.get(vertex1);
        List<String> edges2 = neighborMap.get(vertex2);
        Set<String> destinations = new HashSet<>();
        destinations.addAll(edges1);
        destinations.addAll(edges2);
        // Build the new label
        String newLabel = vertex1 + "-" + vertex2;
        addVertex(newLabel);
        for (String e : destinations) {
            addEdge(newLabel, e);
        }
        // Now remove every remaining reference to v1 or v2.
        removeRemaining(vertex1, vertex2);
    }

    private Graph getCopy() {
        Map<String, List<String>> cmap = new HashMap<>(neighborMap);
        return new Graph(cmap);
    }

    public long karger() {
        // Store the original configuration of this graph.
        Graph copy = getCopy();

        // First combine random vertices until we have only two vertices left.
        while (neighborMap.keySet().size() > 2) {
            Random random = new Random();
            int r = random.ints(0, neighborMap.keySet().size()).findAny().getAsInt();
            String v = neighborMap.keySet().toArray(new String[0])[r];
            int r2 = random.ints(0, neighborMap.get(v).size()).findAny().getAsInt();
            String v2 = neighborMap.get(v).get(r2);

            combine(v, v2);
        }

        // Now count the number of edges which did exist in the original graph but do
        // not exist in the current.
        // Combined vertices have the format <old vertex label1>-<old vertex
        // label2>-<old vertex label3> etc.
        String vertex1 = neighborMap.keySet().toArray(new String[0])[0];
        String vertex2 = neighborMap.keySet().toArray(new String[0])[1];
        int nrCuts = 0;
        // Just count old connections between the first vertex group and the second...
        for (String part : vertex1.split("-")) {
            List<String> vertices = copy.getneighborMap(part);
            for (String part2 : vertex2.split("-")) {
                if (vertices.contains(part2)) {
                    nrCuts++;
                }
            }
        }
        // ...and the other way around.
        for (String part : vertex2.split("-")) {
            List<String> vertices = copy.getneighborMap(part);
            for (String part2 : vertex1.split("-")) {
                if (vertices.contains(part2)) {
                    nrCuts++;
                }
            }
        }

        if (nrCuts == 3) {
            // Number of cuts is 3, so store the result
            long size1 = vertex1.split("-").length;
            long size2 = vertex2.split("-").length;
            result = size1 * size2;
        }
        return nrCuts;
    }


}
