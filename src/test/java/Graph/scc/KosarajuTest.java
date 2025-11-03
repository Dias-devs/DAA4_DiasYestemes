package Graph.scc;

import Graph.core.Graph;
import Graph.util.Metrics;
import Graph.util.MetricsInterface;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KosarajuTest {

    @Test
    public void testKosaraju_SimpleCycle() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1); // forms SCC
        g.addEdge(2, 3, 1);

        MetricsInterface m = new Metrics();
        Kosaraju scc = new Kosaraju(g, m);
        var result = scc.findSCCs();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.containsAll(List.of(0, 1, 2))));
    }

    @Test
    public void testKosaraju_EdgeCase_EmptyGraph() {
        Graph g = new Graph(0);
        Kosaraju scc = new Kosaraju(g, new Metrics());
        assertTrue(scc.findSCCs().isEmpty());
    }
}