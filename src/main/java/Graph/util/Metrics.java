package Graph.util;

/**
 * Implementation of MetricsInterface.
 * Uses System.nanoTime() for timing and simple long counters.
 */
public class Metrics implements MetricsInterface {
    private long dfsVisits = 0;
    private long dfsEdges = 0;
    private long kahnPush = 0;
    private long kahnPop = 0;
    private long relaxations = 0;

    private long startTime = 0;
    private long endTime = 0;
    private boolean running = false;

    @Override
    public void incDfsVisits() { dfsVisits++; }

    @Override
    public void addDfsEdges(long count) { dfsEdges += count; }

    @Override
    public void incKahnPush() { kahnPush++; }

    @Override
    public void incKahnPop() { kahnPop++; }

    @Override
    public void incRelaxations() { relaxations++; }

    @Override
    public void startTimer() {
        running = true;
        startTime = System.nanoTime();
    }

    @Override
    public void stopTimer() {
        if (running) {
            endTime = System.nanoTime();
            running = false;
        }
    }

    @Override
    public long getElapsedNanos() {
        return (running ? System.nanoTime() - startTime : endTime - startTime);
    }

    @Override
    public double getElapsedMillis() {
        return getElapsedNanos() / 1_000_000.0;
    }

    @Override
    public String summary() {
        return String.format("""
            Metrics Summary:
              Time: %.3f ms
              DFS Visits: %d
              DFS Edges: %d
              Kahn Pushes: %d
              Kahn Pops: %d
              Relaxations: %d
            """, getElapsedMillis(), dfsVisits, dfsEdges, kahnPush, kahnPop, relaxations);
    }

    @Override
    public String toString() { return summary(); }
}
