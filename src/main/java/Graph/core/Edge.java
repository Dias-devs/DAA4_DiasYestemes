package Graph.core;

/**
 * Represents a directed weighted edge in the graph.
 */
public class Edge {
    private final int u;
    private final int v;
    private final long w;

    public Edge(int u, int v, long w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public int getU() { return u; }
    public int getV() { return v; }
    public long getW() { return w; }

    @Override
    public String toString() {
        return String.format("(%d -> %d, w=%d)", u, v, w);
    }
}