import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    HashMap<String, Integer> names; //Porque quiero guardar los nombres, pero trabajar con los numeros.
    HashMap<Integer, ArrayList<Integer>> elements;
    int size;
    int last;

    public Graph(){
        this.names  = new HashMap<>();
        this.elements = new HashMap<>();
        this.size = 0;
        this.last = 0;
    }

    public void addElement(String name){
        if (!this.names.containsKey(name)) {
            this.names.putIfAbsent(name, last);
            this.elements.putIfAbsent(last, new ArrayList<>());
            last++;
            size++;
        }
    }

    public void removeElement(String name){
        if (this.names.containsKey(name)){
            this.elements.remove(this.names.get(name));
            this.names.remove(name);
            size--;
        }
    }

    public void addEdge(String first, String end){
        if (this.names.containsKey(first) && this.names.containsKey(end)){
            this.elements.get(this.names.get(first)).add(this.names.get(end));
        }
    }

    public void removeEdge(String first, String end){
        if (this.names.containsKey(first) && this.names.containsKey(end)){
            this.elements.get(this.names.get(first)).remove(this.names.get(end));
        }
    }

    public int getSize(){
        return this.size;
    }

    public void print(){
        for(Integer pkg: this.elements.keySet()){
            System.out.println("Nodo: " + pkg);
            for(Integer dp: this.elements.get(pkg)){
                System.out.println("Adyacente: " + dp);
            }

            System.out.println("\n");
        }
    }

}
