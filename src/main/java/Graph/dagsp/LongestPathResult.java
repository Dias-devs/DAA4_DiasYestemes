package Graph.dagsp;

import java.util.List;

/**
 * Holds a path and its total weight.
 */
public class LongestPathResult {
    private final long distance;
    private final List<Integer> path;

    public LongestPathResult(long distance, List<Integer> path) {
        this.distance = distance;
        this.path = path;
    }

    public long getDistance() { return distance; }
    public List<Integer> getPath() { return path; }

    @Override
    public String toString() {
        return "PathResult {distance = " + distance + " | path = " + path + "}";
    }
}