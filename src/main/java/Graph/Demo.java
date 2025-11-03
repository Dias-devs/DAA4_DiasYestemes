package Graph;

import Graph.dagsp.DAGLongestPath;
import Graph.dagsp.DAGShortestPath;
import Graph.scc.CondGraph;
import Graph.scc.Kosaraju;
import Graph.topo.TopologicalSort;
import Graph.util.GraphLoader;
import Graph.core.Graph;
import Graph.util.Metrics;
import Graph.util.MetricsInterface;


public class Demo {
    public static void main(String[] args) {
        String file = (args.length > 0) ? args[0] : "src/main/java/Graph/data/small_1.json";
        var loaded = GraphLoader.loadFromFile(file);
        Graph g = loaded.graph();

        MetricsInterface m = new Metrics();

        System.out.println("--- Loaded Graph ---");
        System.out.println(g);

        // SCC
        Kosaraju scc = new Kosaraju(g, m);
        var sccs = scc.findSCCs();
        System.out.println("----------------------------\n");
        System.out.println("SCCs: " + sccs);
        System.out.println(m.summary());

        // Condensation DAG
        var cond = CondGraph.build(g, sccs);
        System.out.println("----------------------------\n");
        System.out.println("Condensation DAG:\n" + cond.dag());

        // Topological Order
        TopologicalSort topo = new TopologicalSort(cond.dag(), m);
        var order = topo.topoSort();
        System.out.println("----------------------------\n");
        System.out.println("Topological Order: " + order);

        // DAG Shortest Path
        DAGShortestPath sp = new DAGShortestPath(cond.dag(), m);
        System.out.println("Shortest Distances: " + sp.shortestDistances(0));

        // DAG Longest Path
        DAGLongestPath lp = new DAGLongestPath(cond.dag(), m);
        System.out.println("Longest Path: " + lp.longestFrom(0));
    }
}