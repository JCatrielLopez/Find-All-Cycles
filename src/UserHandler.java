import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class UserHandler extends DefaultHandler {

    boolean type = false;
    boolean depends_on = false;

    private HashMap<String, String> packages = new HashMap<>();
    private HashMap<String, ArrayList<String>> dependencies = new HashMap<>();
    private HashMap<String, ArrayList<String>> pkg_dep = new HashMap<>();
    private String current_pkg;
    private String current_class;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("namespace")) {
            this.current_pkg = attributes.getValue("name");
        }
        else
            if (qName.equalsIgnoreCase("type")) {
                this.current_class = attributes.getValue("name");
                this.packages.putIfAbsent(this.current_class, this.current_pkg);
                this.dependencies.putIfAbsent(this.current_class, new ArrayList<>());
                type = true;
            }
        else
            if (qName.equalsIgnoreCase("depends-on")) {
                String class_name = attributes.getValue("name");
                if (class_name != null)
                    this.dependencies.get(this.current_class).add(class_name);
                depends_on = true;
            }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("namespace")) {
            this.current_pkg = "";
        }
        else
            if (qName.equalsIgnoreCase("type")) {
                this.current_class = "";
                this.type = false;
            }
        else
            if (qName.equalsIgnoreCase("lastname"))
                depends_on = false;


    }

    public HashMap<String, ArrayList<String>> getPKGS(){

        for(String clase: this.dependencies.keySet()){
            if (!this.pkg_dep.containsKey(this.packages.get(clase)))
                this.pkg_dep.put(this.packages.get(clase), new ArrayList<>());
            for(String dep: this.dependencies.get(clase)){
                this.pkg_dep.get(this.packages.get(clase)).add(this.packages.get(dep));
            }
        }
        return this.pkg_dep;
    }

    public HashMap getDependencies(){
        return this.dependencies;
    }

}