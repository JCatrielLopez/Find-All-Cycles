import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        long inicio = System.currentTimeMillis();


        UserHandler my_handler = new UserHandler();
        ODEMSAXParser parser;

        try {

            parser = new ODEMSAXParser(my_handler,
                    "odems/apache-cxf-2.0.6.odem");
            parser.parse();

        } catch (ParserConfigurationException | IOException | SAXException e ) {
            e.printStackTrace();
        }


        HashMap<String, ArrayList<String>> pkgs = my_handler.getPKGS();
//        HashMap<String, ArrayList<String>> dep = my_handler.getDependencies();


//        HashMap<String, ArrayList<String>> pkg_graph = new HashMap<>();
//        for (String class_name: pkgs.keySet()){
//
//            if (!pkg_graph.containsKey(pkgs.get(class_name))){
//                ArrayList<String> pkg_dep = new ArrayList<>();
//                for (String dependency : dep.get(class_name)) {
//                    if (!pkg_dep.contains(pkgs.get(dependency)))
//                        if ((pkgs.get(dependency) != null)
//                                && (!pkgs.get(dependency).equals(pkgs.get(class_name))))
//                            pkg_dep.add(pkgs.get(dependency));
//
//                }
//
//                pkg_graph.putIfAbsent(pkgs.get(class_name), pkg_dep);
//            }
//            else{
//                for (String dependency : dep.get(class_name)) {
//                    if (!pkg_graph.get(pkgs.get(class_name)).contains(pkgs.get(dependency))) {
//                        if ((pkgs.get(dependency) != null)
//                                && (!pkgs.get(dependency).equals(pkgs.get(class_name))))
//
//                            pkg_graph.get(pkgs.get(class_name)).add(pkgs.get(dependency));
//                    }
//
//                }
//            }
//
//        }

        Graph graph = new Graph();


        for(String pkg: pkgs.keySet()){
            Node node = new Node(pkg);
            graph.addElement(node);
            System.out.println("NODO: " + node.toString());
            for(String pkg_ady: pkgs.get(pkg)){
                if ((pkg_ady != null)){
                    Node ady;
                    if (graph.contains(pkg_ady)){
                        ady = graph.get(pkg_ady);
                        System.out.println("El adyacente ya existe " + ady.toString());
                    }
                    else {
                        ady = new Node(pkg_ady);
                        graph.addElement(ady);
                        System.out.println("El adyacente no existe " + ady.toString());
                    }

                    if (!ady.equals(node)){
                        graph.addEdge(node, ady);
                    }
                }
            }
        }

        graph.print();

        System.out.println("\nSize: " + graph.getSize());


        long fin = System.currentTimeMillis();
        System.out.println("Demora de generacion de grafo (milis): " + (fin - inicio));
    }
}
