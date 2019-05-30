import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class Main2 {

    private void save_to_file(ArrayList<ArrayList<Node>> cycles){

    }

    public static void main(String[] args) {



        Scanner reader = new Scanner(System.in);
        System.out.println("Ingrese ruta del odem: ");
        String odem= reader.next();
        reader.close();

        UserHandler my_handler = new UserHandler();
        ODEMSAXParser parser;

        long inicio = System.currentTimeMillis();

        try {

            parser = new ODEMSAXParser(my_handler,
                    odem);
            parser.parse();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }


        HashMap<String, ArrayList<String>> pkgs = my_handler.getPKGS();

        Graph graph = new Graph();


        for (String pkg : pkgs.keySet()) {
            Node node;
            if (graph.contains(pkg)) {
                node = graph.getNodeByName(pkg);
            }
            else{
            node = new Node(pkg);
            graph.addElement(node);
            }
            for (String pkg_ady : pkgs.get(pkg)) {

                if ((pkg_ady != null)) {
                    Node ady;
                    if (graph.contains(pkg_ady)) {
                        ady = graph.getNodeByName(pkg_ady);
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


        long inicioCiclos = System.currentTimeMillis();

        System.out.println("cant vertices: "+ graph.cantNodos());
        System.out.println("cant arcos: "+ graph.cantArcos());



        int max=3;
        Johnson j= new Johnson();
        ArrayList<ArrayList<Node>> allCycles = j.simpleCyles(graph, max);

        long finCiclos = System.currentTimeMillis();

        long time = finCiclos-inicioCiclos;


        allCycles.forEach(cycle -> {
            StringJoiner joiner = new StringJoiner("->");
            cycle.forEach(vertex -> joiner.add(vertex.toString()));
            System.out.println(joiner);
        });

        System.out.println("cant ciclos: "+ allCycles.size());
        System.out.println("TIEMPO DE BUSQUEDA DE CICLOS: "+ time);
    }
}
