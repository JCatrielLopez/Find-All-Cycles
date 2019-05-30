import java.util.*;

public class Graph {

    private HashMap<Node, ArrayList<Node>> elements;
    private ArrayList<Edge> edges;
    ArrayList<Node> visited; //0: not visited, 1:visited, 2:completed
    Deque<Node> stack = new LinkedList<>();

    private int size;

    public Graph() {
        this.elements = new HashMap<>();
        this.size = 0;
        this.edges = new ArrayList<>();
    }

    public void addElement(Node name) {
        if (!this.elements.containsKey(name)) {
            this.elements.putIfAbsent(name, new ArrayList<>());
            size++;
        }
    }

    public void removeElement(Node name) {
        if (this.elements.containsKey(name)) {
            this.elements.remove(name);
            size--;
        }

        for (ArrayList<Node> e : elements.values()) {
            e.remove(name);
        }

        ArrayList<Edge> new_edges = new ArrayList<>();

        for (int i = 0; i < this.edges.size(); i++) {
            if (!edges.get(i).contains(name)) {
                new_edges.add(edges.get(i));
            }
        }
        this.edges = new_edges;
    }

    public void addEdge(Node first, Node end) {
        if ((this.elements.containsKey(first) && this.elements.containsKey(end))) {
            if (!this.elements.get(first).contains(end)) {
                this.elements.get(first).add(end);
                this.edges.add(new Edge(first, end));
            }
        }
    }

    public void removeEdge(Edge edge) {
        if (this.edges.contains(edge)) {
            this.edges.remove(edge);
        }
    }

    public int getSize() {
        return this.size;
    }

    public ArrayList<Node> getAdy(Node node) {
        return this.elements.get(node);
    }

    public void print() {
        for (Node nodo : this.elements.keySet()) {
            System.out.println("NODO: " + nodo.toString());
            System.out.println("ADYACENTES: ");
            for (Node adyacentes : this.elements.get(nodo)) {
                System.out.println(adyacentes.toString());
            }
            System.out.println(" -------------------------- ");
        }
    }

    public boolean contains(String name) {
        for (Node nodo : this.elements.keySet()) {
            if (nodo.getName().equals(name))
                return true;
        }

        return false;
    }

    public Node get(String name) {
        for (Node nodo : this.elements.keySet()) {
            if (nodo.getName().equals(name))
                return nodo;
        }

        return null;
    }

    public int edgesSize() {
        return this.edges.size();
    }

    public Node getNode(int id) {
        for (Node nodo : this.elements.keySet()) {
            if (nodo.getId() == id)
                return nodo;
        }
        return null;
    }

    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Node node : elements.keySet())
            nodes.add(node);
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return (ArrayList<Edge>) this.edges.clone();
    }


    public HashSet<HashSet<Node>> get_all_cycles(Graph graph, int max) {
        long inicio = System.currentTimeMillis();

        HashSet<HashSet<Node>> result = new HashSet<>();
        ArrayList<Node> nodes = graph.getNodes();

        for (int i = 0; i < nodes.size(); i++) {
            //visited = new ArrayList<>();
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
        Deque<Node> cycle = new LinkedList<>();

        blockedSet = new HashSet<>();
        blockedMap = new HashMap<>();
        stack.addFirst(current_node);
//        System.out.println("-----------------------------------------------------");
//        System.out.println("Voy a buscar a partir del nodo " + current_node);
//        System.out.println("-----------------------------------------------------");
        while (!stack.isEmpty()) {
            //System.out.println("STACK: " + stack);
            Node top = stack.peekFirst();
            System.out.println("NODO ACTUAL: " + top);
            boolean found_cycle = false;

            //System.out.println("VISITED: " + visited);
            //System.out.println(top + " > VISITED");
            blockedSet.add(top);

            if (cycle.size() <= max)
                cycle.add(top);
            else
                continue; // Si ya llegue a un numero de nodos superior al maximo, no tiene sentido continuar.

            ArrayList<Node> ady = graph.getAdy(top);

            if (ady == null) {
                cycle.remove(top);
                stack.removeFirst();
                continue;
            }

            boolean all_v = true;
            for (int i = 0; i < ady.size(); i++) {
                if (!blockedSet.contains(ady.get(i))) {
                    //System.out.println(ady.get(i) + " > STACK");
                    stack.addFirst(ady.get(i));
                    all_v = false;
                } else {
                    //System.out.println("OJO! YO YA VISITE EL NODO " + ady.get(i));
                    //System.out.println("CICLO ACTUAL: " + cycle);
                    if (ady.get(i).equals(current_node) && (cycle.size() < max) && (cycle.size() >= 2)) {
                        if (!result.contains(cycle)) {
                            System.out.println("GUARDO UN CICLO");
                            //System.out.println("Size: " + cycle.size());
                            HashSet<Node> new_cycle = new HashSet<>();
                            for (Node nodo : cycle)
                                new_cycle.add(nodo);
                            result.add(new_cycle);
                            System.out.println("CICLO GUARDADO: " + new_cycle);
                            cycle = refresh(cycle, top);
                            unblock(top);
                        }
                    } else
                        for (int j=0; j<ady.size();j++) {
                            Set<Node> bSet = getBSet(ady.get(j));
                            bSet.add(top);

                        }
                }
            }

            System.gc();
            boolean delete = true;
            for (Node ady_n : graph.getAdy(top))
                if (!blockedSet.contains(ady_n))
                    delete = false;

            if (delete) {
                stack.remove(top);
                cycle.remove(top);
            }

            if (all_v && cycle.size() > 0)
                cycle.remove(cycle.size() - 1);

            //System.out.println("\n");
        }

        return result;
    }

    private Deque<Node> refresh(Deque<Node> cycle, Node current) {
        int i = cycle.size();
        while (!cycle.peekLast().equals(current)) {
            cycle.pollLast();

        }
        //cycle.pollLast();
        return cycle;
    }

    Set<Node> blockedSet;
    Map<Node, Set<Node>> blockedMap;

    private Set<Node> getBSet(Node v) {
        return blockedMap.computeIfAbsent(v, (key) ->
                new HashSet<>());
    }


    public HashSet<HashSet<Node>> getCycles2(Graph graph, Node first, int max) {
        //Node current= first;
        blockedSet = new HashSet<>();
        blockedMap = new HashMap<>();
        stack = new LinkedList<>();
        HashSet<HashSet<Node>> allCycles = new HashSet<>();

        stack.push(first);
        blockedSet.add(first);
        boolean found = false;

        while (!stack.isEmpty()) {
            found = false;
            Node current = stack.peek();

            for (Node ady : graph.getAdy(current)) {
                if (ady.equals(first) && stack.size() > 2 && stack.size() <= max) {
                    HashSet<Node> cycle = new HashSet<>();
                    cycle.addAll(stack);
                    found = true;
                    allCycles.add(cycle);
                } else if (!blockedSet.contains(ady)) {
                    stack.push(ady);
                    blockedSet.add(ady);
                }
            }
            if (found) {
                unblock(current);
            } else {
                for (Node ady : graph.getAdy(current)) {
                    Set<Node> bSet = getBSet(ady);
                    bSet.add(current);
                }
            }
            stack.pop();
        }

        return allCycles;
    }

    private void unblock(Node u) {
        blockedSet.remove(u);
        if (blockedMap.get(u) != null) {
            blockedMap.get(u).forEach(v -> {
                if (blockedSet.contains(v)) {
                    unblock(v);
                }
            });
            blockedMap.remove(u);
        }
    }

}