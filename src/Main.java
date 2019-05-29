import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    private void save_to_file(ArrayList<ArrayList<Node>> cycles){

    }

    public static void main(String[] args) {

        long inicio = System.currentTimeMillis();


        UserHandler my_handler = new UserHandler();
        ODEMSAXParser parser;

        try {

            parser = new ODEMSAXParser(my_handler, "odems/hibernate-core-4.0.0.Final.odem");
            parser.parse();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }


        HashMap<String, ArrayList<String>> pkgs = my_handler.getPKGS();

        Graph graph = new Graph();


        for (String pkg : pkgs.keySet()) {
            Node node = new Node(pkg);
            graph.addElement(node);
            for (String pkg_ady : pkgs.get(pkg)) {
                if ((pkg_ady != null)) {
                    Node ady;
                    if (graph.contains(pkg_ady)) {
                        ady = graph.get(pkg_ady);
                    } else {
                        ady = new Node(pkg_ady);
                        graph.addElement(ady);
                    }

                    if (!ady.equals(node)) {
                        graph.addEdge(node, ady);
                    }
                }
            }
        }

//        graph.print();
//        System.out.println("Nodes: " + graph.getSize());
//        System.out.println("Edges: " + graph.edgesSize());
//        System.out.println(" -------------------------- \n");


        long fin = System.currentTimeMillis();
        System.out.println("Demora de generacion de grafo (milis): " + (fin - inicio));


        System.out.println(" ############################### ");

        Graph test_graph = new Graph();

        Node nodo_1 = new Node("1");
        Node nodo_2 = new Node("2");
        Node nodo_3 = new Node("3");
        Node nodo_4 = new Node("4");
        Node nodo_5 = new Node("5");
        Node nodo_6 = new Node("6");



        test_graph.addElement(nodo_1);
        test_graph.addElement(nodo_2);
        test_graph.addElement(nodo_3);
        test_graph.addElement(nodo_4);
        test_graph.addElement(nodo_5);
        test_graph.addElement(nodo_6);


        test_graph.addEdge(nodo_1, nodo_2);
        test_graph.addEdge(nodo_2, nodo_3);
        test_graph.addEdge(nodo_3, nodo_1);
        test_graph.addEdge(nodo_2, nodo_4);
        test_graph.addEdge(nodo_2, nodo_6);
        test_graph.addEdge(nodo_4, nodo_5);
        test_graph.addEdge(nodo_6, nodo_5);
        test_graph.addEdge(nodo_5, nodo_6);


        ArrayList<ArrayList<Node>> cycles = graph.get_all_cycles(test_graph,10);
        System.out.println("#######################");

//        System.out.println(cycles);

        System.out.println("Cantidad de ciclos: " + cycles.size());
        System.out.println(" ----------------------- ");
        System.out.println(" ----------------------- ");
        for(ArrayList<Node> ciclo: cycles){
            System.out.println("Cantidad de nodos: " + ciclo.size());
            System.out.println(" ----------------------- ");
            for(Node nodo: ciclo){
                System.out.println(nodo.toString());
            }
            System.out.println(" ----------------------- ");
        }
    }
}
