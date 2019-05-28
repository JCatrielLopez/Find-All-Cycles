import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Graph {

    private HashMap<Node, ArrayList<Node>> elements;
    private ArrayList<Edge> edges;

    private int size;

    public Graph(){
        this.elements = new HashMap<>();
        this.size = 0;
        this.edges = new ArrayList<>();
    }

    public void addElement(Node name){
        if (!this.elements.containsKey(name)) {
            this.elements.putIfAbsent(name, new ArrayList<>());
            size++;
        }
    }

    public void removeElement(Node name){
        if (this.elements.containsKey(name)){
            this.elements.remove(name);
            size--;
        }

        for(Edge edge: this.edges){
            if (edge.contains(name)){
                this.edges.remove(edge);
            }
        }
    }

    public void addEdge(Node first, Node end){
        if ((this.elements.containsKey(first) && this.elements.containsKey(end))){
            if (!this.elements.get(first).contains(end)) {
                this.elements.get(first).add(end);
                this.edges.add(new Edge(first, end));
            }
        }
    }

    public void removeEdge(Edge edge){
        if (this.edges.contains(edge)){
            this.edges.remove(edge);
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

    public int edgesSize(){
        return this.edges.size();
    }

    public Node getNode(int id){
        for(Node nodo: this.elements.keySet()){
            if (nodo.getId()==id)
                return nodo;
        }
        return null;
    }

    public ArrayList<Node> getNodes(){
        ArrayList<Node> nodes= new ArrayList<>();
        for(Node node: elements.keySet())
            nodes.add(node);
        return nodes;
    }

    public ArrayList<Edge> getEdges(){
        return (ArrayList<Edge>) this.edges.clone();
    }

    public ArrayList<ArrayList<Node>> find_all_cycles(int max){
        ArrayList<Node> completed = new ArrayList<>();
        ArrayList<Node> visiting = new ArrayList<>();
        ArrayList<ArrayList<Node>> result = new ArrayList<>();
        for(Node node: this.elements.keySet()){
            result.addAll(this.find_cycles(node, max, completed, visiting));
        }

        return result;
    }

    private ArrayList<ArrayList<Node>> find_cycles(Node node, int max, ArrayList<Node> completed, ArrayList<Node> visiting){

        ArrayList<ArrayList<Node>> result = new ArrayList<>();
        ArrayList<Node> current_cycle = new ArrayList<>();

        visiting.add(node);
        HashSet<Node> stack = new HashSet<>();

        stack.add(node);

        for(Node n: stack){
            stack.remove(n);

            if(!completed.contains(n)){
                if (visiting.contains(n)){
                    if ((3 < current_cycle.size()) && (current_cycle.size() < max)) {
                        System.out.println("Agrego un ciclo");
                        result.add(current_cycle);
                    }
                    current_cycle = new ArrayList<>();
                }
                else{
                    visiting.add(n);
                    current_cycle.add(n);

                    for(Node ady: this.elements.get(n)){
                        if (!visiting.contains(ady))
                            stack.add(ady);
                    }
                }
            }

            completed.add(n);
        }

        return result;

    }


}