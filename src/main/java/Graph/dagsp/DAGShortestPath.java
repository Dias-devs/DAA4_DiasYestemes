package Graph.dagsp;

import Graph.topo.TopologicalSort;
import Graph.util.MetricsInterface;
import Graph.core.Graph;
import Graph.core.Edge;

import java.util.*;

/**
 * Computes shortest paths in a DAG using edge weights.
 */
public class DAGShortestPath {
    private final Graph g;
    private final MetricsInterface m;

    public DAGShortestPath(Graph g, MetricsInterface m) { this.g = g; this.m = m; }

    public Map<Integer, Long> shortestDistances(int source) {
        List<Integer> topo = new TopologicalSort(g, m).topoSort();
        Map<Integer, Long> dist = new HashMap<>();
        for (int i = 0; i < g.size(); i++) dist.put(i, Long.MAX_VALUE / 4);
        dist.put(source, 0L);

        m.startTimer();
        for (int u : topo) {
            long du = dist.get(u);
            if (du == Long.MAX_VALUE / 4) continue;
            for (Edge e : g.neighbors(u)) {
                long nd = du + e.getW();
                if (nd < dist.get(e.getV())) {
                    dist.put(e.getV(), nd);
                    m.incRelaxations();
                }
            }
        }
        m.stopTimer();
        return dist;
    }
}