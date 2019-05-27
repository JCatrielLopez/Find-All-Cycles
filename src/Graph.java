import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    HashMap<Node, ArrayList<Node>> elements;

    int size;
    int last;

    public Graph(){
        this.elements = new HashMap<>();
        this.size = 0;
        this.last = 0;
    }

    public void addElement(Node name){
        if (!this.elements.containsKey(name)) {
            name.setId(last);
            this.elements.putIfAbsent(name, new ArrayList<>());
            last++;
            size++;
        }
    }

    public void removeElement(Node name){
        if (this.elements.containsKey(name)){
            this.elements.remove(name);
            size--;
        }
    }

    public void addEdge(Node first, Node end){
        if ((this.elements.containsKey(first) && this.elements.containsKey(end))){
            if (!this.elements.get(first).contains(end))
                this.elements.get(first).add(end);
        }
    }

    public void removeEdge(Node first, Node end){
        if ((this.elements.containsKey(first) && this.elements.containsKey(end))){
            if (this.elements.get(first).contains(end))
                this.elements.get(first).remove(end);
        }
    }

    public int getSize(){
        return this.size;
    }

    public ArrayList<Node> getAdy(Node node){
        return this.elements.get(node);
    }

    public void print(){
        for (Node nodo: this.elements.keySet()){
            System.out.println("NODO: " + nodo.toString());
            System.out.println("ADYACENTES: ");
            for(Node adyacentes: this.elements.get(nodo)){
                System.out.println(adyacentes.toString());
            }
            System.out.println(" -------------------------- ");
        }
    }

    public boolean contains(String name){
        for(Node nodo: this.elements.keySet()){
            if (nodo.getName().equals(name))
                return true;
        }

        return false;
    }

    public Node get(String name){
        for(Node nodo: this.elements.keySet()){
            if (nodo.getName().equals(name))
                return nodo;
        }

        return null;
    }

}
