import java.util.*;

public class TarjanStronglyConnectedComponent {

    private Map<Node, Integer> visitedTime;
    private Map<Node, Integer>  lowTime;
    private Set<Node> onStack;
    private Deque<Node> stack;
    private Set<Node> visited;
    private List<Set<Node>> result;
    private int time;


    public List<Set<Node>> scc(Graph graph) {

        time=0;
        visitedTime = new HashMap<>();
        lowTime = new HashMap<>();
        onStack = new HashSet<>();
        stack= new LinkedList<>();
        visited = new HashSet<>();
        result = new ArrayList<>();

        for(Node node: graph.getNodes()){
            if(visited.contains(node)){
                continue;
            }
            sccUntil(node);
        }

        return result;
    }

    private void sccUntil(Node node){

    }
}
