package Graph.dagsp;

import Graph.core.Graph;
import Graph.util.MetricsInterface;
import Graph.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ShortestPathTest {

    @Test
    public void testShortestPath_SimpleDAG() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 7);
        g.addEdge(2, 4, 3);

        MetricsInterface m = new Metrics();
        DAGShortestPath sp = new DAGShortestPath(g, m);
        Map<Integer, Long> dist = sp.shortestDistances(0);

        assertEquals(0L, (long) dist.get(0));
        assertEquals(2L, (long) dist.get(1));
        assertEquals(3L, (long) dist.get(2));
        assertEquals(9L, (long) dist.get(3));
        assertEquals(6L, (long) dist.get(4));
    }

    @Test
    public void testShortestPath_EdgeCase_SingleNode() {
        Graph g = new Graph(1);
        DAGShortestPath sp = new DAGShortestPath(g, new Metrics());
        Map<Integer, Long> dist = sp.shortestDistances(0);

        assertEquals(0L, dist.get(0));
    }
}