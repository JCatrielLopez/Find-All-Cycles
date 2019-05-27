import java.util.*;

public class JohnsonCiclos {

    Set<Node> blockedSet;
    Map<Node, Set<Node>> blockedMap;
    Deque<Node> stack;
    List<List<Node>> cycles;


    public List<List<Node>> getCycles(Graph graph) {

        blockedSet = new HashSet<>();
        blockedMap = new HashMap<>();
        stack = new LinkedList<>();
        cycles = new ArrayList<>();

        return cycles;
    }

}
