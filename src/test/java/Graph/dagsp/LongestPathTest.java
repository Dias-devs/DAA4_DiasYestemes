package Graph.dagsp;

import Graph.core.Graph;
import Graph.util.MetricsInterface;
import Graph.util.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LongestPathTest {

    @Test
    public void testLongestPath_SimpleDAG() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 3);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 3, 4);
        g.addEdge(1, 4, 5);

        MetricsInterface m = new Metrics();
        DAGLongestPath lp = new DAGLongestPath(g, m);
        LongestPathResult result = lp.longestFrom(0);

        assertNotNull(result);
        assertTrue(result.getDistance() > 0);
        assertFalse(result.getPath().isEmpty());
    }

    @Test
    public void testLongestPath_EdgeCase_Disconnected() {
        Graph g = new Graph(3);
        g.addEdge(0, 1, 2);
        // Node 2 disconnected

        DAGLongestPath lp = new DAGLongestPath(g, new Metrics());
        LongestPathResult res = lp.longestFrom(0);

        assertNotNull(res);
        assertTrue(res.getDistance() >= 0);
    }
}
