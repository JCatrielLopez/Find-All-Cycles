import java.util.HashSet;
import java.util.Objects;

public class Node {

    private int id;
    private String name;
    static int cont = 0;
    public HashSet<Node> adyacentes;

    public Node(String name) {
        this.id = cont;
        this.name = name;
        cont++;
        adyacentes = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void removeAdy(Node nodo) {
        adyacentes.remove(nodo);
    }

    public void addAdy(Node nodo) {
        adyacentes.add(nodo);
    }

    public boolean containsAdy(Node n) {
        return adyacentes.contains(n);
    }

    public HashSet<Node> getAdyacentes(){
        return adyacentes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return name.equals(node.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
