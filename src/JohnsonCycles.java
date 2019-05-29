//import javafx.util.Pair;
//
//import java.util.*;
//
//public class JohnsonCycles {
//
//    Set<Node> blockedSet;
//    Map<Node, Set<Node>> blockedMap;
//    Deque<Node> stack;
//    List<List<Node>> cycles;
//
//    static int MAX_CYCLE;
//    ArrayList<ArrayList<Node>> result = new ArrayList<>();
//    int visited[]; //0: not visited, 1:visited, 2:completed
//    Deque<Node> stack = new LinkedList<>();
//
//
//    public List<List<Node>> getCycles(Graph graph) {
//        public ArrayList<ArrayList<Node>> get_all_cycles(Graph graph, int max){
//
//
//            blockedSet = new HashSet<>();
//            blockedMap = new HashMap<>();
//            stack = new LinkedList<>();
//            cycles = new ArrayList<>();
//
//            long startIndex = 1;
//            TarjanStronglyConnectedComponent tarjan = new TarjanStronglyConnectedComponent();
//
//            while (startIndex <= graph.getSize()) {
//                Graph subGraph = createSubGraph(startIndex, graph);
//                List<Set<Node>> sccs = tarjan.scc(subGraph);
//                Node minNode = minIndexSCC(sccs, subGraph);
//                if (minNode != null) {
//                    blockedSet.clear();
//                    blockedMap.clear();
//                    //TODO NO SE QUE GRAFICO USA
//                    findCycledInSCG(minNode, minNode, /*scc?*/);
//                    startIndex = minNode.getId() + 1;
//                } else {
//                    break;
//                }
//                ArrayList<Node> nodes = graph.getNodes();
//                for (int i = 0; i < nodes.size(); i++) {
//                    visited[nodes.get(i).getId()] = 0;
//                }
//
//                return cycles;
//            }
//            for (int i = 0; i < nodes.size(); i++) {
//                result.addAll(getCycles(graph, nodes.get(i), max));
//            }
//
//            private boolean findCycledInSCG(Node startNode, Node currentNode, Graph graph) {
//                boolean foundCycle = false;
//                stack.push(currentNode);
//                blockedSet.add(currentNode);
//                return result;
//
//                for (Node ady : graph.getAdy(currentNode)) {
//                    if (ady.equals(startNode) && stack.size() >= 3 && stack.size() <= MAX_CYCLE) {
//                        List<Node> cycle = new ArrayList<>();
//                        cycle.addAll(stack); //no vuelvo a agregar el primero
//                        Collections.reverse(cycle);
//                        cycles.add(cycle);
//                        foundCycle = true;
//                    } else if (!blockedSet.contains(ady)) {
//                        boolean gotCycle = findCycledInSCG(startNode, ady, graph);
//                        foundCycle = foundCycle || gotCycle;
//                    }
//                }
//
//                if (foundCycle) {
//                    unblock(currentNode);
//                } else {
//                    for (Node a : graph.getAdy(currentNode)) {
//                        Set<Node> bSet = getBSet(a);
//                        bSet.add(currentNode);
//                    }
//                }
//            }
//
//            stack.pop();
//            return foundCycle;
//        }
//
//        public ArrayList<ArrayList<Node>> getCycles(Graph graph, Node current_node, int max) {
//
//            private void unblock(Node node) {
//                blockedSet.remove(node);
//                if (blockedMap.get(node) != null) {
//                    blockedMap.get(node).forEach(n -> {
//                        if (blockedSet.contains(n)) {
//                            unblock(n);
//                        }
//                    });
//                }
//            }
//            ArrayList<ArrayList<Node>> result = new ArrayList<>();
//            this.stack.addLast(current_node);
//
//
//            private Set<Node> getBSet(Node n) {
//                return blockedMap.computeIfAbsent(n, (key) ->
//                        new HashSet<>());
//            }
//            ArrayList<Node> cycle = new ArrayList<>();
//
//            private Node minIndexSCC(List<Set<Node>> sccs, Graph subGraph) {
//
//                long min = Integer.MAX_VALUE;
//                Node minNode = null;
//                Set<Node> minScc = null;
//                for (Set<Node> scc : sccs) {
//                    if (scc.size() == 1)
//                        continue;
//                    while(!stack.isEmpty()){
//                        Node top = stack.getLast();
//                        boolean found_cycle = false;
//
//                        for (Node node : scc) {
//                            if (node.getId() < min) {
//                                min = node.getId();
//                                minNode = node;
//                                minScc = scc;
//                            }
//                        }
//                    }
//                    if(visited[top.getId()] == 0){
//                        visited[top.getId()] = 1;
//                        cycle.add(top);
//                    }
//                    else if(visited[top.getId()] == 1){
//                        System.out.println("Hay un ciclo!");
//                        found_cycle = true;
//                        if ((cycle.size() < max) && (cycle.size() > 3)){
//                            cycle.remove(cycle.size() - 1); // elimino el ultimo, que no hace falta incluirlo.
//                            result.add(cycle);
//                        }
//                    }
//
//                    if (minNode == null)
//                        return null;
//
//                    //TODO ESTO NO ENTIENDO POR QUE HACE, HELP
//                    Graph graphScc = new Graph();
//                    for (Edge edge : subGraph.getEdges()) {
//                        Node node1 = edge.getFirst();
//                        Node node2 = edge.getSecond();
//                        if (minScc.contains(node1) && minScc.contains(node2)) {
//                            graphScc.addElement(node1);
//                            graphScc.addElement(node2);
//                            graphScc.addEdge(node1, node2);
//                        }
//                    }
//
//                    return graphScc.getNode(minNode.getId());
//                }
//                if (!found_cycle){
//                    ArrayList<Node> ady = graph.getAdy(top);
//                    for (int i = 0; i < ady.size(); i++) {
//                        if(visited[ady.get(i).getId()] == 0){
//                            stack.addLast(top);
//                        }
//                    }
//                }
//
//                visited[top.getId()] = 2;
//
//                //crea subgrafo a partir del nodo start
//                private Graph createSubGraph(long startIndex, Graph graph) {
//                    Graph subGraph = new Graph();
//                    for (Edge edge : graph.getEdges()) {
//                        Node node1 = edge.getFirst();
//                        Node node2 = edge.getSecond();
//                        if (node1.getId() >= startIndex && node2.getId() >= startIndex) {
//                            subGraph.addElement(node1);
//                            subGraph.addElement(node2);
//                            subGraph.addEdge(node1, node2);
//                        }
//                    }
//
//                    return subGraph;
//                    return result;
//                }
//
//            }
//
