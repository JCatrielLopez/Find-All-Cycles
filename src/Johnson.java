import java.util.*;

public class Johnson {
    HashSet<Node> blockedSet;
    HashMap<Node, Set<Node>> blockedMap;
    LinkedList<Node> stack;
    ArrayList<ArrayList<Node>> allCycles;

    public ArrayList<ArrayList<Node>> simpleCyles(Graph graph, int max) {

        blockedSet = new HashSet<>();
        blockedMap = new HashMap<>();
        stack = new LinkedList<>();
        allCycles = new ArrayList<>();
        int startId = 0;
        Tarjan tarjan = new Tarjan();

        while (startId <= graph.cantNodos()) {

            graph = subgraph(startId, graph);

            ArrayList<HashSet<Node>> cfcs = tarjan.cfc(graph);

            Node menorNodo = minIndexCFC(cfcs, graph);
            if (menorNodo!=null) {
                blockedSet.clear();
                blockedMap.clear();
                findCyclesInGraphCFC(menorNodo, menorNodo, max);
                System.gc();
                startId = menorNodo.getId() + 1;
            } else {
                break;
            }
        }
        return allCycles;
    }

    //creo subgrafo eliminando los nodos que ya explore
    private Graph subgraph(int startID, Graph graph) {
        Graph subGraph = new Graph();
        for (Edge edge : graph.edges) {
            if (edge.getNode1().getId() >= startID && edge.getNode2().getId() >= startID) {
                subGraph.addElement(edge.getNode1());
                subGraph.addElement(edge.getNode2());
                subGraph.addEdge(edge.getNode1(), edge.getNode2());
            }
            else if (edge.getNode1().getId() < startID)
                graph.removeElement(edge.getNode1());
            else if (edge.getNode2().getId() < startID)
                graph.removeElement(edge.getNode2());
        }

        return subGraph;
    }


    private Node minIndexCFC(ArrayList<HashSet<Node>> sccs, Graph subGraph) {
        long min = Integer.MAX_VALUE;
        Node minNode = null;
        Set<Node> minScc = null;
        for (HashSet<Node> scc : sccs) {
            if (scc.size() == 1) {
                continue;
            }
            for (Node vertex : scc) {
                if (vertex.getId() < min) {
                    min = vertex.getId();
                    minNode = vertex;
                    minScc = scc;
                }
            }
        }

        if (minNode == null) {
            return null;
        }

        //armo un subgrafo con los nodos que se encuentran en la minima componente fuertemente conectada
        Graph graphCFC = new Graph();
        for (Edge edge : subGraph.edges) {
            if (minScc.contains(edge.getNode1()) && minScc.contains(edge.getNode2())) {
                graphCFC.addElement(edge.getNode1());
                graphCFC.addElement(edge.getNode2());
                graphCFC.addEdge(edge.getNode1(), edge.getNode2());

            }
        }

        return graphCFC.getNodeByID(minNode.getId());
    }

    private void unblock(Node node) {
        blockedSet.remove(node);
        if (blockedMap.get(node) != null) {
            blockedMap.get(node).forEach(v -> {
                if (blockedSet.contains(v)) {
                    unblock(v);
                }
            });
            blockedMap.remove(node);
        }
    }

    private boolean findCyclesInGraphCFC(Node startNode, Node currentNode, int max) {

        boolean foundCycle = false;
        stack.push(currentNode);
        blockedSet.add(currentNode);
        boolean over_max=false;

        for (Node ady : currentNode.getAdyacentes()) {
            if (ady == startNode && stack.size() > 2 && stack.size() <= max ) {
                ArrayList<Node> cycle = new ArrayList<>(stack);
                Collections.reverse(cycle);
                allCycles.add(cycle);
                foundCycle = true;
            }
            else {
                if (stack.size() > max) {
                    over_max=true;
                    break;
                }
                //busco por el adyacente solo si no se encuentra bloqueado
                if (!blockedSet.contains(ady)) {
                    boolean gotCycle =
                            findCyclesInGraphCFC(startNode, ady, max);
                    foundCycle = foundCycle || gotCycle;
                }
            }
        }
        //si encuentro un ciclo o mi stack pasa el maximo, desbloqueo los nodos
        if (foundCycle || over_max) {
            unblock(currentNode);
        } else {
            ///si no encuentro un ciclo bloqueo al nodo y a sus adyacentes ya que se que no voy a encontrar un ciclo por ese camino
            for (Node n : currentNode.adyacentes) {
                Set<Node> bSet = getBSet(n);
                bSet.add(currentNode);
            }
        }
        stack.pop();
        return foundCycle;
    }

    private Set<Node> getBSet(Node v) {
        return blockedMap.computeIfAbsent(v, (key) ->
                new HashSet<>());
    }


}
