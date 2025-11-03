package Graph.topo;

import Graph.core.Graph;
import Graph.util.MetricsInterface;
import Graph.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TopoSortTest {

    @Test
    public void testTopoSort_SimpleDAG() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(0, 3, 1);

        MetricsInterface m = new Metrics();
        TopologicalSort topo = new TopologicalSort(g, m);
        List<Integer> order = topo.topoSort();

        assertEquals(4, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(3));
    }

    @Test
    public void testTopoSort_CyclicGraph() {
        Graph g = new Graph(3);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);

        TopologicalSort topo = new TopologicalSort(g, new Metrics());
        List<Integer> order = topo.topoSort();

        // Should return empty list because graph is cyclic
        assertTrue(order.isEmpty());
    }
}