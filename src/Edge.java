import java.util.Objects;

public class Edge {

    Node first;
    Node second;

    public Edge(Node first, Node second) {
        this.first = first;
        this.second = second;
    }

    public Node getFirst() {
        return first;
    }

    public Node getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return first.equals(edge.first) &&
                second.equals(edge.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public boolean contains(Node node){
        return (first.equals(node)|| second.equals(node));
    }
}

