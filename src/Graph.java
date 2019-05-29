import java.util.*;

public class Graph {

    private HashMap<Node, ArrayList<Node>> elements;
    private ArrayList<Edge> edges;
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

        ArrayList<Edge> new_edges = new ArrayList<>();

        for (int i = 0; i < this.edges.size(); i++) {
            if (!edges.get(i).contains(name)){
                new_edges.add(edges.get(i));
            }
        }
        this.edges = new_edges;
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


    public HashSet<HashSet<Node>> get_all_cycles(Graph graph, int max){
        long inicio = System.currentTimeMillis();

        HashSet<HashSet<Node>> result = new HashSet<>();
        ArrayList<Node> nodes = graph.getNodes();

        for (int i = 0; i < nodes.size(); i++) {
            visited = new ArrayList<>();
            result.addAll(getCycles(graph, nodes.get(i), max));
            graph.removeElement(nodes.get(i));
        }

        long fin = System.currentTimeMillis();
        System.out.println("Demora de busqueda de ciclos (milis): " + (fin - inicio));
        return result;
    }


    public HashSet<HashSet<Node>> getCycles(Graph graph, Node current_node, int max) {

        //TODO Es asquerosamente ineficiente.

        HashSet<HashSet<Node>> result = new HashSet<>();
        HashSet<Node> cycle = new HashSet<>();

        stack.push(current_node);
        System.out.println("-----------------------------------------------------");
        System.out.println("Voy a buscar a partir del nodo " + current_node);
        System.out.println("-----------------------------------------------------");
        while(!stack.isEmpty()){
            System.out.println("STACK: " + stack);
            Node top = stack.peek();
            System.out.println("NODO ACTUAL: " + top);
            boolean found_cycle = false;

            System.out.println("VISITED: " + visited);
            System.out.println(top + " > VISITED");
            visited.add(top);

            if (cycle.size() <= max)
                cycle.add(top);
            else
                continue; // Si ya llegue a un numero de nodos superior al maximo, no tiene sentido continuar.

            ArrayList<Node> ady = graph.getAdy(top);

            if(ady == null) {
                cycle.remove(top);
                stack.pop();
                continue;
            }

            boolean all_v = true;
            for (int i = 0; i < ady.size(); i++) {
                if(!visited.contains(ady.get(i))){
                    System.out.println(ady.get(i) + " > STACK");
                    stack.push(ady.get(i));
                    all_v = false;
                }
                else{
                    System.out.println("OJO! YO YA VISITE EL NODO " + ady.get(i));
                    System.out.println("CICLO ACTUAL: " + cycle);
                    if (ady.get(i).equals(current_node) && (cycle.size() < max) && (cycle.size() >= 2)){
                        if(!result.contains(cycle)) {
                            System.out.println("GUARDO UN CICLO");
                            System.out.println("Size: " + cycle.size());
                            HashSet<Node> new_cycle = new HashSet<>();
                            for (Node nodo : cycle)
                                new_cycle.add(nodo);
                            result.add(new_cycle);
                            System.out.println("CICLO GUARDADO: " + new_cycle);
                            cycle = new HashSet<>();
                        }
                    }
                }
            }

            System.gc();
            boolean delete = true;
            for(Node ady_n: graph.getAdy(top))
                if (!visited.contains(ady_n))
                    delete = false;

            if (delete) {
                stack.remove(top);
                cycle.remove(top);
            }

            if(all_v && cycle.size()>0)
                cycle.remove(cycle.size() - 1);

            System.out.println("\n");
        }

        reader.close();
        return result;
    }

}