
import java.util.*;

/**
 * Date 08/16/2015
 * @author Tushar Roy
 *
 * Find strongly connected components of directed graph.
 *
 * Time complexity is O(E + V)
 * Space complexity  is O(V)
 *
 * Reference - https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm
 */
public class TarjanStronglyConnectedComponent {

    private Map<Node, Integer> visitedTime;
    private Map<Node, Integer> lowTime;
    private Set<Node> onStack;
    private Deque<Node> stack;
    private Set<Node> visited;
    private List<Set<Node>> result;
    private int time;

    public List<Set<Node>> scc(Graph2 graph) {

        //keeps the time when every vertex is visited
        time = 0;
        //keeps map of vertex to time it was visited
        visitedTime = new HashMap<>();

        //keeps map of vertex and time of first vertex visited in current DFS
        lowTime = new HashMap<>();

        //tells if a vertex is in stack or not
        onStack = new HashSet<>();

        //stack of visited vertices
        stack = new LinkedList<>();

        //tells if vertex has ever been visited or not. This is for DFS purpose.
        visited = new HashSet<>();

        //stores the strongly connected components result;
        result = new ArrayList<>();

        //start from any vertex in the graph.
        for (Node nodo : graph.getNodes()) {
            if(visited.contains(nodo)) {
                continue;
            }
            sccUtil(nodo);
        }

        return result;
    }

    private void sccUtil(Node nodo) {

        visited.add(nodo);
        visitedTime.put(nodo, time);
        lowTime.put(nodo, time);
        time++;
        stack.addFirst(nodo);
        onStack.add(nodo);

        for (Node ady : nodo.adyacentes) {
            //if ady is not visited then visit it and see if it has link back to vertex's ancestor. In that case update
            //low time to ancestor's visit time
            if (!visited.contains(ady)) {
                sccUtil(ady);
                //sets lowTime[vertex] = min(lowTime[vertex], lowTime[ady]);
                lowTime.compute(nodo, (v, low) ->
                        Math.min(low, lowTime.get(ady))
                );
            } //if ady is on stack then see if it was visited before vertex's low time. If yes then update vertex's low time to that.
            else if (onStack.contains(ady)) {
                //sets lowTime[vertex] = min(lowTime[vertex], visitedTime[ady]);
                lowTime.compute(nodo, (v, low) -> Math.min(low, visitedTime.get(ady))
                );
            }
        }

        //if vertex low time is same as visited time then this is start vertex for strongly connected component.
        //keep popping vertices out of stack still you find current vertex. They are all part of one strongly
        //connected component.
        if (visitedTime.get(nodo) == lowTime.get(nodo)) {
            Set<Node> stronglyConnectedComponenet = new HashSet<>();
            Node v;
            do {
                v = stack.pollFirst();
                onStack.remove(v);
                stronglyConnectedComponenet.add(v);
            } while (!nodo.equals(v));
            result.add(stronglyConnectedComponenet);
        }
    }

    public static void main(String args[]) {
        Graph2 graph = new Graph2();

        TarjanStronglyConnectedComponent tarjanStronglyConnectedComponent = new TarjanStronglyConnectedComponent();
        List<Set<Node>> result = tarjanStronglyConnectedComponent.scc(graph);

        result.forEach(scc -> {
            scc.forEach(vertex -> System.out.print(vertex + " "));
            System.out.println();
        });

    }
}