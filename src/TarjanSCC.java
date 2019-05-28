import java.util.*;

public class TarjanSCC {

    private Map<Node, Integer> visitedTime;
    private Map<Node, Integer> lowTime;
    private Set<Node> onStack;
    private Deque<Node> stack;
    private Set<Node> visited;
    private List<Set<Node>> result;
    private int time;


    public List<Set<Node>> scc(Graph graph) {

        time = 0;
        visitedTime = new HashMap<>();
        lowTime = new HashMap<>();
        onStack = new HashSet<>();
        stack = new LinkedList<>();
        visited = new HashSet<>();
        result = new ArrayList<>();

        for (Node node : graph.getNodes()) {
            if (visited.contains(node)) {
                continue;
            }
            sccUntil(node, graph);
        }

        return result;
    }

    private void sccUntil(Node node, Graph graph) {

        visited.add(node);
        visitedTime.put(node, time);
        lowTime.put(node, time);
        time++;
        stack.addFirst(node);
        onStack.add(node);

        for(Node n: graph.getAdy(node)){
            if (!visited.contains(n)){
                sccUntil(n, graph);
                lowTime.compute(node, (n1,low) -> Math.min(low, lowTime.get(n)));
            }
            else if (onStack.contains(n)){
                lowTime.compute(node, (n1,low) -> Math.min(low, lowTime.get(n)));
            }
        }

        if (visitedTime.get(node) == lowTime.get(node)){
            Set<Node> stronglyConnectedComponent = new HashSet<>();
            Node n;
            do{
                n= stack.pollFirst();
                onStack.remove(n);
                stronglyConnectedComponent.add(n);
            } while (!node.equals(n));
            result.add(stronglyConnectedComponent);
        }

    }
}
