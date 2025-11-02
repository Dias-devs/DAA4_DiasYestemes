package Graph.core;

import java.util.*;

/**
 * Directed weighted graph implemented with adjacency lists.
 */
public class Graph {
    private final int n;
    private final List<List<Edge>> adj;

    public Graph(int n) {
        this.n = n;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    // Adds a directed weighted edge (u -> v).
    public void addEdge(int u, int v, long w) {
        checkVertex(u); checkVertex(v);
        adj.get(u).add(new Edge(u, v, w));
    }

    // Returns read-only neighbors of vertex u.
    public List<Edge> neighbors(int u) {
        checkVertex(u);
        return Collections.unmodifiableList(adj.get(u));
    }

    public int size() { return n; }

    public List<List<Edge>> adjacency() { return adj; }

    private void checkVertex(int v) {
        if (v < 0 || v >= n) throw new IndexOutOfBoundsException("Vertex: " + v);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph(n=").append(n).append(")\n");
        for (int i = 0; i < n; i++) {
            sb.append(i).append(": ").append(adj.get(i)).append('\n');
        }
        return sb.toString();
    }
}