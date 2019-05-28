import javafx.util.Pair;

import java.util.*;

public class JohnsonCycles {

    Set<Node> blockedSet;
    Map<Node, Set<Node>> blockedMap;
    Deque<Node> stack;
    List<List<Node>> cycles;

    static int MAX_CYCLE;


    public List<List<Node>> getCycles(Graph graph) {


        blockedSet = new HashSet<>();
        blockedMap = new HashMap<>();
        stack = new LinkedList<>();
        cycles = new ArrayList<>();

        long startIndex = 1;
        TarjanStronglyConnectedComponent tarjan = new TarjanStronglyConnectedComponent();

        while (startIndex <= graph.getSize()) {
            Graph subGraph = createSubGraph(startIndex, graph);
            List<Set<Node>> sccs = tarjan.scc(subGraph);
            Node minNode = minIndexSCC(sccs, subGraph);
            if (minNode != null) {
                blockedSet.clear();
                blockedMap.clear();
                //TODO NO SE QUE GRAFICO USA
                findCycledInSCG(minNode, minNode, /*scc?*/);
                startIndex = minNode.getId() + 1;
            } else {
                break;
            }
        }

        return cycles;
    }

    private boolean findCycledInSCG(Node startNode, Node currentNode, Graph graph) {
        boolean foundCycle = false;
        stack.push(currentNode);
        blockedSet.add(currentNode);

        for (Node ady : graph.getAdy(currentNode)) {
            if (ady.equals(startNode) && stack.size() >= 3 && stack.size() <= MAX_CYCLE) {
                List<Node> cycle = new ArrayList<>();
                cycle.addAll(stack); //no vuelvo a agregar el primero
                Collections.reverse(cycle);
                cycles.add(cycle);
                foundCycle = true;
            } else if (!blockedSet.contains(ady)) {
                boolean gotCycle = findCycledInSCG(startNode, ady, graph);
                foundCycle = foundCycle || gotCycle;
            }
        }

        if (foundCycle) {
            unblock(currentNode);
        } else {
            for (Node a : graph.getAdy(currentNode)) {
                Set<Node> bSet = getBSet(a);
                bSet.add(currentNode);
            }
        }

        stack.pop();
        return foundCycle;
    }

    private void unblock(Node node) {
        blockedSet.remove(node);
        if (blockedMap.get(node) != null) {
            blockedMap.get(node).forEach(n -> {
                if (blockedSet.contains(n)) {
                    unblock(n);
                }
            });
        }
    }

    private Set<Node> getBSet(Node n) {
        return blockedMap.computeIfAbsent(n, (key) ->
                new HashSet<>());
    }

    private Node minIndexSCC(List<Set<Node>> sccs, Graph subGraph) {

        long min = Integer.MAX_VALUE;
        Node minNode = null;
        Set<Node> minScc = null;
        for (Set<Node> scc : sccs) {
            if (scc.size() == 1)
                continue;

            for (Node node : scc) {
                if (node.getId() < min) {
                    min = node.getId();
                    minNode = node;
                    minScc = scc;
                }
            }
        }

        if (minNode == null)
            return null;

        //TODO ESTO NO ENTIENDO POR QUE HACE, HELP
        Graph graphScc = new Graph();
        for (Edge edge : subGraph.getEdges()) {
            Node node1 = edge.getFirst();
            Node node2 = edge.getSecond();
            if (minScc.contains(node1) && minScc.contains(node2)) {
                graphScc.addElement(node1);
                graphScc.addElement(node2);
                graphScc.addEdge(node1, node2);
            }
        }

        return graphScc.getNode(minNode.getId());
    }


    //crea subgrafo a partir del nodo start
    private Graph createSubGraph(long startIndex, Graph graph) {
        Graph subGraph = new Graph();
        for (Edge edge : graph.getEdges()) {
            Node node1 = edge.getFirst();
            Node node2 = edge.getSecond();
            if (node1.getId() >= startIndex && node2.getId() >= startIndex) {
                subGraph.addElement(node1);
                subGraph.addElement(node2);
                subGraph.addEdge(node1, node2);
            }
        }

        return subGraph;
    }

}
