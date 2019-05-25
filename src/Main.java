import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

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

//        for(String key: pkgs.keySet()){
//            System.out.println("Class: " + key);
//            System.out.println("Package: " + pkgs.get(key) + "\n");
//        }
//
//        System.out.println("\n############################################################################\n");
//
//        for(String key: dep.keySet()){
//            System.out.println("Class: " + key);
//            for(String class_name: dep.get(key)) {
//                System.out.println("     Dependency:" + class_name);
//            }
//        }


        HashMap<String, ArrayList<String>> pkg_graph = new HashMap<>();
        for (String class_name: pkgs.keySet()){

            ArrayList<String> pkg_dep = new ArrayList<>();
            for (String dependency: dep.get(class_name)) {
                if (!pkg_dep.contains(pkgs.get(dependency)))
                    if ((pkgs.get(dependency) != null)
                            && (!dependency.startsWith("java."))
                            && (!dependency.contains("$"))
                            && (pkgs.get(dependency) != pkgs.get(class_name)))

                        pkg_dep.add(pkgs.get(dependency));
            }

            pkg_graph.putIfAbsent(pkgs.get(class_name), pkg_dep);

        }

        for(String pkg: pkg_graph.keySet()){
            System.out.println("Nodo: " + pkg);
            for(String dp: pkg_graph.get(pkg)){
                System.out.println("Adyacentes: " + dp);
            }

            System.out.println("\n");
        }


    }
}
