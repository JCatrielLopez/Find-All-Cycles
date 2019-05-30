
import java.util.*;

public class Johnson {
    Set<Node> blockedSet;
    Map<Node, Set<Node>> blockedMap;
    Deque<Node> stack;
    List<List<Node>> allCycles;

    /**
     * Main function to find all cycles
     */
    public List<List<Node>> simpleCyles(Graph2 graph, int max) {

        blockedSet = new HashSet();
        blockedMap = new HashMap();
        stack = new LinkedList();
        allCycles = new ArrayList();
        int startIndex = 0;
        TarjanStronglyConnectedComponent tarjan = new TarjanStronglyConnectedComponent();

        while (startIndex <= graph.cantNodos()) {

            graph = subgraph(startIndex, graph);

            List<Set<Node>> sccs = tarjan.scc(graph);

            Optional<Node> maybeLeastNode = leastIndexSCC(sccs, graph);
            if (maybeLeastNode.isPresent()) {
                Node leastNode = maybeLeastNode.get();
                blockedSet.clear();
                blockedMap.clear();
                findCyclesInSCG(leastNode, leastNode, max);
                System.gc();
                startIndex = leastNode.getId() + 1;
            } else {
                break;
            }
        }
        return allCycles;
    }

    private Graph2 subgraph(int startVertex, Graph2 graph) {
        Graph2 subGraph = new Graph2();
        for (Arco edge : graph.edges) {
            if (edge.getNode1().getId() >= startVertex && edge.getNode2().getId() >= startVertex) {
                subGraph.addElement(edge.getNode1());
                subGraph.addElement(edge.getNode2());
                subGraph.addEdge(edge.getNode1(), edge.getNode2());
            }
            else if (edge.getNode1().getId() < startVertex)
                graph.removeElement(edge.getNode1());
            else if (edge.getNode2().getId() < startVertex)
                graph.removeElement(edge.getNode2());
        }

        return subGraph;
    }


    private Optional<Node> leastIndexSCC(List<Set<Node>> sccs, Graph2 subGraph) {
        long min = Integer.MAX_VALUE;
        Node minNode = null;
        Set<Node> minScc = null;
        for (Set<Node> scc : sccs) {
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
            return Optional.empty();
        }


        Graph2 graphScc = new Graph2();
        for (Arco edge : subGraph.edges) {
            if (minScc.contains(edge.getNode1()) && minScc.contains(edge.getNode2())) {
                graphScc.addElement(edge.getNode1());
                graphScc.addElement(edge.getNode2());
                graphScc.addEdge(edge.getNode1(), edge.getNode2());

            }
        }

        return Optional.of(graphScc.getNodeID(minNode.getId()));
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

    private boolean findCyclesInSCG(
            Node startNode,
            Node currentNode, int max) {

        boolean foundCycle = false;
        stack.push(currentNode);
        blockedSet.add(currentNode);
        boolean m=false;

        for (Node ady : currentNode.adyacentes) {
            //if ady is same as start vertex means cycle is found.
            //Store contents of stack in final result.
            if (ady == startNode && stack.size() > 2 && stack.size() <= max ) {
                List<Node> cycle = new ArrayList();
                cycle.addAll(stack);
                Collections.reverse(cycle);
                allCycles.add(cycle);
                foundCycle = true;
            } //explore this ady only if it is not in blockedSet.
            else {
                if (stack.size() > max) {
                    m=true;
                    break;
                }

                if (!blockedSet.contains(ady)) {
                    boolean gotCycle =
                            findCyclesInSCG(startNode, ady, max);
                    foundCycle = foundCycle || gotCycle;
                }
            }
        }
        //if cycle is found with current vertex then recursively unblock vertex and all vertices which are dependent on this vertex.
        if (foundCycle || m) {
            //remove from blockedSet  and then remove all the other vertices dependent on this vertex from blockedSet
            unblock(currentNode);
        } else {
            //if no cycle is found with current vertex then don't unblock it. But find all its neighbors and add this
            //vertex to their blockedMap. If any of those neighbors ever get unblocked then unblock current vertex as well.
            for (Node n : currentNode.adyacentes) {
                Set<Node> bSet = getBSet(n);
                bSet.add(currentNode);
            }
        }
        //remove vertex from the stack.
        stack.pop();
        return foundCycle;
    }

    private Set<Node> getBSet(Node v) {
        return blockedMap.computeIfAbsent(v, (key) ->
                new HashSet());
    }


//    private Graph createSubGraph(long startNode, Graph graph) {
//        Graph subGraph = new Graph(true);
//        for(Edge edge : graph.getAllEdges()) {
//            if(edge.getNode1().getId() >= startNode && edge.getNode2().getId() >= startNode) {
//                subGraph.addEdge(edge.getNode1().getId(), edge.getNode2().getId());
//            }
//        }
//        return subGraph;
//    }

//    public static void main(String args[]) {
//        Johnson johnson = new Johnson();
//        Graph graph = new Graph(true);
////        graph.addEdge(1, 2);
////        graph.addEdge(1, 8);
////        graph.addEdge(1, 5);
////        graph.addEdge(2, 9);
////        graph.addEdge(2, 7);
////        graph.addEdge(2, 3);
////        graph.addEdge(3, 1);
////        graph.addEdge(3, 2);
////        graph.addEdge(3, 6);
////        graph.addEdge(3, 4);
////        graph.addEdge(6, 4);
////        graph.addEdge(4, 5);
////        graph.addEdge(5, 2);
////        graph.addEdge(8, 9);
////        graph.addEdge(9, 8);
//        graph.addEdge(1, 2);
//        graph.addEdge(1, 3);
//        graph.addEdge(2, 3);
//        graph.addEdge(2, 5);
//        graph.addEdge(3, 4);
//        graph.addEdge(4, 1);
//        graph.addEdge(4, 6);
//        graph.addEdge(6, 5);
//        graph.addEdge(5, 4);
//
//        long inicio = System.currentTimeMillis();
//        List<List<Node>> allCycles = johnson.simpleCyles(graph);
//        long fin = System.currentTimeMillis();
//        System.out.println("TIME MILLIS: "+ (fin-inicio));
//        allCycles.forEach(cycle -> {
//            StringJoiner joiner = new StringJoiner("->");
//            cycle.forEach(vertex -> joiner.add(String.valueOf(vertex.getId())));
//            System.out.println(joiner);
//        });
//    }

}
