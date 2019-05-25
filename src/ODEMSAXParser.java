import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ODEMSAXParser {

    private UserHandler user_handler;
    private SAXParser parser;
    private File inputFile;

    public ODEMSAXParser(UserHandler handler, String file_path) throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        this.parser = factory.newSAXParser();
        this.inputFile = Paths.get(file_path).toFile();
        this.user_handler = handler;
    }

    public void parse() throws IOException, SAXException {
        parser.parse(this.inputFile, this.user_handler);

    }
}