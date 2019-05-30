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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
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
        return Objects.hash(name);
    }

//    @Override
//    public String toString() {
//        return this.id + " (" + this.name + ")";
//    }

    public String toString() {
        return this.name;
    }

    public boolean removeAdy(Node nodo) {
        boolean deleted=false;
        if (adyacentes.contains(nodo)) {
            deleted= adyacentes.remove(nodo);
        }
        return deleted;
    }

    public boolean addAdy(Node nodo) {
        return adyacentes.add(nodo);
    }

    public void print() {
        System.out.println("NODO: " + this.toString());
        System.out.println("ADYACENTES: ");
        for (Node ady : adyacentes) {
            System.out.println(ady.toString());
        }
    }

    public boolean containsAdy(Node n) {
        return adyacentes.contains(n);
    }
}
