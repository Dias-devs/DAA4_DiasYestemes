package Graph.scc;

import Graph.util.MetricsInterface;
import Graph.core.Edge;
import Graph.core.Graph;

import java.util.*;

/**
 * Implements <b>Kosaraju's algorithm</b> for detecting Strongly Connected Components (SCCs)
 * in a directed graph.
 * <p>
 * The algorithm runs in <b>O(V + E)</b> time and performs two DFS passes:
 * <ol>
 *     <li>First DFS on the original graph to compute vertices' finishing order.</li>
 *     <li>Second DFS on the transposed graph to extract the SCCs.</li>
 * </ol>
 * <p>
 * Metrics are recorded via {@link MetricsInterface}, including DFS visit counts, edge traversals,
 * and total execution time.
 */
public class Kosaraju {
    private final Graph graph;
    private final MetricsInterface metrics;
    private final int n;

    /**
     * Constructs a new SCCFinder for the given graph.
     *
     * @param graph   directed graph to analyze
     * @param metrics shared metrics collector for instrumentation
     */
    public Kosaraju(Graph graph, MetricsInterface metrics) {
        this.graph = graph;
        this.metrics = metrics;
        this.n = graph.size();
    }

    /**
     * Finds all strongly connected components (SCCs) in the graph using Kosaraju's algorithm.
     *
     * @return a list of SCCs, where each SCC is represented as a list of vertex indices
     */
    public List<List<Integer>> findSCCs() {
        metrics.startTimer();

        boolean[] visited = new boolean[n];
        Deque<Integer> order = new ArrayDeque<>();

        // Phase 1: DFS on original graph
        // We fill the stack with vertices in order of decreasing finishing time.
        for (int i = 0; i < n; i++) {
            if (!visited[i]) dfs1(i, visited, order);
        }

        // Phase 2: Transpose the graph
        Graph transposed = transpose();

        // Phase 3: DFS on transposed graph
        // Pop vertices from stack (in decreasing finish time) and explore SCCs.
        Arrays.fill(visited, false);
        List<List<Integer>> scc_list = new ArrayList<>();

        while (!order.isEmpty()) {
            int v = order.pop();
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                dfs2(transposed, v, visited, component);
                scc_list.add(component);
            }
        }

        metrics.stopTimer();
        return scc_list;
    }

    /**
     * First DFS pass: records vertices by finishing time.
     */
    private void dfs1(int u, boolean[] visited, Deque<Integer> order) {
        visited[u] = true;
        metrics.incDfsVisits();

        for (Edge e : graph.neighbors(u)) {
            metrics.addDfsEdges(1);
            int v = e.v();
            if (!visited[v]) dfs1(v, visited, order);
        }

        // When a vertex is fully explored, push it onto the stack.
        order.push(u);
    }

    /**
     * Second DFS pass: explores the transposed graph to extract a full SCC.
     */
    private void dfs2(Graph gt, int u, boolean[] visited, List<Integer> component) {
        visited[u] = true;
        component.add(u);
        metrics.incDfsVisits();

        for (Edge e : gt.neighbors(u)) {
            metrics.addDfsEdges(1);
            int v = e.v();
            if (!visited[v]) dfs2(gt, v, visited, component);
        }
    }

    /**
     * Creates and returns the transposed (reversed) version of the current graph.
     */
    private Graph transpose() {
        Graph gt = new Graph(n);
        for (int u = 0; u < n; u++) {
            for (Edge e : graph.neighbors(u)) {
                gt.addEdge(e.v(), e.u(), e.w());
            }
        }
        return gt;
    }
}