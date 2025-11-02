package Graph.core;

/**
 * Represents a directed weighted edge in the graph.
 */
public record Edge(int u, int v, long w) {

    @Override
    public String toString() {
        return String.format("(%d -> %d, w=%d)", u, v, w);
    }
}