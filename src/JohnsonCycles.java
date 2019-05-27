import javafx.util.Pair;

import java.util.*;

public class JohnsonCycles {

    Set<Node> blockedSet;
    Map<Node, Set<Node>> blockedMap;
    Deque<Node> stack;
    List<List<Node>> cycles;


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
                findCycledInSCG(minNode, minNode);
                startIndex = minNode.getId() + 1;
            } else {
                break;
            }
        }

        return cycles;
    }

    private void findCycledInSCG(Node minNode, Node minNode1) {
        //todo
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

        Graph graphScc = new Graph();

        //todo for edge

        return graphScc.getNode(minNode.getId());
    }


    //crea subgrafo a partir del nodo start
    private Graph createSubGraph(long startIndex, Graph graph) {
        Graph subGraph = new Graph();
        //todo
        return subGraph;
    }

}
