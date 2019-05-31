import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

//    private static final int MAXLINEAS=250;

    public static void save(ArrayList<ArrayList<Node>> cycles) throws IOException {

        FileOutputStream fos = new FileOutputStream("resultado.txt");
        OutputStreamWriter w = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(w);

        for (int i = 0; i < cycles.size(); i++) {
            for (int j = 0; j < cycles.get(i).size(); j++) {
                bw.write(cycles.get(i).get(j) + "->"); //Queda un -> al final de cada ciclo!
            }
            bw.write("\n");
            bw.flush();
        }

        bw.close();
        System.out.println("Se guardo el archivo!");
    }

    public static void main(String[] args) throws IOException {



        Scanner reader = new Scanner(System.in);
        System.out.println("Ingrese ruta del odem: ");
        String odem= reader.next();
        System.out.println("Ingrese longitud maxima de ciclos: ");
        int max=reader.nextInt();
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


        Johnson j= new Johnson();
        ArrayList<ArrayList<Node>> allCycles = j.simpleCyles(graph, max);

        long finCiclos = System.currentTimeMillis();

        long time = finCiclos-inicioCiclos;

        System.out.println("cant ciclos: "+ allCycles.size());
        System.out.println("TIEMPO DE BUSQUEDA DE CICLOS: "+ time);

        long inicioArchivo = System.currentTimeMillis();

        save(allCycles);
        long finArchivo = System.currentTimeMillis();

        System.out.println("TIEMPO DE GENERACION DEL ARCHIVO: "+ (finArchivo-inicioArchivo));
    }
}
