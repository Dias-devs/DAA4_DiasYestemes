package Graph.topo;

import Graph.util.MetricsInterface;
import Graph.core.Graph;
import Graph.core.Edge;

import java.util.*;

/**
 * Kahn's algorithm for topological sorting of a DAG.
 */
public class TopologicalSort {
    private final Graph g;
    private final MetricsInterface m;

    public TopologicalSort(Graph g, MetricsInterface m) {
        this.g = g;
        this.m = m;
    }

    public List<Integer> topoSort() {
        int n = g.size();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++)
            for (Edge e : g.neighbors(u)) indeg[e.getV()]++;

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) { q.add(i); m.incKahnPush(); }

        m.startTimer();
        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.remove();
            m.incKahnPop();
            order.add(u);
            for (Edge e : g.neighbors(u)) {
                int v = e.getV();
                indeg[v]--;
                if (indeg[v] == 0) { q.add(v); m.incKahnPush(); }
            }
        }
        m.stopTimer();

        if (order.size() != n) return Collections.emptyList(); // not a DAG
        return order;
    }
}