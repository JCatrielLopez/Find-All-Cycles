import java.util.*;

public class Graph {

    private HashSet<Node> nodos;
    public ArrayList<Edge> edges;

    private int size;

    public Graph() {
        this.nodos = new HashSet<>();
        this.size = 0;
        this.edges = new ArrayList<>();
    }

    public void addElement(Node name) {
        if (!this.nodos.contains(name)) {
            this.nodos.add(name);
            size++;
        }
    }

    public void removeElement(Node name) {
        if (this.nodos.contains(name)) {
            this.nodos.remove(name);
            size--;
        }

        ArrayList<Edge> new_edges = new ArrayList<>();

        for (int i = 0; i < this.edges.size(); i++) {
            if (!edges.get(i).contains(name)) {
                new_edges.add(edges.get(i));
            }
        }
        this.edges = new_edges;

        //elimino de la lista de adyacencias de los otros nodos
        for (Node nodo : nodos) {
            nodo.removeAdy(name);

        }
    }

    public void addEdge(Node first, Node end) {
        if ((this.nodos.contains(first) && this.nodos.contains(end))) {
            first.addAdy(end);
            edges.add(new Edge(first, end));
        }
    }

    public boolean contains(String name) {
        for (Node nodo : nodos) {
            if (nodo.getName().equals(name))
                return true;
        }
        return false;
    }

    public Node getNodeByName(String name) {
        for (Node nodo : nodos) {
            if (nodo.getName().equals(name))
                return nodo;
        }
        return null;
    }


    public Node getNodeByID(int id) {
        for (Node nodo : nodos) {
            if (nodo.getId() == id)
                return nodo;
        }
        return null;
    }

    public HashSet<Node> getNodes() {
        return nodos;
    }


    public int cantNodos() {
        return size;
    }

    public int cantArcos() {
        return edges.size();
    }


}

class Edge {

    Node first;
    Node second;

    public Edge(Node first, Node second) {
        this.first = first;
        this.second = second;
    }

    public Node getNode1() {
        return first;
    }

    public Node getNode2() {
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

    public boolean contains(Node node) {
        return (first.equals(node) || second.equals(node));
    }

    @Override
    public String toString() {
        return "(" +
                "first=" + first +
                ", second=" + second +
                ')';
    }
}


