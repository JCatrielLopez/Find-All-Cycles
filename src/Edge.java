import java.util.Objects;

public class Edge {

    int first;
    int second;

    public Edge(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return first == edge.first &&
                second == edge.second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public boolean contains(int id) {
        return (this.first==id || this.second==id);
    }
}

