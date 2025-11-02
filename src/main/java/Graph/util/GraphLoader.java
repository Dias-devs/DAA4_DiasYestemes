package Graph.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import Graph.core.Graph;
import java.io.File;

/**
 * Loads a graph from a JSON dataset in /data folder.
 */
public class GraphLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public record Loaded(Graph graph, int source) {
    }

    // Loads dataset from the JSON file
    public static Loaded loadFromFile(String path) {
        try {
            JsonNode root = MAPPER.readTree(new File(path));
            int n = root.get("n").asInt();
            Graph g = new Graph(n);
            for (JsonNode e : root.withArray("edges")) {
                g.addEdge(e.get("u").asInt(), e.get("v").asInt(), e.get("w").asLong());
            }
            int source = root.has("source") ? root.get("source").asInt() : 0;
            return new Loaded(g, source);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load graph: " + e.getMessage(), e);
        }
    }
}