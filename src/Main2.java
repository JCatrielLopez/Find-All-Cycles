import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public class Main2 {

    private void save_to_file(ArrayList<ArrayList<Node>> cycles){

    }

    public static void main(String[] args) {
/*
        long inicio = System.currentTimeMillis();


        UserHandler my_handler = new UserHandler();
        ODEMSAXParser parser;

        try {

            parser = new ODEMSAXParser(my_handler,
                    "odems/apache-cxf-2.0.6.odem");
            parser.parse();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }


        HashMap<String, ArrayList<String>> pkgs = my_handler.getPKGS();

        Graph2 graph = new Graph2();



        long i=0;
        for (String pkg : pkgs.keySet()) {
            Node node;
            if (graph.contains(pkg)) {
                node = graph.get(pkg);
            }
            else{
            node = new Node(pkg);
            graph.addElement(node);
            }
            for (String pkg_ady : pkgs.get(pkg)) {

                if ((pkg_ady != null)) {
                    Node ady;
                    if (graph.contains(pkg_ady)) {
                        ady = graph.get(pkg_ady);
                    } else {
                        ady = new Node(pkg_ady);
                        graph.addElement(ady);
                    }

                    if (!ady.equals(node) && !node.containsAdy(ady)) {

                        graph.addEdge(node, ady);
                    }
                }
            }
        }


        long fin = System.currentTimeMillis();
        System.out.println("Demora de generacion de grafo (milis): " + (fin - inicio));
*/
        Graph2 test_graph= new Graph2();

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
        test_graph.addEdge(nodo_1, nodo_3);
        test_graph.addEdge(nodo_2, nodo_5);
        test_graph.addEdge(nodo_5, nodo_4);
        test_graph.addEdge(nodo_3, nodo_4);
        test_graph.addEdge(nodo_6, nodo_5);
        test_graph.addEdge(nodo_4, nodo_6);
        test_graph.addEdge(nodo_4, nodo_1);

        long inicio2 = System.currentTimeMillis();

        System.out.println("cant vertices: "+ test_graph.cantNodos());
        System.out.println("cant arcos: "+ test_graph.cantArcos());

        //System.out.println("GRAFO: ");
        //graph.print();

        Johnson j= new Johnson();
        List<List<Node>> allCycles = j.simpleCyles(test_graph);

        long fin2 = System.currentTimeMillis();
        System.out.println("Demora de busqueda de ciclos (milis): " + (fin2 - inicio2));
        System.out.println("cant ciclos: "+ allCycles.size());

        allCycles.forEach(cycle -> {
            StringJoiner joiner = new StringJoiner("->");
            cycle.forEach(vertex -> joiner.add(String.valueOf(vertex.getId())));
            System.out.println(joiner);
        });
    }
}
