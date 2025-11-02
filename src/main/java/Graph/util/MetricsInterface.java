package Graph.util;

/**
 * Common metrics interface for algorithm instrumentation.
 * Tracks operation counts and timing using System.nanoTime().
 */
public interface MetricsInterface {

    // SCC (Tarjan) counters
    void incDfsVisits();
    void addDfsEdges(long count);

    // Kahn’s Topological Sort counters
    void incKahnPush();
    void incKahnPop();

    // DAG Shortest/Longest Path counters
    void incRelaxations();

    // Timer control
    void startTimer();
    void stopTimer();
    long getElapsedNanos();
    double getElapsedMillis();

    // Reporting
    String summary();
}