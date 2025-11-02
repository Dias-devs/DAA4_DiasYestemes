package Graph.core;

/**
 * Basic vertex representation.
 */
public record Node(int id) {

    @Override
    public String toString() {
        return "Node(" + id + ")";
    }
}