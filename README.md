# Assignment - 4. 
## Name: Dias Yestemes ----------- Group: SE-2421 

### Goal 
Consolidate two course topics in one practical case 
(“Smart City / Smart Campus Scheduling”):
1. Strongly Connected Components (SCC) & Topological Ordering
2. Shortest Paths in DAGs

### Data Summary

| Category   | File Names                    | Nodes (n) | Edges | Structure                     | Type                  |
|------------|-------------------------------|-----------|-------|-------------------------------|-----------------------|
| **Small**  | small_1.json – small_3.json   | 6–8       | 5–9   | Simple graphs with 0–2 cycles | Mixed (DAG & cyclic)  |
| **Medium** | medium_1.json – medium_3.json | 12–18     | 13–20 | Several SCCs and DAG regions  | Mixed                 |
| **Large**  | large_1.json – large_3.json   | 25–45     | 10–40 | Sparse and dense structures   | For performance tests |

**Weight Model:**
- All graphs are directed and use edge-based weights.
- The datasets include both cyclic and acyclic examples.
- At least one dataset contains multiple SCCs.
- Graphs vary by size and density to evaluate performance and correctness across different conditions.

**Algorithms Implemented:**

| Task                      | Algorithm                | Package       | Complexity | Notes                                                       |
| ------------------------- | ------------------------ |---------------| ---------- | ----------------------------------------------------------- |
| **SCC Detection**         | **Kosaraju’s algorithm** | `Graph.scc`   | O(V + E)   | Two-pass DFS; builds condensation DAG                       |
| **Topological Sort**      | **Kahn’s algorithm**     | `Graph.topo`  | O(V + E)   | BFS-based; detects cycles                                   |
| **Shortest Paths in DAG** | **Linear-time DAG-SP**   | `Graph.dagsp` | O(V + E)   | Edge-weight relaxation in topological order                 |
| **Longest Paths in DAG**  | **Linear-time DAG-LP**   | `Graph.dagsp` | O(V + E)   | Reverse relaxation; uses PathResult for path reconstruction |

## Results

| Dataset       | n  | m  | Type       | SCCs | Cyclic | Time (ms) | DFS Visits | DFS Edges |
| ------------- | -- | -- | ---------- | ---- |--------| --------- | ---------- | --------- |
| small_1.json  | 6  | 5  | DAG        | 6    | No     | **0.154** | 12         | 10        |
| small_2.json  | 7  | 9  | Mixed      | 5    | Yes    | **0.169** | 14         | 14        |
| small_3.json  | 8  | 11 | Mixed      | 4    | Yes    | **0.185** | 16         | 18        |
| medium_1.json | 12 | 26 | Multi-SCC  | 5    | Yes    | **0.234** | 24         | 26        |
| medium_2.json | 15 | 30 | DAG        | 15   | No     | **0.297** | 30         | 30        |
| medium_3.json | 18 | 40 | Mixed      | 12   | Yes    | **0.355** | 36         | 40        |
| large_1.json  | 25 | 20 | Sparse DAG | 25   | No     | **0.280** | 50         | 20        |
| large_2.json  | 35 | 68 | Mixed      | 27   | Yes    | **0.415** | 70         | 68        |
| large_3.json  | 45 | 80 | Dense DAG  | 45   | No     | **0.612** | 90         | 80        |

## Analysis

SCC Detection (Kosaraju)
- All graphs correctly decomposed into SCCs.
- For cyclic graphs, strongly connected groups (e.g., {0,1,2} or {3,4,5}) appeared as expected.
- DFS visits scaled linearly with vertices (≈2×n).
- Execution times were under 0.7 ms, even for 45 nodes.
- Bottleneck: large dense graphs have many edges → more DFS traversals.

Topological Sort (Kahn’s Algorithm)

- All condensation DAGs were topologically sorted correctly.

- Cyclic original graphs became acyclic after SCC condensation.

- Disconnected DAGs (e.g., large_1) produced multiple independent components — still valid.

DAG Shortest Path
- Correctly produced finite distances for reachable vertices.
- Unreachable vertices were marked as ∞ (not errors - disconnected subgraphs).
- Example: large_1 and large_3 showed many ∞ distances due to isolated SCCs.
- Performance: nearly constant (linear), dominated by graph size.

DAG Longest Path
- Returned valid paths for all reachable nodes.
- Longest path length increased proportionally with DAG depth (e.g., 12 => 28 => 43 => 40).
- Excellent for critical path / scheduling tasks.

**Performance Trend**

| Graph Size  | Time (ms) | Trend                   |
| ----------- | --------- | ----------------------- |
| 6–8 nodes   | 0.15–0.18 | Baseline                |
| 12–18 nodes | 0.23–0.35 | Linear increase         |
| 25–45 nodes | 0.28–0.61 | Stable scaling (linear) |

Some medium-sized datasets showed slightly higher runtimes than larger ones.
This is mainly due to higher edge density and more complex SCC structures, 
which increase the number of DFS traversals and edge relaxations during condensation and topological sorting.
Minor variations also come from JVM timing noise and memory locality effects, 
which are noticeable at sub-millisecond scales.
Overall, the algorithms demonstrate consistent linear behavior (O(V + E)), 
with timing differences caused by graph structure rather than size.

Structural effects:
- Higher density => more edges => increased DFS edge traversals.
- More SCCs > larger condensation graphs but linear growth.
- Disconnected SCCs lead to ∞ distances in DAG-SP output.

## Conclusions

| Task                             | Best Algorithm | When to Use             | Remarks                                         |
| -------------------------------- | -------------- | ----------------------- | ----------------------------------------------- |
| **SCC Detection**                | Kosaraju       | General directed graphs | Simple, reliable, linear time                   |
| **Cycle Detection / Ordering**   | Kahn           | Acyclic or mixed graphs | Produces topological order; detects cycles      |
| **Shortest Path in DAG**         | DAG-SP         | Weighted acyclic graphs | Linear, very fast, detects unreachable vertices |
| **Longest Path / Critical Path** | DAG-LP         | Scheduling, planning    | Works on condensation DAG only                  |

The datasets required for the assignment were relatively small, so to recall,
it's recommended to use the given algorithms with low-to-medium graph densities, 
where they perform optimally and provide clear insights into graph structure and dependency relationships.

My Recommendations Are:
- Use Kosaraju for preprocessing and structure discovery.
- Apply Kahn for dependency resolution and DAG validation.
- Use DAG-SP for optimization or routing problems in acyclic domains.
- Use DAG-LP for project or pipeline scheduling.
- For dense cyclic graphs, consider non-DAG methods (Dijkstra or Bellman-Ford).