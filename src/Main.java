import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

public class Main {

    private static final int MAXLINEAS=1000;

    private static void save_to_file(ArrayList<ArrayList<Node>> cycles) {
        FileWriter fw=null;
        try{
        File file = new File("ciclos.txt");
        if (!file.exists())
            file.createNewFile();
        fw= new FileWriter(file);

        int i=0;
        StringBuilder ciclos= new StringBuilder();
        for(ArrayList<Node> cycle: cycles){
            for(Node n: cycle){
                ciclos.append(n.toString()+";");
            }
            i++;
            ciclos.append("\n");

            //para no quedarme sin memoria, cada cierta cantidad de lineas las guardo en el archivo y reseteo el string builder
            if (i==MAXLINEAS){
                fw.append(ciclos);
                ciclos.setLength(0);
            }
        }
            fw.append(ciclos);

        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        finally {
            try{
                if (fw!=null)
                    fw.close();
            }
            catch (IOException e){
                System.out.println("Error cerrando el archivo");
            }

        }

    }

    public static void main(String[] args) {



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

        save_to_file(allCycles);

        long finArchivo = System.currentTimeMillis();

        System.out.println("TIEMPO DE GENERACION DEL ARCHIVO: "+ (finArchivo-inicioArchivo));
    }
}
