import java.util.*;

public class Graph {

    private HashMap<Node, ArrayList<Node>> elements;
    private ArrayList<Edge> edges;
    ArrayList<Stack<Node>> result = new ArrayList<>();
    ArrayList<Node> visited; //0: not visited, 1:visited, 2:completed
    Stack<Node> stack = new Stack<>();

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


    public ArrayList<Stack<Node>> get_all_cycles(Graph graph, int max){

        ArrayList<Node> nodes = graph.getNodes();

        visited = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            visited = new ArrayList<>();
            System.out.println("Voy a buscar los ciclos a partir del: " + nodes.get(i));
            result.addAll(getCycles(graph, nodes.get(i), max));
            System.out.println("--------------------------------");
        }

        return result;
    }


    public ArrayList<Stack<Node>> getCycles(Graph graph, Node current_node, int max) {

        ArrayList<Stack<Node>> result = new ArrayList<>();
        Stack<Node> cycle = new Stack<>();

        stack.push(current_node);
        cycle.add(current_node);

        boolean found_cycle;
        int sons;

        while(!stack.isEmpty()){
            sons = 0;
            System.out.println("STACK: " + stack);
            Node top = stack.pop();
            found_cycle = false;

            System.out.println("NODO ACTUAL: " + top);
            System.out.println("VISITED: " + visited);

            if(!visited.contains(top)){
                System.out.println("EL NODO ACTUAL NO FUE VISITADO");
                visited.add(top);
                if ((cycle.size() < max) && !cycle.contains(top))
                    cycle.push(top);
                else
                    if (cycle.size() >= max)
                        continue; // Si ya llegue a un numero de nodos superior al maximo, no tiene sentido continuar.
            }

            ArrayList<Node> ady = graph.getAdy(top);
            for (int i = 0; i < ady.size(); i++) {
                if(!visited.contains(ady.get(i))){
                    stack.push(ady.get(i));
                    sons++;
                }
                else{
                    System.out.println("OJO! Yo ya fui al nodo " + ady.get(i));
                    if (ady.get(i).equals(current_node) && (cycle.size() < max) && (cycle.size() >= 3)){
                        System.out.println("CICLO!!!!!!!!!!!!!!");
                        System.out.println("Cycle size: " + cycle.size());
                        result.add(cycle);
                    }
                }
            }

            if (sons == 0)
                cycle.pop();

            System.out.println("CICLE: " + cycle);
            System.out.println("\n");

//            cycle = new ArrayList<>();
        }

        return result;
    }

}