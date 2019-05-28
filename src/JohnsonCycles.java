import java.util.*;

public class JohnsonCycles {

    ArrayList<ArrayList<Node>> result = new ArrayList<>();
    int visited[]; //0: not visited, 1:visited, 2:completed
    Deque<Node> stack = new LinkedList<>();


    public ArrayList<ArrayList<Node>> get_all_cycles(Graph graph, int max){

        ArrayList<Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            visited[nodes.get(i).getId()] = 0;
        }

        for (int i = 0; i < nodes.size(); i++) {
            result.addAll(getCycles(graph, nodes.get(i), max));
        }

        return result;


    }


    public ArrayList<ArrayList<Node>> getCycles(Graph graph, Node current_node, int max) {

        ArrayList<ArrayList<Node>> result = new ArrayList<>();
        this.stack.addLast(current_node);


        ArrayList<Node> cycle = new ArrayList<>();


        while(!stack.isEmpty()){
            Node top = stack.getLast();
            boolean found_cycle = false;

            if(visited[top.getId()] == 0){
                visited[top.getId()] = 1;
                cycle.add(top);
            }
            else if(visited[top.getId()] == 1){
                System.out.println("Hay un ciclo!");
                found_cycle = true;
                if ((cycle.size() < max) && (cycle.size() > 3)){
                    cycle.remove(cycle.size() - 1); // elimino el ultimo, que no hace falta incluirlo.
                    result.add(cycle);
                }
            }

            if (!found_cycle){
                ArrayList<Node> ady = graph.getAdy(top);
                for (int i = 0; i < ady.size(); i++) {
                    if(visited[ady.get(i).getId()] == 0){
                        stack.addLast(top);
                    }
                }
            }

            visited[top.getId()] = 2;

        }

        return result;
    }

}
