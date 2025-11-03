package Graph.dagsp;

import Graph.topo.TopologicalSort;
import Graph.util.MetricsInterface;
import Graph.core.Graph;
import Graph.core.Edge;

import java.util.*;

/**
 * Computes longest paths in a DAG using edge weights.
 */
public class DAGLongestPath {
    private final Graph g;
    private final MetricsInterface m;

    public DAGLongestPath(Graph g, MetricsInterface m) { this.g = g; this.m = m; }

    public LongestPathResult longestFrom(int source) {
        List<Integer> topo = new TopologicalSort(g, m).topoSort();
        Map<Integer, Long> dist = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        for (int i = 0; i < g.size(); i++) dist.put(i, Long.MIN_VALUE / 4);
        dist.put(source, 0L);

        m.startTimer();
        for (int u : topo) {
            long du = dist.get(u);
            if (du == Long.MIN_VALUE / 4) continue;
            for (Edge e : g.neighbors(u)) {
                long nd = du + e.getW();
                if (nd > dist.get(e.getV())) {
                    dist.put(e.getV(), nd);
                    parent.put(e.getV(), u);
                }
            }
        }
        m.stopTimer();

        long best = Long.MIN_VALUE / 4; int bestNode = source;
        for (var e : dist.entrySet()) if (e.getValue() > best) { best = e.getValue(); bestNode = e.getKey(); }

        LinkedList<Integer> path = new LinkedList<>();
        int cur = bestNode; path.addFirst(cur);
        while (parent.containsKey(cur)) { cur = parent.get(cur); path.addFirst(cur); }
        return new LongestPathResult(best, path);
    }
}