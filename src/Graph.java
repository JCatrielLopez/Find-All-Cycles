import java.util.*;

public class Graph {

    private HashMap<Node, ArrayList<Node>> elements;
    private ArrayList<Edge> edges;
    ArrayList<ArrayList<Node>> result = new ArrayList<>();
    ArrayList<Node> visited = new ArrayList<>(); //0: not visited, 1:visited, 2:completed
    Deque<Node> stack = new LinkedList<>();

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


    public ArrayList<ArrayList<Node>> get_all_cycles(Graph graph, int max){

        ArrayList<Node> nodes = graph.getNodes();

        for (int i = 0; i < nodes.size(); i++) {
            result.addAll(getCycles(graph, nodes.get(i), max));
        }

        return result;
    }


    public ArrayList<ArrayList<Node>> getCycles(Graph graph, Node current_node, int max) {

        ArrayList<ArrayList<Node>> result = new ArrayList<>();
        this.stack.addLast(current_node);

        ArrayList<Node> cycle = new ArrayList<>();

        while(!stack.isEmpty()){
            Node top = stack.getLast();
            boolean found_cycle = false;

            if(!visited.contains(top)){
                visited.add(top);
                cycle.add(top);
            }
            else{
                System.out.println("Hay un ciclo: " + cycle);
                found_cycle = true;
                if ((cycle.size() < max) && (cycle.size() > 3)){
                    cycle.remove(cycle.size() - 1); // elimino el ultimo, que no hace falta incluirlo.
//                    System.out.println("Ciclo: " + cycle);
                    result.add(cycle);
                }
            }

            if (!found_cycle){
                ArrayList<Node> ady = graph.getAdy(top);
                for (int i = 0; i < ady.size(); i++) {
                    if(!visited.contains(ady.get(i))){
                        stack.addLast(top);
                    }
                }
            }
        }

        return result;
    }

}