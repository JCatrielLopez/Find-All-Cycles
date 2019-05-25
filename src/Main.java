import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        long inicio = System.currentTimeMillis();


        UserHandler my_handler = new UserHandler();
        ODEMSAXParser parser = null;

        try {

            parser = new ODEMSAXParser(my_handler,
                    "/home/catriel/IdeaProjects/tp_final_java/odems/apache-cxf-2.0.6.odem");
            parser.parse();

        } catch (ParserConfigurationException | IOException | SAXException e ) {
            e.printStackTrace();
        }


        HashMap<String, String> pkgs = my_handler.getPKGS();
        HashMap<String, ArrayList<String>> dep = my_handler.getDependencies();


        HashMap<String, ArrayList<String>> pkg_graph = new HashMap<>();
        for (String class_name: pkgs.keySet()){

            if (!pkg_graph.containsKey(pkgs.get(class_name))){
                ArrayList<String> pkg_dep = new ArrayList<>();
                for (String dependency : dep.get(class_name)) {
                    if (!pkg_dep.contains(pkgs.get(dependency)))
                        if ((pkgs.get(dependency) != null)
                                && (!pkgs.get(dependency).equals(pkgs.get(class_name))))
                            pkg_dep.add(pkgs.get(dependency));

                }

                pkg_graph.putIfAbsent(pkgs.get(class_name), pkg_dep);
            }
            else{
                for (String dependency : dep.get(class_name)) {
                    if (!pkg_graph.get(pkgs.get(class_name)).contains(pkgs.get(dependency))) {
                        if ((pkgs.get(dependency) != null)
                                && (!pkgs.get(dependency).equals(pkgs.get(class_name))))

                            pkg_graph.get(pkgs.get(class_name)).add(pkgs.get(dependency));
                    }

                }
            }

        }

        for(String pkg: pkg_graph.keySet()){
            System.out.println("Nodo: " + pkg);
            for(String dp: pkg_graph.get(pkg)){
                System.out.println("Adyacentes: " + dp);
            }

            System.out.println("\n");
        }

        long fin = System.currentTimeMillis();
        System.out.println("Demora de generacion de grafo (milis): " + (fin - inicio));
    }
}
