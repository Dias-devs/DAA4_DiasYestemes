package Graph.scc;

import Graph.core.Edge;
import Graph.core.Graph;
import java.util.*;

/**
 * Builds condensation DAG from SCCs.
 */
public class CondGraph {

    public record Result(Graph dag, int[] compOf, List<List<Integer>> sccs) {
    }

    //Builds Condensations DAG using the SCC saved in List.
    public static Result build(Graph g, List<List<Integer>> sccs) {
        int n = g.size();
        int k = sccs.size();
        int[] compOf = new int[n];
        for (int i = 0; i < k; i++)
            for (int v : sccs.get(i)) compOf[v] = i;

        Graph dag = new Graph(k);
        Set<Long> seen = new HashSet<>();
        for (int u = 0; u < n; u++)
            for (Edge e : g.neighbors(u)) {
                int cu = compOf[u], cv = compOf[e.getV()];
                if (cu != cv) {
                    long key = (((long) cu) << 32) | (cv & 0xffffffffL);
                    if (seen.add(key)) dag.addEdge(cu, cv, e.getW());
                }
            }

        return new Result(dag, compOf, sccs);
    }
}